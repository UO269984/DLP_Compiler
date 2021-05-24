package ast.expressions;

import ast.Expression;

public abstract class BinaryOperation extends BinaryExpression {
	private String operator;
	
	public BinaryOperation(Expression left, Expression right, String operator, int line, int column) {
		super(left, right, line, column);
		this.operator = operator;
	}
	
	public String getOperand() {
		return this.operator;
	}
}