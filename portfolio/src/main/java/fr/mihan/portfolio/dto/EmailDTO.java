package fr.mihan.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Objet minimal représentant un Email
 */
@Getter
@AllArgsConstructor
public abstract class EmailDTO {
    @NotBlank(message = "L'email est obligatoire")
    @NotNull(message = "L'email doit pas être null")
    @Email(message = "Format d'email invalide")
    protected final String email;

    @NotBlank(message = "Le subject est obligatoire")
    @NotNull(message = "Le subject doit pas être null")
    @Size(min = 3, max = 60, message = "Le subject doit être compris entre 3 et 60 caractères")
    protected final String subject;

    @NotBlank(message = "Le message est obligatoire")
    @NotNull(message = "Le message doit pas être null")
    @Size(min = 10, message = "Le subject doit faire au moins 10 caractères")
    protected final String message;
}
