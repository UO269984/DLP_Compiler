package ast.expresions;

import ast.Expresion;
import visitor.Visitor;

public class LogicOperation extends BinaryExpresion {
	private String operand;
	
	public LogicOperation(Expresion left, Expresion right, String operand, int line, int column) {
		super(left, right, line, column);
		this.operand = operand;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}