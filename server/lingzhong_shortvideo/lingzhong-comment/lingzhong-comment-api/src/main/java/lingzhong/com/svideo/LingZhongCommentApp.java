package lingzhong.com.svideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author 孙铭杰
 * @since 2023-10-25 15:41
 */

@SpringBootApplication
@EnableDiscoveryClient
public class LingZhongCommentApp {
    public static void main(String[] args) {
        SpringApplication.run(LingZhongCommentApp.class , args);
    }
}
