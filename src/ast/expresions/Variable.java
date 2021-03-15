package ast.expresions;

import ast.Expresion;
import ast.PosASTNode;
import visitor.Visitor;

public class Variable extends PosASTNode implements Expresion {
	private String name;
	
	public Variable(String name, int line, int column) {
		super(line, column);
		this.name = name;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}