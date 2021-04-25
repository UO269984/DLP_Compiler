package ast.types;

import ast.ASTNode;
import ast.PosASTNode;
import ast.Type;
import ast.Expresion;

import util.ErrorMSG;

import java.util.List;

public abstract class AbstractType extends PosASTNode implements Type {
	
	public AbstractType() {
	}
	
	public AbstractType(int line, int column) {
		super(line, column);
	}
	
	@Override
	public int numberOfBytes() {
		return 0;
	}
	
	@Override
	public char suffix() {
		throw new IllegalStateException("Type without suffix");
	}
	
	@Override
	public int getCastId() {
		throw new IllegalStateException("Type without cast id");
	}
	
	@Override
	public boolean isLogical() {
		return false;
	}
	
	@Override
	public boolean isBuiltInType() {
		return false;
	}
	
	@Override
	public Type arithmetic(Type type, ASTNode node) {
		return createError(type, node, "typeError.arithmetic");
	}
	
	@Override
	public Type arithmetic(ASTNode node) {
		return createErrorOneType(node, "typeError.arithmeticUnary");
	}
	
	@Override
	public Type comparison(Type type, ASTNode node) {
		return createError(type, node, "typeError.comparison");
	}
	
	@Override
	public Type logic(Type type, ASTNode node) {
		return createError(type, node, "typeError.logic.binary");
	}
	
	@Override
	public Type logic(ASTNode node) {
		return createErrorOneType(node, "typeError.logic.unary");
	}
	
	@Override
	public Type promotesTo(Type type, ASTNode node) {
		return createError(type, node, "typeError.promotesTo");
	}
	
	@Override
	public Type canBeCastTo(Type type, ASTNode node) {
		return createError(type, node, "typeError.cast");
	}
	
	@Override
	public Type arrayAccess(Type type, ASTNode node) {
		return createErrorOneType(node, "typeError.arrayAccess.array");
	}
	
	@Override
	public Type structAccess(String fieldName, ASTNode node) {
		return createErrorOneType(node, "typeError.structAccess");
	}
	
	@Override
	public Type funcCall(List<Expresion> params, ASTNode node) {
		return createErrorOneType(node, "typeError.funcCall");
	}
	
	private Type createErrorOneType(ASTNode node, String msgName) {
		return new ErrorType(ErrorMSG.getMsg(msgName, toString()), node.getLine(), node.getColumn());
	}
	
	private Type createError(Type type, ASTNode node, String msgName) {
		if (type instanceof ErrorType)
			return this;
		else
			return new ErrorType(ErrorMSG.getMsg(msgName, toString(), type.toString()), node.getLine(), node.getColumn());
	}
}