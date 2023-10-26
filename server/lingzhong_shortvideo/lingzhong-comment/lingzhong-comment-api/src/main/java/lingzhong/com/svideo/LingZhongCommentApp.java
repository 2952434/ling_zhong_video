package lingzhong.com.svideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author 孙铭杰
 * @since 2023-10-25 15:41
 */

@SpringBootApplication
@EnableDiscoveryClient
@CrossOrigin(origins = "http://localhost:9023")
public class LingZhongCommentApp {
    public static void main(String[] args) {
        SpringApplication.run(LingZhongCommentApp.class , args);
    }
}
