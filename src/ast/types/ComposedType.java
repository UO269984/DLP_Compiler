package ast.types;

import ast.NoPosASTNode;
import ast.Type;

public abstract class ComposedType extends NoPosASTNode implements Type {
	private Type type;
	
	public ComposedType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
}