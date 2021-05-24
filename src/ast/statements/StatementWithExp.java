package ast.statements;

import ast.PosASTNode;
import ast.Statement;
import ast.Expression;

public abstract class StatementWithExp extends PosASTNode implements Statement {
	private Expression expression;
	
	public StatementWithExp(Expression expression, int line, int column) {
		super(line, column);
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return this.expression;
	}
}