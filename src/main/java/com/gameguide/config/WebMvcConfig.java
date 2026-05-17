package com.gameguide.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    private String resolveUploadPath() {
        String dir = uploadDir;
        if (dir.startsWith("classpath:")) {
            dir = dir.substring("classpath:".length());
        }
        if (dir.startsWith("/")) {
            dir = dir.substring(1);
        }
        if (dir.isEmpty()) {
            dir = "uploads";
        }
        return Paths.get(dir).toAbsolutePath().toString();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + resolveUploadPath() + "/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/games").setViewName("games");
        registry.addViewController("/guides").setViewName("guides");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/user-center").setViewName("user-center");
        registry.addViewController("/guide-detail").setViewName("guide-detail");
        registry.addViewController("/game-detail").setViewName("game-detail");
        registry.addViewController("/guide-editor").setViewName("guide-editor");
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/contact").setViewName("contact");
        registry.addViewController("/disclaimer").setViewName("disclaimer");
        registry.addViewController("/privacy").setViewName("privacy");
    }
}
