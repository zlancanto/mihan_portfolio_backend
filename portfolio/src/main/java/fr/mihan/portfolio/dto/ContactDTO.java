package fr.mihan.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * Objet représentant le mail qu'un user m'envoie
 */
public class ContactDTO extends EmailDTO {
    @NotBlank(message = "name est obligatoire")
    @NotNull(message = "name doit pas être null")
    @Size(min = 2, message = "Le subject doit faire au moins 2 caractères")
    @Getter
    private final String name;

    public ContactDTO(String name, String email, String subject, String message) {
        super(email, subject, message);
        this.name = name.trim();
    }
}
