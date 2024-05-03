package io.github.chasencode.csconfigclient.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @Program: csconfig
 * @Description: 配置中心注册到bean中
 * @Author: Chasen
 * @Create: 2024-05-03 21:25
 **/
public class CSConfigRegister implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("register CSPropertySourcesProcessor");
        registerBean(registry, CSPropertySourcesProcessor.class);
    }


    private static void registerBean(BeanDefinitionRegistry registry, Class<?> clazz) {

        Optional<String> first = Arrays.stream(registry.getBeanDefinitionNames())
                .filter(x -> Objects.equals(registry.getBeanDefinition(x)
                        .getBeanClassName(), clazz.getName())).findFirst();
        if (first.isEmpty()) {
            System.out.println("register " + clazz.getName());
            // 创建bean的定义
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CSPropertySourcesProcessor.class);
            registry.registerBeanDefinition(CSPropertySourcesProcessor.class.getName(), builder.getBeanDefinition());
        }
    }
}
