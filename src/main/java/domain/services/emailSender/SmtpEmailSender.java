package domain.services.emailSender;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SmtpEmailSender implements EmailSender {
    private final String username;
    private final String password;
    private final Properties properties;


    public SmtpEmailSender(String usuario, String password) {
        this.username = usuario;
        this.password = password;

        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP
        properties.put("mail.smtp.port", "587"); // Puerto

    }

    @Override
    public void enviarMail(String titulo, String destinatario, String mensaje) {
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        System.out.println("Enviar el mail: " +mensaje);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(titulo);
            message.setText(mensaje);
            Transport.send(message);
            System.out.println("Correo enviado exitosamente.");
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }
    }

