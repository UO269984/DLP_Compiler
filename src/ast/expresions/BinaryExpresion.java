package ast.expresions;

import ast.PosASTNode;
import ast.Expresion;

public abstract class BinaryExpresion extends PosASTNode implements Expresion {
	private Expresion expresion1;
	private Expresion expresion2;
	
	public BinaryExpresion(Expresion expresion1, Expresion expresion2, int line, int column) {
		super(line, column);
		this.expresion1 = expresion1;
		this.expresion2 = expresion2;
	}
}