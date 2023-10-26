package lingzhong.com.svideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * @Author 孙铭杰
 * @since 2023-10-25 16:10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LingZhongChatApp {
    public static void main(String[] args) {
        SpringApplication.run(LingZhongChatApp.class , args);
    }
}
