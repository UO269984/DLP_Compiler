package errorhandler;

import java.util.List;
import java.util.LinkedList;
import java.io.PrintStream;

import ast.types.ErrorType;

public class EH {
	private static EH handler = new EH();
	
	private List<ErrorType> errors;
	
	private EH() {
		this.errors = new LinkedList<ErrorType>();
	}
	
	public void addError(ErrorType error) {
		this.errors.add(error);
	}
	
	public boolean hasErrors() {
		return ! this.errors.isEmpty();
	}
	
	public void showErrors(PrintStream out) {
		this.errors.forEach(out::println);
	}
	
	public static EH getEH() {
		return handler;
	}
}