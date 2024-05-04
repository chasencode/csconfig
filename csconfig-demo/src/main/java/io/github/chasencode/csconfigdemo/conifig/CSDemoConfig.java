package io.github.chasencode.csconfigdemo.conifig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Program: csconfig
 * @Description: 配置文件
 * @Author: Chasen
 * @Create: 2024-05-03 20:45
 **/
@ConfigurationProperties(prefix = "cs")
@Data
public class CSDemoConfig {

    String a;

    String b;
}
