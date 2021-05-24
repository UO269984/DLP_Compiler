package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class StructAccess extends UnaryExpression {
	private String fieldName;
	
	public StructAccess(Expression struct, String fieldName, int line, int column) {
		super(struct, line, column);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}