package br.ufms.cpcx.balan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Trabalho1Application {

    @Autowired
    @Value("${application.name}")
    private String valorDaMinhaConfig;

    @GetMapping("/profile")
    public String hello() {
        return valorDaMinhaConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(Trabalho1Application.class, args);
    }
}
