package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class UnaryMinus extends UnaryExpression {
	
	public UnaryMinus(Expression expression, int line, int column) {
		super(expression, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}