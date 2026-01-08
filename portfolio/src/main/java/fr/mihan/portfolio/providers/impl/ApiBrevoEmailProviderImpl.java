package fr.mihan.portfolio.providers.impl;

import fr.mihan.portfolio.dto.ContactDTO;
import fr.mihan.portfolio.dto.EmailRequestDTO;
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

    @Override
    public void sendEmail(ContactDTO dto) throws MessagingException {
        final String url = brevoPropertie.baseUrl() + RoutesApiBrevo.SEND_EMAIL;

        String cleanName = HtmlUtils.htmlEscape(dto.getName());
        String cleanEmail = HtmlUtils.htmlEscape(dto.getEmail());
        String cleanMessage = HtmlUtils.htmlEscape(dto.getMessage()).replace("\n", "<br/>");

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
        """.formatted(cleanEmail, cleanName, cleanMessage);

        EmailRequestDTO body = EmailRequestDTO.builder()
                .sender(new EmailRequestDTO.Sender("Portfolio Contact", contactPropertie.getEmail()))
                .to(List.of(new EmailRequestDTO.To(contactPropertie.getEmail())))
                .subject("Nouveau message de : " + dto.getName())
                .htmlContent(htmlContent)
                .build();

        HttpResponse<JsonNode> response = Unirest.post(url)
                .body(body)
               .asJson();

        if (!response.isSuccess()) {
            throw new RuntimeException("Échec de l'envoi : " + response.getBody());
        }
    }
}
