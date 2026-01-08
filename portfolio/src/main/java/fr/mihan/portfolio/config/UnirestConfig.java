package fr.mihan.portfolio.config;

import fr.mihan.portfolio.properties.ApiBrevoPropertie;
import jakarta.annotation.PostConstruct;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UnirestConfig {
    private final ApiBrevoPropertie apiBrevoPropertie;

    @PostConstruct
    public void init() {
        Unirest.config().setDefaultHeader("api-key", apiBrevoPropertie.apiKey());
        Unirest.config().setDefaultHeader("Content-Type", "application/json");
        Unirest.config().setDefaultHeader("Accept", "application/json");
    }
}
