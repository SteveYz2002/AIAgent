package com.steve.steveaiagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class SteveAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteveAiAgentApplication.class, args);
    }

}
