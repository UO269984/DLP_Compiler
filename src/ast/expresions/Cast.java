package ast.expresions;

import ast.Expresion;
import ast.Type;

public class Cast extends UnaryExpresion {
	private Type castType;
	
	public Cast(Expresion expresion, Type castType, int line, int column) {
		super(expresion, line, column);
		this.castType = castType;
	}
}