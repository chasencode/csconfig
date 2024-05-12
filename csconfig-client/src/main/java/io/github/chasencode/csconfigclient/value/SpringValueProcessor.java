package io.github.chasencode.csconfigclient.value;

import io.github.chasencode.csconfigclient.util.FieldUtils;
import io.github.chasencode.csconfigclient.util.PlaceholderHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * 1.扫描所有spring Value,保存起来
 * 2.在配置变更时，更新所有srping value
 * @Program: csconfig
 * @Description: 解析 spring value
 * @Author: Chasen
 * @Create: 2024-05-09 09:15
 **/
@Slf4j
public class SpringValueProcessor  implements BeanPostProcessor, BeanFactoryAware, ApplicationListener<EnvironmentChangeEvent> {

    static final PlaceholderHelper helper = PlaceholderHelper.getInstance();

    static final MultiValueMap<String, SpringValue> VALUE_HOLDER = new LinkedMultiValueMap<>();
    @Setter
    private BeanFactory beanFactory;

    /**
     * 所有bean 加载过程中被扫描到, 看到bean中有@Value注解, 就保存起来 进行处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        List<Field> annotatedField = FieldUtils.findAnnotatedField(bean.getClass(), Value.class);
        annotatedField.forEach(field -> {
            log.info("[CSConfig] >> Found @Value annotation in bean: " + beanName + ", field: " + field.getName());
            Value value = field.getAnnotation(Value.class);
            helper.extractPlaceholderKeys(value.value()).forEach(key -> {
                log.info("[CSConfig] >>  Found placeholder key: " + key + " in @Value annotation of bean: " + beanName + ", field: " + field.getName());
                SpringValue springValue = new SpringValue(bean, beanName, key, value.value(), field);
                VALUE_HOLDER.add(key, springValue);
            });
        });
        return bean;
    }

    /**
     * 监听 EnvironmentChangeEvent 的变化
     * @param event
     */
//    @EventListener
//    public void onChange(EnvironmentChangeEvent event) {
//
//    }

    /**
     * 监听 EnvironmentChangeEvent 的变化
     * 和 @EventListener 是等价的
     * @param event
     */
    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        log.info("[CSConfig] >>  Environment change event received, keys: {}", event.getKeys());

        event.getKeys().forEach(key -> {
            List<SpringValue> springValues = VALUE_HOLDER.get(key);
            if (springValues == null || springValues.isEmpty()) {
              return;
            }
            springValues.forEach(springValue -> {
                log.info("[CSConfig] >>  Updating spring value: {} for key: {}", springValue, key);
                try {
                    final Object value = helper.resolvePropertyValue((ConfigurableBeanFactory) beanFactory, springValue.getBeanName(),
                            springValue.getPlaceholder());
                    log.info("[CSConfig] >>  update field value: {} for key: {}", value, key);
                    springValue.getField().setAccessible(true);
                    springValue.getField().set(springValue.getBean(), value);
                } catch (IllegalAccessException e) {
                    log.error("[CSConfig] >>  Failed to update spring value for key: " + key, e);
//                    throw new RuntimeException(e);
                }
            }); // 更新spring value
        });
    }
}
