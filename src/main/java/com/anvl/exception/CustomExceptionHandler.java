/**
 * 
 */
package com.anvl.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.anvl.service.MessageService;

import io.jsonwebtoken.JwtException;

/**
 * @author Vaibhav
 *
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageService messageService;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception exception) {
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(exception.getLocalizedMessage(),
						messageService.getMsg("internal.server.error.msg"), LocalDateTime.now().toString()));
		return responseEntity;
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleException(UsernameNotFoundException exception) {
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.body(new ExceptionResponse(messageService.getMsg("user.not.found.ex.msg"),
						messageService.getMsg("authentication.failed"), LocalDateTime.now().toString()));
		return responseEntity;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ObjectError> allErrors = ex.getAllErrors();
		StringBuilder builder = new StringBuilder();
		allErrors.forEach(error -> builder.append(messageService.getMsg(error.getDefaultMessage())).append(", "));
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(builder.toString(), messageService.getMsg("invalid.request.msg"),
						LocalDateTime.now().toString()));
		return responseEntity;
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handle(MethodArgumentTypeMismatchException ex) {
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(
						messageService.getMsg("method.argument.type.exception.msg", ex.getRequiredType(),
								ex.getRootCause().getLocalizedMessage()),
						messageService.getMsg("invalid.request.msg"), LocalDateTime.now().toString()));
		return responseEntity;
	}

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<Object> handle(AuthenticationException ex) {
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ExceptionResponse(messageService.getMsg(ex.getLocalizedMessage()),
						messageService.getMsg("authentication.failed"), LocalDateTime.now().toString()));
		return responseEntity;
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<Object> handle(JwtException ex) {
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ExceptionResponse(messageService.getMsg(ex.getLocalizedMessage()),
						messageService.getMsg("authentication.failed"), LocalDateTime.now().toString()));
		return responseEntity;
	}

}
