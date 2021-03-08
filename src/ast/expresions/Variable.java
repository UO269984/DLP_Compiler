package ast.expresions;

import ast.Expresion;
import ast.PosASTNode;

public class Variable extends PosASTNode implements Expresion {
	private String name;
	
	public Variable(String name, int line, int column) {
		super(line, column);
		this.name = name;
	}
}