package ru.inno.task5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.inno.task5.config.AppConfig;

@SpringBootApplication
@EnableTransactionManagement
public class Task5Application {

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }

}
