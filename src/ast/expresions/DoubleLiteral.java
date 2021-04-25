package ast.expresions;

import visitor.Visitor;

public class DoubleLiteral extends AbstractExpression {
	private double doubleNum;
	
	public DoubleLiteral(double doubleNum, int line, int column) {
		super(line, column);
		this.doubleNum = doubleNum;
	}
	
	public double getLiteral() {
		return this.doubleNum;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}