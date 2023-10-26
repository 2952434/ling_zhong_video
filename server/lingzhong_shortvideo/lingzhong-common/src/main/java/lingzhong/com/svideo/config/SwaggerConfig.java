package lingzhong.com.svideo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Value("${spring.swagger.active.enabled}")
    private Boolean enabled;


    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .enable(enabled)
                .apiInfo(apiInfo())
                .host("https://gitee.com/sminjer/350-start")
                .select()
                .apis(RequestHandlerSelectors.basePackage("lingzhong.com.svideo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("凌众短视频服务接口文档")
                .description("350小队成员:孙铭杰、李君祥、陈士博")
                .contact(new Contact("350team",null,null))
                .version("1.0.0")
                .build();
    }
}