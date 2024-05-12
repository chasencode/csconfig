package io.github.chasencode.csconfigclient.value;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * @Program: csconfig
 * @Description:
 * @Author: Chasen
 * @Create: 2024-05-09 09:36
 **/
@Data
@AllArgsConstructor
public class SpringValue {
    private Object bean;
    private String beanName;
    // placeholder
    private String key;
    // 全的placeholder
    private String placeholder;
    private Field field;
}
