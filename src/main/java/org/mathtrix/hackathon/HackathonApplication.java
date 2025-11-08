package org.mathtrix.hackathon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class HackathonApplication {
    public static void main(String[] args) {
        log.info("-------- Starting the Application -------");
        SpringApplication.run(HackathonApplication.class, args);
    }
}
