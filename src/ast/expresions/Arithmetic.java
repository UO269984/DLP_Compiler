package ast.expresions;

import ast.Expresion;
import visitor.Visitor;

public class Arithmetic extends BinaryExpresion {
	private String operator;
	
	public Arithmetic(Expresion left, Expresion right, String operator, int line, int column) {
		super(left, right, line, column);
		this.operator = operator;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}