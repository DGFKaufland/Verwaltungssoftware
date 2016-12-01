package services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class SMTP {
	
	
	public static void sendMail()
    {

		final String username = "kaufland.sensoren@gmail.com";
		final String password = "Abcde4$5%";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("kaufland.sensoren@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("kaufland.leitstand@gmail.com"));
			message.setSubject("");
			message.setText("");
			Transport.send(message);

			System.out.println("Mail erfolgreich gesendet");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		
    }
	
}
