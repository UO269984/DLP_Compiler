package ast.statements;

import java.util.List;
import java.util.ArrayList;

import ast.Expression;
import ast.Statement;
import visitor.Visitor;

public class WhileLoop extends StatementWithExp {
	private List<Statement> whileStatements;
	
	public WhileLoop(Expression whileCond, List<Statement> whileStatements, int line, int column) {
		super(whileCond, line, column);
		this.whileStatements = whileStatements;
	}
	
	public List<Statement> getWhileStatements() {
		return this.whileStatements;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}