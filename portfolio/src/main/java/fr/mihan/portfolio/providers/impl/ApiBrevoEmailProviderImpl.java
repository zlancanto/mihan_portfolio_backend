package fr.mihan.portfolio.providers.impl;

import fr.mihan.portfolio.dto.ContactDTO;
import fr.mihan.portfolio.dto.EmailRequestDTO;
import fr.mihan.portfolio.properties.AdminPropertie;
import fr.mihan.portfolio.properties.ApiBrevoPropertie;
import fr.mihan.portfolio.properties.ContactPropertie;
import fr.mihan.portfolio.providers.ContactMailProvider;
import fr.mihan.portfolio.variables.RoutesApiBrevo;
import jakarta.mail.MessagingException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class ApiBrevoEmailProviderImpl implements ContactMailProvider {
    private final ApiBrevoPropertie brevoPropertie;
    private final ContactPropertie contactPropertie;
    private final AdminPropertie adminPropertie;

    @Override
    public void sendEmail(ContactDTO dto) throws MessagingException {
        final String url = brevoPropertie.baseUrl() + RoutesApiBrevo.SEND_EMAIL;

        String cleanName = HtmlUtils.htmlEscape(dto.getName());
        String cleanEmail = HtmlUtils.htmlEscape(dto.getEmail());
        String cleanMessage = HtmlUtils.htmlEscape(dto.getMessage()).replace("\n", "<br/>");

        EmailRequestDTO bodyAdmin = buildEmailAdmin(cleanName, cleanEmail, cleanMessage);
        EmailRequestDTO bodyUser = buildEmailUser(cleanEmail);

        // Admin
        HttpResponse<JsonNode> response = Unirest.post(url)
                .body(bodyAdmin)
               .asJson();

        if (!response.isSuccess()) {
            throw new RuntimeException("Échec de l'envoi Admin : " + response.getBody());
        }

        // User
        response = Unirest.post(url)
                .body(bodyUser)
                .asJson();

        if (!response.isSuccess()) {
            throw new RuntimeException("Échec de l'envoi User : " + response.getBody());
        }
    }

    private EmailRequestDTO buildEmailAdmin(String name, String email, String msg) {
        String htmlContent = """
            <html>
                <body style="font-family: sans-serif;">
                    <h2 style="color: #2c3e50;">Nouveau message de mon Portfolio</h2>
                    <p><strong>Email :</strong> %s</p>
                    <p><strong>Nom :</strong> %s</p>
                    <p><strong>Message :</strong></p>
                    <div style="background: #f9f9f9; padding: 15px; border-left: 4px solid #3498db;">
                        %s
                    </div>
                </body>
            </html>
        """.formatted(email, name, msg);

        return EmailRequestDTO.builder()
                .sender(new EmailRequestDTO.Sender("Portfolio Contact", contactPropertie.getEmail()))
                .to(List.of(new EmailRequestDTO.To(contactPropertie.getEmail())))
                .subject("Nouveau message de : " + name)
                .htmlContent(htmlContent)
                .build();
    }

    private EmailRequestDTO buildEmailUser(String email) {
        final String ADMIN_NAME = adminPropertie.firstName() + " " + adminPropertie.lastName();
        String htmlContent = """
            <html>
                <body style="font-family: sans-serif;">
                    <h2>Merci de m'avoir contacté !</h2>
                    <p>J'ai bien reçu votre message et je vous répondrai dans les plus brefs délais.</p>
                    <br/>
                    <p>Cordialement,<p>
                    <p><strong>%s</strong></p>
                </body>
            </html>
        """.formatted(ADMIN_NAME);

        return EmailRequestDTO.builder()
                .sender(new EmailRequestDTO.Sender("<ScriptKode/>", contactPropertie.getEmail()))
                .to(List.of(new EmailRequestDTO.To(email)))
                .subject("Accusé de reception")
                .htmlContent(htmlContent)
                .build();
    }
}
