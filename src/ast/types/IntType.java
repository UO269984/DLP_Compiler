package ast.types;

import visitor.Visitor;

public class IntType extends AbstractType {
	
	IntType() {}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}