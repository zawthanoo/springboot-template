package org.mutu.example.config.dto;

import org.apache.logging.log4j.message.ObjectMessage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogMessage {
	private String process;
	private ObjectMessage payload;
	
	public LogMessage(String process, Object payload) {
		this.process = process;
		this.payload = new ObjectMessage(payload);
	}
}