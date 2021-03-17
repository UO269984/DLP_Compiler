package ast;

public interface Expresion extends ASTNode {
	public boolean getLValue();
	public void setLValue(boolean lValue);
}