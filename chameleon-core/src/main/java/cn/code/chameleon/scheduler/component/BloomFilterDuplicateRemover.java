package cn.code.chameleon.scheduler.component;

import cn.code.chameleon.carrier.Request;
import cn.code.chameleon.carrier.Task;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liumingyu
 * @create 2018-04-11 下午5:17
 */
public class BloomFilterDuplicateRemover implements DuplicateRemover {

    private int expectedCounts;

    private double fpp;

    private AtomicInteger counter;

    private final BloomFilter<CharSequence> bloomFilter;

    public BloomFilterDuplicateRemover(int expectedCounts) {
        this(expectedCounts, 0.0);
    }

    public BloomFilterDuplicateRemover(int expectedCounts, double fpp) {
        this.expectedCounts = expectedCounts;
        this.fpp = fpp;
        this.bloomFilter = rebuildBloomFilter();
    }

    protected BloomFilter<CharSequence> rebuildBloomFilter() {
        counter = new AtomicInteger(0);
        return BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), expectedCounts, fpp);
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
        boolean isDuplicate = bloomFilter.mightContain(getUrl(request));
        if (!isDuplicate) {
            bloomFilter.put(getUrl(request));
            counter.incrementAndGet();
        }
        return isDuplicate;
    }

    public String getUrl(Request request) {
        if (request == null) {
            return null;
        }
        return request.getUrl();
    }

    @Override
    public void resetDuplicate(Task task) {
        rebuildBloomFilter();
    }

    @Override
    public int getDuplicateCounts(Task task) {
        return counter.get();
    }
}
