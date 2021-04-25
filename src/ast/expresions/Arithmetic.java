package ast.expresions;

import ast.Expresion;
import visitor.Visitor;

public class Arithmetic extends BinaryOperation {
	
	public Arithmetic(Expresion left, Expresion right, String operator, int line, int column) {
		super(left, right, operator, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}