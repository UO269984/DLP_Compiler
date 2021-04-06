package ast;

public abstract class PosASTNode implements ASTNode {
	private int line;
	private int column;
	
	public PosASTNode() {
		this(0, 0);
	}
	
	public PosASTNode(int line, int column) {
		if (line < 0)
			throw new IllegalArgumentException("Línea negativa");
		
		else if (column < 0)
			throw new IllegalArgumentException("Colúmna negativa");
		
		this.line = line;
		this.column = column;
	}
	
	public int getLine() {
		return this.line;
	}
	
	public int getColumn() {
		return this.column;
	}
}