package com.first.error;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorDetail {
	private String title;
	private int status;
	private String detail;
	private long timeStamp;
	private String developerMessage;

}
