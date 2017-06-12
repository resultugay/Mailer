import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mailer {

	public static void main(String[] args) {
		final String username = "tugayr@itu.edu.tr";//username
		final String password = "----------";            //password

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.itu.edu.tr"); //email server
		props.put("mail.smtp.port", "587");	//defaul port

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("tugayr@itu.edu.tr")); //from 
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("tugayr@itu.edu.tr")); //to
			message.setSubject("Testing Subject"); //subject
			message.setText("Mr. Tugay,"
				+ "\n\n This is email."); // mail

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
