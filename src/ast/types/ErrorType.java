package ast.types;

import errorhandler.EH;
import visitor.Visitor;

public class ErrorType extends AbstractType {
	private String message;
	
	public ErrorType(String message, int line, int column) {
		super(line, column);
		this.message = message;
		
		EH.getEH().addError(this);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public String toString() {
		//Usamos la columna + 1 para que la priemra sea 1, en vez de 0
		return "Error - " + getLine() + ":" + (getColumn() + 1) + " - " + this.message;
	}
}