package ast;

import java.util.List;
import java.util.ArrayList;
import visitor.Visitor;

public class Program extends PosASTNode {
	private List<Definition> definitions;
	
	public Program() {
		this.definitions = new ArrayList<Definition>();
	}
	
	public List<Definition> getDefinitions() {
		return this.definitions;
	}
	
	public void add(Definition newDefinition) {
		this.definitions.add(newDefinition);
	}
	
	public void addVarsDef(List<VarDefinition> newDefinitions) {
		this.definitions.addAll(newDefinitions);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}