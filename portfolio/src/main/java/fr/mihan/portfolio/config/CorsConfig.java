package fr.mihan.portfolio.config;

import fr.mihan.portfolio.properties.CorsPropertie;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableConfigurationProperties(CorsPropertie.class)
public class CorsConfig {
    private final CorsPropertie corsPropertie;

    public CorsConfig(CorsPropertie corsPropertie) {
        this.corsPropertie = corsPropertie;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(corsPropertie.getAllowedOrigins());
        config.setAllowedMethods(corsPropertie.getAllowedMethods());
        config.setAllowedHeaders(corsPropertie.getAllowedHeaders());
        config.setAllowCredentials(corsPropertie.isAllowCredentials());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
