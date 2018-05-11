package org.copy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableTransactionManagement
@ServletComponentScan
@MapperScan("org.copy.*.dao")
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run( App.class,args);
    }

}
