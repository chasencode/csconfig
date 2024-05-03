package io.github.chasencode.csconfigclient.annoation;

import io.github.chasencode.csconfigclient.spring.CSConfigRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import(CSConfigRegister.class)
public @interface EnableCSConfig {
}
