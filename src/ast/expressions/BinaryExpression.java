package ast.expressions;

import ast.Expression;

public abstract class BinaryExpression extends AbstractExpression {
	private Expression expression1;
	private Expression expression2;
	
	public BinaryExpression(Expression expression1, Expression expression2, int line, int column) {
		super(line, column);
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	
	public Expression getExpression1() {
		return this.expression1;
	}
	
	public Expression getExpression2() {
		return this.expression2;
	}
}