package fr.mihan.portfolio.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin")
public record AdminPropertie (
        String firstName,
        String lastName
){}
