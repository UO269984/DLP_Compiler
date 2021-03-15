package ast.expresions;

import ast.Expresion;
import visitor.Visitor;

public class Comparison extends BinaryExpresion {
	private String operand;
	
	public Comparison(Expresion left, Expresion right, String operand, int line, int column) {
		super(left, right, line, column);
		this.operand = operand;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}