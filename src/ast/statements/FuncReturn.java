package ast.statements;

import ast.PosASTNode;
import ast.Statement;
import ast.Expresion;

public class FuncReturn extends PosASTNode implements Statement {
	private Expresion retExp;
	
	public FuncReturn(Expresion retExp, int line, int column) {
		super(line, column);
		this.retExp = retExp;
	}
}