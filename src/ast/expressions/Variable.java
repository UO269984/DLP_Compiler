package ast.expressions;

import visitor.Visitor;

public class Variable extends ExpressionWithDefinition {
	
	public Variable(String name, int line, int column) {
		super(name, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}