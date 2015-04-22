package com.notification.letter;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.liferay.portal.model.User;

@Component
public class NotificationService {
	
	private static final String SEND_FROM = "arinapodoba@gmail.com";
	private static final String SUBJECT = "Public Utility";

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void sendConfirmationEmail(final User user) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(SEND_FROM);
                message.setTo(user.getEmailAddress());
                Map<String, User> model = new HashMap<String, User>();
                model.put("user", user);
                message.setSubject(SUBJECT);
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/notification/letter/notification.vm", model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }

}
