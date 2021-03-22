package ast;

public interface Expresion extends ASTNode {
	public boolean getLValue();
	public void setLValue(boolean lValue);
	public Type getType();
	public void setType(Type type);
}