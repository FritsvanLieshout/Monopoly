package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"restcontroller"})
public class MonopolyRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonopolyRestApplication.class, args);
    }
}
