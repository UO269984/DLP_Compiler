package ast;

import java.util.List;

public interface Type extends ASTNode {
	
	public boolean isLogical();
	public boolean isBuiltInType();
	public Type arithmetic(Type type, ASTNode node);
	public Type arithmetic(ASTNode node);
	public Type comparison(Type type, ASTNode node);
	public Type logic(Type type, ASTNode node);
	public Type logic(ASTNode node);
	public Type promotesTo(Type type, ASTNode node);
	public Type canBeCastTo(Type type, ASTNode node);
	public Type arrayAccess(Type type, ASTNode node);
	public Type structAccess(String fieldName, ASTNode node);
	public Type funcCall(List<Expresion> params, ASTNode node);
}