package ast.types;

import ast.Type;
import ast.ASTNode;

import ast.util.TypesUtil;
import visitor.Visitor;

public class VoidType extends AbstractType {
	
	VoidType() {}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public Type promotesTo(Type type, ASTNode node) {
		return TypesUtil.checkSameTypes(this, type, node, "typeError.promotesTo");
	}
	
	@Override
	public String toString() {
		return "void";
	}
}