package util;

import java.io.IOException;
import java.util.Properties;

public class ErrorMSG {
	
	public static final String MESSAGES_FILE = "errors.properties";
	public static Properties messages;
	
	static {
		messages = new Properties();
		try {
			messages.load(ErrorMSG.class.getResourceAsStream(MESSAGES_FILE));
		} catch (IOException e) {
			throw new RuntimeException("Error loading errors file");
		}
	}
	
	public static String getMsg(String msgName) {
		String msg = messages.getProperty(msgName);
		if (msg == null)
			throw new IllegalArgumentException("Message not registered");
		
		return msg;
	}
	
	public static String getMsg(String msgName, String... args) {
		return String.format(getMsg(msgName), (Object[]) args);
	}
	
	public static String getMsg(String msgName, Object... args) {
		return String.format(getMsg(msgName), args);
	}
}