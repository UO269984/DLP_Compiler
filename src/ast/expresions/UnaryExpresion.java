package ast.expresions;

import ast.PosASTNode;
import ast.Expresion;

public class UnaryExpresion extends PosASTNode implements Expresion {
	private Expresion expresion;
	
	public UnaryExpresion(Expresion expresion, int line, int column) {
		super(line, column);
		this.expresion = expresion;
	}
}