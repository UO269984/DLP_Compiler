package ast.types;

import ast.Type;

public abstract class ComposedType extends AbstractType {
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