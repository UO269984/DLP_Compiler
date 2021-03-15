package ast.expresions;

import ast.Expresion;
import visitor.Visitor;

public class StructAccess extends UnaryExpresion {
	private String fieldName;
	
	public StructAccess(Expresion struct, String fieldName, int line, int column) {
		super(struct, line, column);
		this.fieldName = fieldName;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}