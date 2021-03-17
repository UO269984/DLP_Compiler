package ast.expresions;

import ast.Expresion;

public abstract class BinaryExpresion extends AbstractExpression {
	private Expresion expresion1;
	private Expresion expresion2;
	
	public BinaryExpresion(Expresion expresion1, Expresion expresion2, int line, int column) {
		super(line, column);
		this.expresion1 = expresion1;
		this.expresion2 = expresion2;
	}
	
	public Expresion getExpresion1() {
		return this.expresion1;
	}
	
	public Expresion getExpresion2() {
		return this.expresion2;
	}
}