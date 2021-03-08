package ast.types;

import ast.Type;
import ast.PosASTNode;
import errorhandler.EH;

public class ErrorType extends PosASTNode implements Type {
	private String message;
	
	public ErrorType(String message, int line, int column) {
		super(line, column);
		this.message = message;
		
		EH.getEH().addError(this);
	}
	
	@Override
	public String toString() {
		return "Error - " + getLine() + ":" + getColumn() + " - " + this.message;
	}
}