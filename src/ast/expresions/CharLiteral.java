package ast.expresions;

import visitor.Visitor;

public class CharLiteral extends AbstractExpression {
	private char character;
	
	public CharLiteral(char character, int line, int column) {
		super(line, column);
		this.character = character;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}