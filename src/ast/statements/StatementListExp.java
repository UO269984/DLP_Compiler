package ast.statements;

import java.util.List;
import java.util.ArrayList;

import ast.PosASTNode;
import ast.Statement;
import ast.Expresion;

public abstract class StatementListExp extends PosASTNode implements Statement {
	private List<Expresion> expresions;
	
	public StatementListExp(List<Expresion> expresions, int line, int column) {
		super(line, column);
		this.expresions = expresions;
	}
	
	public List<Expresion> getExpresions() {
		return this.expresions;
	}
}