package fr.mihan.portfolio.service.impl;

import fr.mihan.portfolio.dto.ContactDTO;
import fr.mihan.portfolio.propertie.ContactProperties;
import fr.mihan.portfolio.service.ContactMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

/**
 * Service d'envoi de mail
 */
@Service
public class ContactContactMailServiceImpl implements ContactMailService {
    private final JavaMailSender mailSender;
    private final ContactProperties contactProperties;

    /**
     * Création d'un service mail
     * @param mailSender objet qui envoie le mail
     * @param contactProperties propriétés du contact
     * @NullPointerException si au moins un params est null
     */
    public ContactContactMailServiceImpl(JavaMailSender mailSender, ContactProperties contactProperties) {
        this.mailSender= Objects.requireNonNull(
                mailSender,
                "mailSender can't be null"
        );
        this.contactProperties = Objects.requireNonNull(
                contactProperties,
                "contactProperties can't be null"
        );
    }

    /**
     * Methode permettant d'envoyer un mail au
     * propriétaire du portfolio
     * @param dto objet mail
     * @throws MessagingException s'il y a une exception
     */
    @Override
    public void sendMail(ContactDTO dto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

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

        helper.setTo(contactProperties.getEmail());
        helper.setReplyTo(dto.getEmail());
        helper.setSubject(dto.getSubject());
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
