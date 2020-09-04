package com.connectgas.app.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching(proxyTargetClass = true)
public class EnableFireStoreRepositories {

}
