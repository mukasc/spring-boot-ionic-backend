package com.muka.cursomc.resources.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.muka.cursomc.services.exceptions.DataIntegrityException;
import com.muka.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
		
    private String getDatahora(Long millis) {
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
    	return strDate;
    }
    
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		String agora = getDatahora(System.currentTimeMillis());
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), agora);		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}	
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		String agora = getDatahora(System.currentTimeMillis());				
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), agora);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		String agora = getDatahora(System.currentTimeMillis());					
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validacao", agora);
		
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
