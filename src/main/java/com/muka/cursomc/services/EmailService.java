package com.muka.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.muka.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
		
	
	
}
