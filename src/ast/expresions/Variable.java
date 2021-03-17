package ast.expresions;

import visitor.Visitor;

public class Variable extends AbstractExpression {
	private String name;
	
	public Variable(String name, int line, int column) {
		super(line, column);
		this.name = name;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}