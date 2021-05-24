package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class Arithmetic extends BinaryOperation {
	
	public Arithmetic(Expression left, Expression right, String operator, int line, int column) {
		super(left, right, operator, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}