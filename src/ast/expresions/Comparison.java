package ast.expresions;

import ast.Expresion;
import visitor.Visitor;

public class Comparison extends BinaryOperation {
	
	public Comparison(Expresion left, Expresion right, String operand, int line, int column) {
		super(left, right, operand, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}