package io.github.chasencode.csconfigclient.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Program: csconfig
 * @Description: 配置数据
 * @Author: Chasen
 * @Create: 2024-05-04 19:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSConfigMeta {

    public String env;

    public String app;

    public String ns;
    public String configServer;

}
