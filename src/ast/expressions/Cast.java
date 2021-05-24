package ast.expressions;

import ast.Expression;
import ast.Type;
import visitor.Visitor;

public class Cast extends UnaryExpression {
	private Type castType;
	
	public Cast(Expression expression, Type castType, int line, int column) {
		super(expression, line, column);
		this.castType = castType;
	}
	
	public Type getCastType() {
		return this.castType;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	public static int getCastId(Type expType, Type castType) {
		return (expType.getCastId() << 2) | castType.getCastId();
	}
}