package ast.statements;

import ast.PosASTNode;
import ast.Statement;
import ast.Expression;
import visitor.Visitor;

public class VarAssigment extends PosASTNode implements Statement {
	private Expression toAsign;
	private Expression value;
	
	public VarAssigment(Expression toAsign, Expression value, int line, int column) {
		super(line, column);
		this.toAsign = toAsign;
		this.value = value;
	}
	
	public Expression getToAsign() {
		return this.toAsign;
	}
	
	public Expression getValue() {
		return this.value;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}