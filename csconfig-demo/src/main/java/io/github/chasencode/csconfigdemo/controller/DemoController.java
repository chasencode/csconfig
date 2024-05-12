package io.github.chasencode.csconfigdemo.controller;

import io.github.chasencode.csconfigdemo.conifig.CSDemoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Program: csconfig
 * @Description:
 * @Author: Chasen
 * @Create: 2024-05-04 19:59
 **/
@RestController
public class DemoController {

    @Value("${cs.a}")
    private String a;
    @Value("${cs.b}")
    private String b;

    @Autowired
    private CSDemoConfig csDemoConfig;

    @GetMapping ("/demo")
    public String hello() {
        return "cs.a = "+ a + "\n "
                + "cs.b = " + b + "\n"
                + "demo.a = " + csDemoConfig.getA() + "\n"
                + "demo.b = " + csDemoConfig.getB() + "\n";
    }
}
