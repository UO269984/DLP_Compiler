package ast.expressions;

import visitor.Visitor;

public class IntLiteral extends AbstractExpression {
	private int intNum;
	
	public IntLiteral(int intNum, int line, int column) {
		super(line, column);
		this.intNum = intNum;
	}
	
	public int getLiteral() {
		return this.intNum;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}