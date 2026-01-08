package fr.mihan.portfolio.config;

import fr.mihan.portfolio.properties.ApiBrevoPropertie;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApiBrevoPropertie.class)
public class ApiBrevoConfig {
}
