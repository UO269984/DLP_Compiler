package ast.expresions;

import visitor.Visitor;

public class IntLiteral extends AbstractExpression {
	private int intNum;
	
	public IntLiteral(int intNum, int line, int column) {
		super(line, column);
		this.intNum = intNum;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}