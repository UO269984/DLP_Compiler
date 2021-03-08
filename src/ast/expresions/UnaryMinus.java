package ast.expresions;

import ast.Expresion;

public class UnaryMinus extends UnaryExpresion {
	
	public UnaryMinus(Expresion expresion, int line, int column) {
		super(expresion, line, column);
	}
}