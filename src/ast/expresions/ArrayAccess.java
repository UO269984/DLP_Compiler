package ast.expresions;

import ast.Expresion;

public class ArrayAccess extends BinaryExpresion {
	
	public ArrayAccess(Expresion array, Expresion indexExp, int line, int column) {
		super(array, indexExp, line, column);
	}
}