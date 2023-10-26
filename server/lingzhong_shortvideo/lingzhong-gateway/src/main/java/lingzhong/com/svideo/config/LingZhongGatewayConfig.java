package lingzhong.com.svideo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @Author 孙铭杰
 * @since 2023-10-26 16:32
 */
@Configuration
public class LingZhongGatewayConfig {
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedHeader("*");// #允许访问的头信息,*表示全部
        corsConfiguration.addAllowedMethod("*");// 允许提交请求的方法类型，*表示全部允许
        corsConfiguration.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许
        corsConfiguration.setAllowCredentials(true);// 允许cookies跨域

        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }
}
