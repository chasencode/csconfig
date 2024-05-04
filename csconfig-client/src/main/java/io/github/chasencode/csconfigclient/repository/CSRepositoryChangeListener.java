package io.github.chasencode.csconfigclient.repository;

import io.github.chasencode.csconfigclient.spring.CSConfigMeta;

import java.util.Map;

/**
 * @Program: csconfig
 * @Description: 存储事件变更
 * @Author: Chasen
 * @Create: 2024-05-04 20:24
 **/
public interface CSRepositoryChangeListener {

    void onChange(CSRepositoryChangeEvent event);

    // 17 record ， 相当于 class 定义了 meta 和 config 两个属性pojo
    record CSRepositoryChangeEvent(CSConfigMeta meta, Map<String,String> config) {
    }
}
