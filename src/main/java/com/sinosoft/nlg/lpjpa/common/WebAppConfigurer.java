package com.sinosoft.nlg.lpjpa.common;

import com.sinosoft.nlg.lpjpa.common.interceptor.DemoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

public class WebAppConfigurer extends WebMvcConfigurerAdapter {


    /**
     * 内部资源视图解析器 配置 这是springmvc的核心渲染机制 springboot 内置tomcat不支持 使用jsp
     * 如果要这样使用，需要使用外部tomcat
     * 此处同样可以配置 其他的解析方式，比如freemaker
     * @return viewResolver
     */
    @Bean
    public InternalResourceViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        //配置前后缀
        viewResolver.setPrefix("/views");
        viewResolver.setSuffix(".jsp");
        //配置渲染方式.这里是Jstl 可以采用freemark或者其他静态模板的方式
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    /**
     * 添加自定义的静态资源映射
     这里使用代码的方式自定义目录映射，并不影响Spring Boot的默认映射，可以同时使用。
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        super.addResourceHandlers(registry);
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }



    @Bean
    public DemoInterceptor demoInterceptor() {
        return new DemoInterceptor();
    }

    /**
     * 注册拦截器
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则
      excludePathPatterns 用户排除拦截
     可以直接now registry.addInterceptor(new DemoInterceptor()).addPathPatterns("/**");
     或者 像这样
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }



    /**
     * 空白转向注册
     * @param registry 这是加载的处理器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/toupload").setViewName("/upload");
        registry.addViewController("/index").setViewName("/add/index");
    }

    /**
     *  设置不忽略 .后面的参数
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseRegisteredSuffixPatternMatch(false);
    }

}
