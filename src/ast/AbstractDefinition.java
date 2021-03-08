package ast;

public abstract class AbstractDefinition extends PosASTNode implements Definition {
	private String name;
	private Type type;
	
	public AbstractDefinition(String name, Type type, int line, int column) {
		super(line, column);
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
}