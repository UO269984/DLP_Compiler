package ast;

public class VarDefinition extends AbstractDefinition {
	private int offset;
	
	public VarDefinition(String name, int line, int column) {
		super(name, null, line, column);
	}
	
	public VarDefinition(String name, Type type, int line, int column) {
		super(name, type, line, column);
	}
}