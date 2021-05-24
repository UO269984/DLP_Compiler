package ast.statements;

import java.util.List;
import java.util.ArrayList;

import ast.PosASTNode;
import ast.Statement;
import ast.Expression;

public abstract class StatementListExp extends PosASTNode implements Statement {
	private List<Expression> expressions;
	
	public StatementListExp(List<Expression> expressions, int line, int column) {
		super(line, column);
		this.expressions = expressions;
	}
	
	public List<Expression> getExpressions() {
		return this.expressions;
	}
}