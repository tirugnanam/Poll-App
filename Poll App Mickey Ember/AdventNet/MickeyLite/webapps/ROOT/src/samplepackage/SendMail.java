package samplepackage;

//import java.security.Security;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail
{
	private static final String SMTP_HOST_NAME = "smtp.zoho.com";
	private static final String SMTP_PORT = "465";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

//					Don't delete these lines. For program testing purpose
//	private static final String emailMsgTxt = "Test Message Contents";
//	private static final String emailSubjectTxt = "A test from gmail";
//	private static final String emailFromAddress = "tirugnanam.m@zohocorp.com";
//	private static final String[] sendTo = {"subash.arun@zohocorp.com"};


//	public static void main(String args[]) throws Exception
//	{
//		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//
//		new SendMail().sendSSLMessage(sendTo, emailSubjectTxt, emailMsgTxt, emailFromAddress);
//		System.out.println("Sucessfully mail to All Users");
//	}

	public String sendSSLMessage(String[] recipients, String[] recipients_name, String subject, String message, String from) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", SMTP_PORT);
			props.put("mail.smtp.startssl.enable", "true");

			props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.socketFactory.port", SMTP_PORT);

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, "9Yy5EbMTax98");
				}
			});
			session.setDebug(true);

			//		InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				InternetAddress[] addressTo = new InternetAddress[1];    //only one element in array
				addressTo[0] = new InternetAddress(recipients[i]);

				Message msg = new MimeMessage(session);
				InternetAddress addressFrom = new InternetAddress(from);
				msg.setFrom(addressFrom);

				msg.setRecipients(Message.RecipientType.TO, addressTo);
				msg.setSubject(subject);

				String email_msg = "Hi <b>" + recipients_name[i] + "</b>," + message;
				msg.setContent(email_msg, "text/html");

				Transport transport = session.getTransport("smtp");
				transport.connect(SMTP_HOST_NAME, SMTP_PORT, from);
				transport.sendMessage(msg, addressTo);
				transport.close();
			}
			return "Mail Successfully sent";
		}
		catch (Exception e)
		{
			return "Mail NOT sent. Error msg -> " + e.toString();
		}
	}
}

//9Yy5EbMTax98

