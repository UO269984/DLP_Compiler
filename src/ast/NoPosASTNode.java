package ast;

public class NoPosASTNode implements ASTNode {
	
	@Override
	public int getLine() {
		return 0;
	}
	
	@Override
	public int getColumn() {
		return 0;
	}
}