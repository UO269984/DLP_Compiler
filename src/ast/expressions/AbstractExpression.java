package ast.expressions;

import ast.Type;
import ast.PosASTNode;
import ast.Expression;

public abstract class AbstractExpression extends PosASTNode implements Expression {
	private boolean lValue;
	private Type type;
	
	public AbstractExpression(int line, int column) {
		super(line, column);
		this.lValue = false;
	}
	
	@Override
	public boolean getLValue() {
		return this.lValue;
	}
	
	@Override
	public void setLValue(boolean lValue) {
		this.lValue = lValue;
	}
	
	@Override
	public Type getType() {
		return this.type;
	}
	
	@Override
	public void setType(Type type) {
		this.type = type;
	}
}