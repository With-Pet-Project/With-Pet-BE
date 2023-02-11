package WebProject.withpet.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final String[] INTERCEPTOR_WHITE_LIST = {
            "/user",
            "/user/signup"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new JwtAuthInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns("/user", "/user/signup");
    }
}
