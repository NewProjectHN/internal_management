package Com.IFI.InternalTool.Utils;

import com.sun.mail.smtp.SMTPMessage;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailThread extends Thread {

    private String toEmail;
    private String header;
    private MimeMultipart content;

    public MailThread(String _toEmail, String _header, MimeMultipart _content) {
        this.toEmail = _toEmail;
        this.header = _header;
        this.content = _content;
    }
    @Override
    public void run() {
        MailThread.sendMail(toEmail, header, content);
    }
    public static boolean sendMail(String toEmail, String header, MimeMultipart content) {

        final String username = "valueteam\\NguyenDucL";
        final String password = "Ifi@092018";

        Properties props = new Properties();
        props.setProperty("proxySet", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.nttdata-emea.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            SMTPMessage message = new SMTPMessage(session);
            message.setFrom(new InternetAddress("long11.nguyenduc@nttdata.com","dfddf","UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(header);
            message.setContent(content);
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) {
        MimeMultipart content = new MimeMultipart();
        MimeBodyPart textPart = new MimeBodyPart();

        try {
            String text = "TEST TEXT";
            textPart.setText(text, "UTF-8", "html");
            content.addBodyPart(textPart);
            new MailThread("long11.nguyenduc@nttdata.com", "TEST", content).run();
            System.out.println("SEND!!!!!!");
        } catch (Exception ex){

        }
    }
}
