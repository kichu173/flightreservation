package com.example.flightreservation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailUtil {
    @Value("${com.expmple.flighreservation.itinerary.email.subject}")
    public String EMAIL_SUBJECT;
    @Value("${com.expmple.flighreservation.itinerary.email.body}")
    public String EMAIL_BODY;
    @Autowired
    private JavaMailSender sender;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

    public void sendItinerary(String toAddress, String filePath) {
        LOGGER.info("Inside sendItinerary()");
        MimeMessage mimeMessage = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toAddress);
            helper.setText(EMAIL_SUBJECT);
            helper.setSubject(EMAIL_BODY);
            helper.addAttachment("Itinerary", new File(filePath));
        } catch (MessagingException e) {
            LOGGER.error("Exception inside sendItinerary ----> " + e);
            e.printStackTrace();
        }
        sender.send(mimeMessage);
    }
}
