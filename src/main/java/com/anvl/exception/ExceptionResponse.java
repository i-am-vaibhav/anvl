package com.anvl.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

	private String msg;
	private String response;
	private LocalDateTime timestamp;

}
