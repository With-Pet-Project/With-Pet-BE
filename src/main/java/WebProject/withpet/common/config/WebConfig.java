package WebProject.withpet.common.config;

import WebProject.withpet.auth.application.JwtTokenProvider;
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

                .addPathPatterns("/pet/**", "/mypage/**", "/article/**", "/image/**",
                        "/comment/**", "/article_like/**", "/user/duplicate");


    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:3000", "http://localhost:3000", "https://with-pet-fe.vercel.app")
                .allowedHeaders("*").allowedMethods("*").allowCredentials(true);
    }
}
