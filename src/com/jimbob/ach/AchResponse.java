package com.jimbob.ach;

import java.util.Date;
import java.util.List;

public class AchResponse {
	public Date timeStamp;
	public String traceCode;
	public AchStatus status;
	public List<String> errorMessage;
}
