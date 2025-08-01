package org.mutu.example.config.logging;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogHttpResponseEntity {
	private String type;
	private String method;
	private String path;
	private String status;
	private List<LogHeader> headers;
	private Object body;
}
