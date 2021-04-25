package ast.types;

import ast.types.Types;
import ast.Type;
import ast.ASTNode;
import ast.util.TypesUtil;

import visitor.Visitor;
import util.ErrorMSG;

public class DoubleType extends AbstractType {
	
	DoubleType() {}
	
	@Override
	public int numberOfBytes() {
		return 4;
	}
	
	@Override
	public char suffix() {
		return 'f';
	}
	
	@Override
	public int getCastId() {
		return 2;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
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
		return Types.getDouble();
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
		return "double";
	}
}