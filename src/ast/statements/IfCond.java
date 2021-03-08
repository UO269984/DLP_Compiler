package ast.statements;

import java.util.List;
import java.util.ArrayList;

import ast.Expresion;
import ast.Statement;

public class IfCond extends StatementWithExp {
	private List<Statement> ifStatements;
	private List<Statement> elseStatements;
	
	public IfCond(Expresion cond, List<Statement> ifStatements, int line, int column) {
		super(cond, line, column);
		this.ifStatements = ifStatements;
		this.elseStatements = new ArrayList<Statement>();
	}
	
	public void setElseStatements(List<Statement> statements) {
		this.elseStatements = statements;
	}
}