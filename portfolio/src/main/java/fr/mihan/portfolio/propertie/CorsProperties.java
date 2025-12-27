package fr.mihan.portfolio.propertie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {
    private final List<String> allowedOrigins;
    private final List<String> allowedMethods;
    private final List<String> allowedHeaders;
    private final boolean allowCredentials;
}
