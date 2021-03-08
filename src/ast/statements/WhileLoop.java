package ast.statements;

import java.util.List;
import java.util.ArrayList;

import ast.Expresion;
import ast.Statement;

public class WhileLoop extends StatementWithExp {
	private List<Statement> whileStatements;
	
	public WhileLoop(Expresion whileCond, List<Statement> whileStatements, int line, int column) {
		super(whileCond, line, column);
		this.whileStatements = whileStatements;
	}
}