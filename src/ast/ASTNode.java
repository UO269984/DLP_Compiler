package ast;

import visitor.Visitor;

public interface ASTNode {
	public int getLine();
	public int getColumn();
	public Object accept(Visitor visitor, Object param);
}