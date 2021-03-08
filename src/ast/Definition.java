package ast;

public interface Definition extends ASTNode {
	public Type getType();
	public String getName();
}