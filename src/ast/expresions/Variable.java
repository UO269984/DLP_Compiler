package ast.expresions;

import ast.Definition;
import visitor.Visitor;

public class Variable extends AbstractExpression {
	private String name;
	private Definition definition;
	
	public Variable(String name, int line, int column) {
		super(line, column);
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}