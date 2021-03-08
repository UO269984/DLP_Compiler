package ast.expresions;

import ast.Expresion;

public class BoolNot extends UnaryExpresion {
	
	public BoolNot(Expresion expresion, int line, int column) {
		super(expresion, line, column);
	}
}