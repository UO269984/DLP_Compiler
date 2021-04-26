package ast.types;

import ast.Type;
import ast.ASTNode;
import ast.util.TypesUtil;

import visitor.Visitor;
import util.ErrorMSG;

public class CharType extends AbstractType {
	
	CharType() {}
	
	@Override
	public int numberOfBytes() {
		return 1;
	}
	
	@Override
	public char suffix() {
		return 'b';
	}
	
	@Override
	public int getCastId() {
		return 0;
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
		return TypesUtil.checkSameTypesRetType(this, type, Types.getInt(), node, "typeError.arithmetic");
	}
	
	@Override
	public Type comparison(Type type, ASTNode node) {
		return TypesUtil.checkSameTypesRetType(this, type, Types.getInt(), node, "typeError.comparison");
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