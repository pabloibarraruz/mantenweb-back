package cl.hospital.mantenimientos.service;

public interface EmailService {
    void enviar(String to, String subject, String body);
}
