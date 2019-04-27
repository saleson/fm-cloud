package com.fm.gray.client;

import com.fm.gray.client.config.GrayBunnyConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(GrayBunnyConfiguration.class)
public @interface EnableGrayBunny {


}
