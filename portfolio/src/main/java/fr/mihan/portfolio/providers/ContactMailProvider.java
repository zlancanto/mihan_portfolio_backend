package fr.mihan.portfolio.providers;

import fr.mihan.portfolio.dto.ContactDTO;
import jakarta.mail.MessagingException;

public interface ContactMailProvider {
    void sendEmail(ContactDTO dto) throws MessagingException;
}
