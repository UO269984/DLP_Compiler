package ast.expressions;

import visitor.Visitor;

public class EmptyExpression extends AbstractExpression {
	
	public EmptyExpression(int line, int column) {
		super(line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}