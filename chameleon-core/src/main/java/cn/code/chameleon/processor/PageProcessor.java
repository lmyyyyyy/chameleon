package cn.code.chameleon.processor;

import cn.code.chameleon.carrier.Page;
import cn.code.chameleon.carrier.Site;

/**
 * @author liumingyu
 * @create 2018-04-08 下午2:04
 */
public interface PageProcessor {

    void process(Page page);

    Site getSite();
}
