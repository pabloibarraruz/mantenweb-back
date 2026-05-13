package cl.hospital.mantenimientos.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ConsoleEmailService implements EmailService {

    @Override
    public void enviar(String to, String subject, String body) {
        System.out.println("==== EMAIL (SIMULADO) ====");
        System.out.println("TO: " + to);
        System.out.println("SUBJECT: " + subject);
        System.out.println("BODY:\n" + body);
        System.out.println("==========================");
    }
}
