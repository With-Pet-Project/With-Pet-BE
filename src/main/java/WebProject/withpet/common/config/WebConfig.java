package WebProject.withpet.common.config;

import WebProject.withpet.common.auth.application.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtAuthInterceptor(jwtTokenProvider))

            .addPathPatterns("/pet/**", "/mypage/**", "/{petId}/challenge/**", "/article/**",
                "/image/**", "/comment/**","article_like/**","/user/duplicate","/user/password");


    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*", "http://localhost:3000").allowedHeaders("*").allowedMethods("*");
    }
}
