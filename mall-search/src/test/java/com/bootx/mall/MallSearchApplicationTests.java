package com.bootx.mall;

import com.bootx.mall.config.ElasticSearchConfig;
import com.bootx.mall.util.JsonUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
class MallSearchApplicationTests {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
        System.out.println(restHighLevelClient);
    }

    @Test
    public void index() throws Exception{
        IndexRequest indexRequest = new IndexRequest("users");

        indexRequest.id("1");
        User user = new User();
        user.setAge(12);
        user.setGender("男");
        user.setUsername("张三");
        indexRequest.source(JsonUtils.toJson(user), XContentType.JSON);

        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(indexResponse);
    }

    @Test
    public void search() throws Exception{
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("users");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("username","张三");
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        RestStatus status = searchResponse.status();
        System.out.println(searchResponse.toString());

    }


    class User implements Serializable {
        private String username;

        private String gender;

        private Integer age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}
