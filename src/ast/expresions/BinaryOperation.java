package ast.expresions;

import ast.Expresion;

public abstract class BinaryOperation extends BinaryExpresion {
	private String operator;
	
	public BinaryOperation(Expresion left, Expresion right, String operator, int line, int column) {
		super(left, right, line, column);
		this.operator = operator;
	}
	
	public String getOperand() {
		return this.operator;
	}
}