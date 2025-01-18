package com.project.popupmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:.env")
public class PopupmarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(PopupmarketApplication.class, args);
    }

}
