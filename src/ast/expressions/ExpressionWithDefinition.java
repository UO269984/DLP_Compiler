package ast.expressions;

import ast.Definition;

public abstract class ExpressionWithDefinition extends AbstractExpression {
	private String name;
	private Definition definition;
	
	public ExpressionWithDefinition(String name, int line, int column) {
		super(line, column);
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Definition getDefinition() {
		return this.definition;
	}
	
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
}