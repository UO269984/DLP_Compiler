package ast.types;

import ast.Type;

public class RecordField {
	private String name;
	private Type type;
	private int offset;
	
	public RecordField(String name, Type type, int offset) {
		if (offset < 0)
			throw new IllegalArgumentException("Offset negativo");
		
		this.name = name;
		this.type = type;
		this.offset = offset;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
}