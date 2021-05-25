package ast.types;

import java.util.List;

import ast.Expression;
import ast.Type;
import ast.ASTNode;

import errorhandler.EH;
import visitor.Visitor;

public class ErrorType extends AbstractType {
	private String message;
	
	public ErrorType(String message, int line, int column) {
		super(line, column);
		this.message = message;
		
		EH.getEH().addError(this);
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
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public Type arithmetic(Type type, ASTNode node) {
		return this;
	}
	
	@Override
	public Type arithmetic(ASTNode node) {
		return this;
	}
	
	@Override
	public Type comparison(Type type, ASTNode node) {
		return this;
	}
	
	@Override
	public Type logic(Type type, ASTNode node) {
		return this;
	}
	
	@Override
	public Type logic(ASTNode node) {
		return this;
	}
	
	@Override
	public Type promotesTo(Type type, ASTNode node) {
		return this;
	}
	
	@Override
	public Type canBeCastTo(Type type, ASTNode node) {
		return this;
	}
	
	@Override
	public Type arrayAccess(Type type, ASTNode node) {
		return this;
	}
	
	@Override
	public Type structAccess(String type, ASTNode node) {
		return this;
	}
	
	@Override
	public Type funcCall(List<Expression> params, ASTNode node) {
		return this;
	}
	
	@Override
	public String toString() {
		//Usamos la columna + 1 para que la priemra sea 1, en vez de 0
		return "Error - " + getLine() + ":" + (getColumn() + 1) + " - " + this.message;
	}
}