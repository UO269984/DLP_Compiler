package ast.types;

import visitor.Visitor;

public class DoubleType extends AbstractType {
	
	DoubleType() {}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}