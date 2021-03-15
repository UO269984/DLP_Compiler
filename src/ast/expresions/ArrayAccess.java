package ast.expresions;

import ast.Expresion;
import visitor.Visitor;

public class ArrayAccess extends BinaryExpresion {
	
	public ArrayAccess(Expresion array, Expresion indexExp, int line, int column) {
		super(array, indexExp, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}