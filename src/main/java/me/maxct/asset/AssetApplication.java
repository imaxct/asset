package me.maxct.asset;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("me.maxct.asset.mapper")
public class AssetApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
    }

}
