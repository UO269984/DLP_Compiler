package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class ArrayAccess extends BinaryExpression {
	
	public ArrayAccess(Expression array, Expression indexExp, int line, int column) {
		super(array, indexExp, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}