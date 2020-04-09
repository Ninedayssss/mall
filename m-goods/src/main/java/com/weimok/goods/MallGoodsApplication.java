package com.weimok.goods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author itsNine
 * @create 2020-04-05-9:34
 */
@SpringBootApplication
@MapperScan("com.weimok.goods.mapper")
@EnableDubbo
public class MallGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGoodsApplication.class);
    }
}
