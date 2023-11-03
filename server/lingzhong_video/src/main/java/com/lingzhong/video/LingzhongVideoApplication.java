package com.lingzhong.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ljx
 */
@SpringBootApplication
@EnableScheduling
public class LingzhongVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LingzhongVideoApplication.class, args);
    }


}
