package ast.statements;

import java.util.List;
import java.util.ArrayList;

import ast.Expresion;
import ast.Statement;
import visitor.Visitor;

public class IfCond extends StatementWithExp {
	private List<Statement> ifStatements;
	private List<Statement> elseStatements;
	
	public IfCond(Expresion cond, List<Statement> ifStatements, int line, int column) {
		super(cond, line, column);
		this.ifStatements = ifStatements;
		this.elseStatements = new ArrayList<Statement>();
	}
	
	public List<Statement> getIfStatements() {
		return this.ifStatements;
	}
	
	public List<Statement> getElseStatements() {
		return this.elseStatements;
	}
	
	public void setElseStatements(List<Statement> statements) {
		this.elseStatements = statements;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}