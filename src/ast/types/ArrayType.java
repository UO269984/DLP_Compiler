package ast.types;

import ast.Type;
import visitor.Visitor;

public class ArrayType extends ComposedType {
	private int size;
	
	public ArrayType(Type type, int size) {
		super(type);
		if (size < 0)
			throw new IllegalArgumentException("size negativo");
		
		this.size = size;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}