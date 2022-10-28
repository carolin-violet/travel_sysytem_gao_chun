package com.carolin_violet.travel_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


@ServletComponentScan
@SpringBootApplication
@ComponentScan(basePackages = {"com.carolin_violet"})
public class TravelSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelSystemApplication.class, args);
    }

}
