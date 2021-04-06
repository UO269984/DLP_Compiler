package ast.types;

import ast.PosASTNode;
import ast.Type;

public abstract class AbstractType extends PosASTNode implements Type {
	
	public AbstractType() {
	}
	
	public AbstractType(int line, int column) {
		super(line, column);
	}
}