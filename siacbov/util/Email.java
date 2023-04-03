
package br.edu.cio.util;

import br.edu.cio.model.Destinatario;
import java.util.List;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Email {
    public static void enviar(List<Destinatario> destinatarios, String titulo, String msg){
        SimpleEmail email = new SimpleEmail();
        email.setSSLOnConnect(true);
        email.setHostName( "smtp.gmail.com" );
        email.setSslSmtpPort( "465" );
        email.setAuthenticator( new DefaultAuthenticator( "ti.araguatins@ifto.edu.br" ,  "T1c0rr3!o" ) );
        try {
            email.setFrom( "ti.araguatins@ifto.edu.br");
            email.setDebug(true);
            email.setSubject(titulo);
            email.setMsg(msg);
            for(Destinatario d : destinatarios){
                email.addTo(d.getEmail());
            }
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
