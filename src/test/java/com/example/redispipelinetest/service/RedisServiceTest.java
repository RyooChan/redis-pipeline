package com.example.redispipelinetest.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    void 입력_파이프라인_테스트() {
        long start = System.currentTimeMillis();
        redisService.saveDataByPipeline();
        long end = System.currentTimeMillis();
        System.out.println("소요시간 = " + (end - start) + "ms");
    }

    @Test
    void 입력_테스트() {
        long start = System.currentTimeMillis();
        redisService.saveData();
        long end = System.currentTimeMillis();
        System.out.println("소요시간 = " + (end - start) + "ms");
    }

    @Test
    void 검색_파이프라인_테스트() {
        long start = System.currentTimeMillis();
        List<String> dataByPipeline = redisService.findDataByPipeline();
        long end = System.currentTimeMillis();
        System.out.println("소요시간 = " + (end - start) + "ms");
        System.out.println(dataByPipeline.toString());
    }

    @Test
    void 검색_테스트() {
        long start = System.currentTimeMillis();
        List<String> data = redisService.findData();
        long end = System.currentTimeMillis();
        System.out.println("소요시간 = " + (end - start) + "ms");
        System.out.println(data.toString());
    }

}