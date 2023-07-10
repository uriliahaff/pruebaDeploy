package domain.services.wppSender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class TwiloWppSender implements WwpSender {
    // Credenciales de Twilio
    public static final String ACCOUNT_SID = "TU_ACCOUNT_SID";
    public static final String AUTH_TOKEN = "TU_AUTH_TOKEN";
    // Telefono
    public static final String TWILIO_PHONE_NUMBER = "TU_NUMERO_DE_TELEFONO_TWILIO";


    public void enviarWpp(String titulo, String destinatario, String mensaje) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Enviar el mensaje de WhatsApp
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:" + destinatario),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        titulo+ "\n"+ mensaje)
                .create();

        System.out.println("Mensaje enviado. SID del mensaje: " + message.getSid());
    }
}

