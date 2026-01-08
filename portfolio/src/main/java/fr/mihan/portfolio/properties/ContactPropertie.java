package fr.mihan.portfolio.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Contient les variables globales du contact
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.contact")
public final class ContactPropertie {
    private String email;
}
