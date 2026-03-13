package com.example.demo.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelcastConfig() {
        return new Config()
                .addMapConfig(new MapConfig()
                        // NAME CACHÉ
                        .setName("cacheServicePokeApi")
                        // TTL TIME-TO-LIVE DEL CACHÉ
                        .setTimeToLiveSeconds(60));
    }

}