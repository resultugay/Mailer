import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * Author: Resul Tugay (resultugay@hotmail.com) * 
 * */


public class mailer {

	public static void main(String[] args) {
		

		
		final String username = "tugayr@itu.edu.tr";//username
		final String password = "------------";            //password
		List<String> names = new ArrayList<String>();
		List<String> surNames = new ArrayList<String>();
		List<String> emails = new ArrayList<String>();

		
		//mail body. Html form
		StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader("files/content.html"));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
			System.out.println("Error File");	
			System.exit(1);
		}
		String content = contentBuilder.toString();
		
		
		
		//names are being read from the ".txt" file
		/* info.txt
		 * Resul Tugay tugayr@itu.edu.tr
		 * Resul Tugay resultugay@hotmail.com		 * 
		 * */
	    try {
			Scanner namesFile = new Scanner(new File("files/info.txt"));
			while (namesFile.hasNext()) {
				names.add(namesFile.next());
				surNames.add(namesFile.next());
				emails.add(namesFile.next());
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Error Names");	
			System.exit(1);
		}

		
		

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
			
		
			
		for(int i = 0; i < names.size() ; i++ ){

			//Message message = new MimeMessage(session);
			MimeMessage message = new MimeMessage(session); //for html 
			message.setFrom(new InternetAddress("tugayr@itu.edu.tr")); //from 
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emails.get(i))); //to
			message.setSubject("Subject"); //subject
		
			content = content.replaceAll("XXX", names.get(i) +" " + surNames.get(i));
			message.setText(content,"utf-8", "html"); // mail

			Transport.send(message);
			content = content.replaceAll(names.get(i) +" " + surNames.get(i),"XXX");
			System.out.println("Message sent for " + names.get(i) +" " + surNames.get(i));
		}

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		
	}
}
