package io.github.chasencode.csconfigclient.spring;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: csconfig
 * @Description: 资源处理
 * @Author: Chasen
 * @Create: 2024-05-03 21:07
 * PriorityOrdered 相当于 在类上加Order配置 ， 因不想被bean容器管理，是作为类库引入的不是作为bean扫描到使用的，尽量不用注解使用
 **/
@Data
public class CSPropertySourcesProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {


    private final static String CS_PROPERTY_SOURCES = "CSPropertySources";
    private final static String CS_PROPERTY_SOURCE = "CSPropertySource";

    Environment environment;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        if (env.getPropertySources().contains(CS_PROPERTY_SOURCE)) {
            return;
        }
        // 通过HTTP 请求获取配置中心（csconfig-server）的配置
        Map<String, String> config = new HashMap<>();
        config.put("cs.a", "dev500");
        config.put("cs.b", "dev600");
        config.put("cs.c", "dev700");
        // 实例话配置服务
        CSConfigService csConfigService = new CSConfigServiceImpl(config);
        // 实例话数据源
        CSPropertySource propertySources = new CSPropertySource(CS_PROPERTY_SOURCE, csConfigService);
        // 实例配置信息
        CompositePropertySource compositePropertySource = new CompositePropertySource(CS_PROPERTY_SOURCES);
        compositePropertySource.addPropertySource(propertySources);
        // 将配置放到最前面， 因为越往前优先级越高
        env.getPropertySources().addFirst(propertySources);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
