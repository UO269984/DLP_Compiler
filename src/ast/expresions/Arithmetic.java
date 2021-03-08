package ast.expresions;

import ast.Expresion;

public class Arithmetic extends BinaryExpresion {
	private String operator;
	
	public Arithmetic(Expresion left, Expresion right, String operator, int line, int column) {
		super(left, right, line, column);
		this.operator = operator;
	}
}