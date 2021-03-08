package ast.expresions;

import ast.Expresion;

public class StructAccess extends UnaryExpresion {
	private String fieldName;
	
	public StructAccess(Expresion struct, String fieldName, int line, int column) {
		super(struct, line, column);
		this.fieldName = fieldName;
	}
}