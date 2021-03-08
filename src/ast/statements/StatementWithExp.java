package ast.statements;

import ast.PosASTNode;
import ast.Statement;
import ast.Expresion;

public abstract class StatementWithExp extends PosASTNode implements Statement {
	private Expresion expresion;
	
	public StatementWithExp(Expresion expresion, int line, int column) {
		super(line, column);
		this.expresion = expresion;
	}
}