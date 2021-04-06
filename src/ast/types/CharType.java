package ast.types;

import visitor.Visitor;

public class CharType extends AbstractType {
	
	CharType() {}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}