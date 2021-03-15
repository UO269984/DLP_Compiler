package ast.expresions;

import ast.Expresion;
import visitor.Visitor;

public class UnaryMinus extends UnaryExpresion {
	
	public UnaryMinus(Expresion expresion, int line, int column) {
		super(expresion, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}