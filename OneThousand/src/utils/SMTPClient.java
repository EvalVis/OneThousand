package utils;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class SMTPClient {
	
	private static final String host = "smtp.gmail.com";
	private static final String port = "587";
	private static String emailAddress;
	private static String emailPassword;
	private static final String subject = "Thousand game";
	private static Message firstMessage;
	private static String text;
	
	public static void send(MatchCapacitor matchCapacitor) {
		try {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", emailAddress);
        properties.put("mail.password", emailPassword);
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        };
        Session session = Session.getInstance(properties, auth);
        Message msg = null;
        if(firstMessage == null) {
        	msg = new MimeMessage(session);
        	firstMessage = msg;
        }
        else msg = firstMessage.reply(false);
        msg.setFrom(new InternetAddress(emailAddress));
        InternetAddress[] toAddresses = { 
        		new InternetAddress(emailAddress) 
        		};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        Multipart multipart = new MimeMultipart();
        if(text != null) {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(text, "text/html");
        multipart.addBodyPart(messageBodyPart);
        }
        List<byte[]> bytes = matchCapacitor.convertToBytes();
        if(bytes != null) {
        	for(int i = 0; i < bytes.size(); i++) {
        		byte[] imageBytes = bytes.get(i);
        		MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent("", "text/html");
                ByteArrayDataSource bds = new ByteArrayDataSource(imageBytes, "image/png"); 
                messageBodyPart.setDataHandler(new DataHandler(bds));
                messageBodyPart.setFileName("#" + (i + 1) + ".png");
                messageBodyPart.setHeader("Content-ID", "<image>");
                multipart.addBodyPart(messageBodyPart);
        	}
        }
        /*MimeBodyPart attachPart = new MimeBodyPart();
        attachPart.attachFile("canvas.png");
        multipart.addBodyPart(attachPart);*/
        msg.setContent(multipart);
        Transport.send(msg);
		}
		catch(Exception e) {
			System.out.println("Failed to send an email.");
		}
	}
	
	public static void setEmail(String emailAddress, String emailPassword) {
		SMTPClient.emailAddress = emailAddress;
		SMTPClient.emailPassword = emailPassword;
	}
	
	public static boolean emailSet() {
		if(emailAddress == null || emailPassword == null) return false;
		if(emailAddress.length() < 1 || emailPassword.length() < 1) return false;
		return true;
	}
	
	public static void setText(String text) {
		SMTPClient.text = text;
	}
	
	public static void setTextWithNames(String[] names) {
		String text = "";
		try {
		text = "Players: " + names[0] + ", " + names[1] + ", " + names[2] + ".";
		}
		catch(Exception e) {
			return;
		}
		SMTPClient.text = text;
	}

}
