package io.github.chasencode.csconfigdemo;

import io.github.chasencode.csconfigclient.annoation.EnableCSConfig;
import io.github.chasencode.csconfigdemo.conifig.CSDemoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(CSDemoConfig.class)
@EnableCSConfig
public class CsconfigDemoApplication {

    @Value("${cs.a}")
    private String a;

    @Autowired
    private CSDemoConfig csDemoConfig;

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(CsconfigDemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        System.out.println(Arrays.toString(environment.getActiveProfiles()));
        return args -> {
            System.out.println("a = " + a);
            System.out.println("a = " + csDemoConfig.getA());
        };
    }
}
