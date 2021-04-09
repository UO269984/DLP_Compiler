package ast.types;

import visitor.Visitor;

public class VoidType extends AbstractType {
	
	VoidType() {}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public String toString() {
		return "void";
	}
}