package ast.types;

import ast.Type;
import ast.NoPosASTNode;

public class ArrayType extends NoPosASTNode implements Type {
	private Type type;
	private int size;
	
	public ArrayType(Type type, int size) {
		if (size < 0)
			throw new IllegalArgumentException("size negativo");
		
		this.type = type;
		this.size = size;
	}
}