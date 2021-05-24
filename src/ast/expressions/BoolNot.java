package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class BoolNot extends UnaryExpression {
	
	public BoolNot(Expression expression, int line, int column) {
		super(expression, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}