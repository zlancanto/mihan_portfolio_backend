package fr.mihan.portfolio.service;

import fr.mihan.portfolio.dto.ContactDTO;
import jakarta.mail.MessagingException;

public interface ContactMailService {
    void sendMail(ContactDTO dto) throws MessagingException;
}
