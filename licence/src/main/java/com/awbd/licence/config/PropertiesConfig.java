package com.awbd.licence.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("percentage")
@Getter
@Setter
public class PropertiesConfig {
    private int discount;
}
