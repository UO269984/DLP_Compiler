package ast.types;

import ast.Type;
import ast.ASTNode;
import ast.util.TypesUtil;

import visitor.Visitor;
import util.ErrorMSG;

public class CharType extends AbstractType {
	
	CharType() {}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public boolean isBuiltInType() {
		return true;
	}
	
	@Override
	public Type comparison(Type type, ASTNode node) {
		return TypesUtil.checkSameTypes(this, type, node, "typeError.comparison");
	}
	
	@Override
	public Type promotesTo(Type type, ASTNode node) {
		return TypesUtil.checkSameTypes(this, type, node, "typeError.promotesTo");
	}
	
	@Override
	public Type canBeCastTo(Type type, ASTNode node) {
		return TypesUtil.checkCast(this, type, node);
	}
	
	@Override
	public String toString() {
		return "char";
	}
}