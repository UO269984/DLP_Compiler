package ast;

import visitor.Visitor;

public class VarDefinition extends AbstractDefinition {
	private int offset;
	
	public VarDefinition(String name, int line, int column) {
		super(name, null, line, column);
	}
	
	public VarDefinition(String name, Type type, int line, int column) {
		super(name, type, line, column);
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}