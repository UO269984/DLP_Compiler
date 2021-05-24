package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class Comparison extends BinaryOperation {
	
	public Comparison(Expression left, Expression right, String operand, int line, int column) {
		super(left, right, operand, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}