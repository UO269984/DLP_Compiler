package ast.expresions;

import ast.Expresion;

public abstract class UnaryExpresion extends AbstractExpression {
	private Expresion expresion;
	
	public UnaryExpresion(Expresion expresion, int line, int column) {
		super(line, column);
		this.expresion = expresion;
	}
	
	public Expresion getExpresion() {
		return this.expresion;
	}
}