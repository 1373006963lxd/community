package life.lxd.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by codedrinker on 2019/5/16.
 */
@Configuration
//这个注解需要自己配置静态资源的位置，不走默认的路径，如果不用这个注解，会自动解析static下的静态资源
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*可以在后面.excludePathPatterns("/xxxx/xxxx") 来排除那些路径不需要拦截*/
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }
}
