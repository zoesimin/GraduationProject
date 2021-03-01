package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
@SpringBootApplication
public class MindmapBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MindmapBackendApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        ///最大文件
        factory.setMaxFileSize("2048MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("2048MB");
        return factory.createMultipartConfig();
    }
}
