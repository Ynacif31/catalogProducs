package com.ygornacif.projetoCatalog.services;

import com.ygornacif.projetoCatalog.services.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            emailSender.send(message);
            System.out.println("Email enviado com sucesso para: " + to);
        } catch (MailException e) {
            System.err.println("Erro ao enviar email para: " + to + ", motivo: " + e.getMessage());
            throw new EmailException("Failed to send email: " + e.getMessage(), e);
        }
    }
}