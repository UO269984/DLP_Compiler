package ast.types;

import ast.Type;
import ast.ASTNode;
import ast.util.TypesUtil;

import visitor.Visitor;
import util.ErrorMSG;

public class IntType extends AbstractType {
	
	IntType() {}
	
	@Override
	public int numberOfBytes() {
		return 2;
	}
	
	@Override
	public char suffix() {
		return 'i';
	}
	
	@Override
	public int getCastId() {
		return 1;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public boolean isLogical() {
		return true;
	}
	
	@Override
	public boolean isBuiltInType() {
		return true;
	}
	
	@Override
	public Type arithmetic(Type type, ASTNode node) {
		return TypesUtil.checkSameTypes(this, type, node, "typeError.arithmetic");
	}
	
	@Override
	public Type arithmetic(ASTNode node) {
		return Types.getInt();
	}
	
	@Override
	public Type comparison(Type type, ASTNode node) {
		return TypesUtil.checkSameTypes(this, type, node, "typeError.comparison");
	}
	
	@Override
	public Type logic(Type type, ASTNode node) {
		return TypesUtil.checkSameTypes(this, type, node, "typeError.logic.binary");
	}
	
	@Override
	public Type logic(ASTNode node) {
		return Types.getInt();
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
		return "int";
	}
}