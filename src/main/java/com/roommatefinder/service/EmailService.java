package com.roommatefinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    public void sendWelcomeEmail(String toEmail, String fullName) {
        String subject = "Welcome to RoommateFinder!";
        String body = String.format(
                "Hi %s,\n\n" +
                        "Welcome to RoommateFinder!\n\n" +
                        "We're excited to have you on board. To get started, please complete your lifestyle quiz " +
                        "to help us find the perfect roommate matches for you.\n\n" +
                        "If you have any questions, feel free to reach out.\n\n" +
                        "Best regards,\n" +
                        "RoommateFinder Team",
                fullName
        );

        sendEmail(toEmail, subject, body);
    }

    public void sendInterestNotification(String toEmail, String ownerName,
                                         String interestedUserName, String interestedUserEmail,
                                         String interestedUserPhone, String listingTitle,
                                         Integer compatibilityScore, String message) {
        String subject = interestedUserName + " is interested in your listing!";

        StringBuilder body = new StringBuilder();
        body.append(String.format("Hi %s,\n\n", ownerName));
        body.append(String.format("%s is interested in your listing: %s\n\n",
                interestedUserName, listingTitle));

        if (compatibilityScore != null) {
            body.append(String.format("Compatibility Score: %d%%\n\n", compatibilityScore));
        }

        if (message != null && !message.isEmpty()) {
            body.append(String.format("Message from %s:\n\"%s\"\n\n", interestedUserName, message));
        }

        body.append("Contact Details:\n");
        body.append(String.format("- Email: %s\n", interestedUserEmail));
        body.append(String.format("- Phone: %s\n\n", interestedUserPhone));

        body.append("You can contact them directly via email or phone.\n\n");
        body.append("Best regards,\n");
        body.append("RoommateFinder Team");

        sendEmail(toEmail, subject, body.toString());
    }

    public void sendInterestConfirmation(String toEmail, String userName,
                                         String ownerEmail, String ownerPhone,
                                         String listingTitle) {
        String subject = "Your interest has been sent - " + listingTitle;

        String body = String.format(
                "Hi %s,\n\n" +
                        "Your interest in the listing \"%s\" has been successfully sent to the owner.\n\n" +
                        "Owner's Contact Details:\n" +
                        "- Email: %s\n" +
                        "- Phone: %s\n\n" +
                        "The owner will be notified via email and can contact you directly.\n\n" +
                        "Best regards,\n" +
                        "RoommateFinder Team",
                userName, listingTitle, ownerEmail, ownerPhone
        );

        sendEmail(toEmail, subject, body);
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            // Don't throw exception - email failure shouldn't break the flow
        }
    }
}