package com.iamvickyav.springbootjaxrssample.config;

import com.iamvickyav.springbootjaxrssample.controller.BookController;
import com.iamvickyav.springbootjaxrssample.exception.ApiExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class AppConfig extends ResourceConfig {

    AppConfig() {
        register(BookController.class);
        register(ApiExceptionMapper.class);
    }
}
