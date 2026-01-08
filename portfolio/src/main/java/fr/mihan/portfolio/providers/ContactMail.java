package fr.mihan.portfolio.providers;

import fr.mihan.portfolio.dto.ContactDTO;
import fr.mihan.portfolio.exceptions.ContactMailException;
import jakarta.mail.MessagingException;

public interface ContactMail {
    void sendEmail(ContactDTO dto) throws ContactMailException, MessagingException;
}
