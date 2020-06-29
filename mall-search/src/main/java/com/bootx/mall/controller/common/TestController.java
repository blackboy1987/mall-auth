package com.bootx.mall.controller.common;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/init")
    public RestHighLevelClient init(){
        return restHighLevelClient;
    }
}
