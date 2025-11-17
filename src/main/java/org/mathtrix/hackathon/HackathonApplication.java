package org.mathtrix.hackathon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class HackathonApplication {
    public static void main(String[] args) {
        log.info("-------- Starting the Application -------");
        SpringApplication.run(HackathonApplication.class, args);
    }
}
