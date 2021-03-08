package ast.expresions;

import ast.Expresion;

public class Comparison extends BinaryExpresion {
	private String operand;
	
	public Comparison(Expresion left, Expresion right, String operand, int line, int column) {
		super(left, right, line, column);
		this.operand = operand;
	}
}