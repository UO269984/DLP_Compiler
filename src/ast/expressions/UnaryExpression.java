package ast.expressions;

import ast.Expression;

public abstract class UnaryExpression extends AbstractExpression {
	private Expression expression;
	
	public UnaryExpression(Expression expression, int line, int column) {
		super(line, column);
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return this.expression;
	}
}