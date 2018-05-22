package cn.code.chameleon.model;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liumingyu
 * @create 2018-05-22 上午11:03
 */
public class ESClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(ESClient.class);

    protected TransportClient client;

    public ESClient() {
        this("localhost", "9300");
    }

    public ESClient(String host, String port) {
        this("elasticsearch", host, port);
    }

    public ESClient(String clusterName, String host, String port) {
        client = (TransportClient) this.getElient(clusterName, host, port);
    }

    public Client getElient(String clusterName, String host, String port) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("cluster.name", clusterName);
            //创建node客户端
            Settings.Builder settings = Settings.builder().put(map);
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));

            final ClusterHealthResponse healthResponse = client.admin().cluster().prepareHealth()
                    .setTimeout(TimeValue.timeValueMinutes(1)).execute().actionGet();
            if (healthResponse.isTimedOut()) {
                LOGGER.error("ES客户端初始化失败");
            } else {
                LOGGER.info("ES客户端初始化成功");
            }
        } catch (Exception e) {
            LOGGER.error("ES客户端初始化失败");
        }
        return client;
    }

    public boolean isExistsIndex(String indexName) {
        IndicesExistsResponse response =
                client.admin().indices().exists(
                        new IndicesExistsRequest().indices(new String[]{indexName})).actionGet();
        return response.isExists();
    }

    public boolean isExistsType(String indexName, String indexType) {
        TypesExistsResponse response =
                client.admin().indices()
                        .typesExists(new TypesExistsRequest(new String[]{indexName}, indexType)
                        ).actionGet();
        return response.isExists();
    }

    public boolean save(String index, String type, String id, Map<String, Object> results) {

        IndexResponse response = client.prepareIndex(index, type, id).setSource(results).get();
        return response.isCreated();
    }

    public boolean update(String index, String type, String id, Map<String, Object> results) throws Exception {
        UpdateResponse response = client.update(new UpdateRequest(index, type, id)
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .map(results)
                        .endObject()
                )).get();
        return response.isCreated();
    }

    public boolean delete(String index, String type, String id) {
        DeleteResponse response = client.prepareDelete(index, type, id).get();
        return response.isFound();
    }

    public boolean delete(String index, String type) throws Exception {
        DeleteRequest request = new DeleteRequest();
        request.index(index);
        request.type(type);
        ActionFuture<DeleteResponse> response = client.delete(request);
        return response.actionGet().isFound();
    }

    public boolean delete(String index) throws Exception {
        DeleteRequest request = new DeleteRequest();
        request.index(index);
        ActionFuture<DeleteResponse> response = client.delete(request);
        return response.actionGet().isFound();
    }

    public Long getTotalByIndex(String index) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index)
                .setQuery(QueryBuilders.matchAllQuery());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return response.getHits().getTotalHits();
    }

    /**
     * 获取库中数据总数
     *
     * @return
     */
    public Long getTotalByType(String index, String type) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return response.getHits().getTotalHits();
    }

    /**
     * 获取库中符合条件是数据数量
     *
     * @param queryBuilder 匹配条件
     * @return
     */
    protected Long getCountByQuery(String index, String type, QueryBuilder queryBuilder) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(queryBuilder);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return response.getHits().getTotalHits();
    }
}
