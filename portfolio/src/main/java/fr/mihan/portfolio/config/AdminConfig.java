package fr.mihan.portfolio.config;

import fr.mihan.portfolio.properties.AdminPropertie;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AdminPropertie.class)
public class AdminConfig {
}
