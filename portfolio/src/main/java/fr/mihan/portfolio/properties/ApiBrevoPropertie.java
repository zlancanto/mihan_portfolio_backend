package fr.mihan.portfolio.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.brevo")
public record ApiBrevoPropertie(
        String baseUrl,
        String apiKey
){
    public ApiBrevoPropertie {
        // Validation préventive
        if (apiKey == null || apiKey.length() < 30) {
            String msgException = "La clé API Brevo semble invalide (trop courte). " +
                    "Vérifiez la variable d'environnement BREVO_API_KEY.";
            throw new IllegalArgumentException(msgException);
        }
    }
}
