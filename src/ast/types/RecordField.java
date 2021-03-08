package ast.types;

import ast.Type;

public class RecordField {
	private Type type;
	private String name;
	private int offset;
	
	public RecordField(String name, Type type, int offset) {
		if (offset < 0)
			throw new IllegalArgumentException("Offset negativo");
		
		this.name = name;
		this.type = type;
		this.offset = offset;
	}
}