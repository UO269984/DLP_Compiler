package ast.types;

import ast.Type;
import ast.NoPosASTNode;
import visitor.Visitor;

public class ArrayType extends NoPosASTNode implements Type {
	private Type type;
	private int size;
	
	public ArrayType(Type type, int size) {
		if (size < 0)
			throw new IllegalArgumentException("size negativo");
		
		this.type = type;
		this.size = size;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}