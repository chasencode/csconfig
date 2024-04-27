package io.github.chasencode.csconfigserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Program: csconfig-server
 * @Description: 配置
 * @Author: Chasen
 * @Create: 2024-04-27 20:20
 **/
@Data
@AllArgsConstructor
public class Configs {

    private String app;

    private String env;

    private String ns;

    private String pkey;

    private String pval;
}
