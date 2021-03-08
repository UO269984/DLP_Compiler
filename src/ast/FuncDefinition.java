package ast;

import java.util.List;
import java.util.LinkedList;

import ast.types.VoidType;

public class FuncDefinition extends AbstractDefinition implements Definition {
	
	private List<VarDefinition> params;
	private List<VarDefinition> varDefinitions;
	private List<Statement> statements;
	
	public FuncDefinition(String name, List<VarDefinition> params, int line, int column) {
		super(name, new VoidType(), line, column);
		this.params = params;
		this.varDefinitions = new LinkedList<VarDefinition>();
		this.statements = new LinkedList<Statement>();
	}
	
	public void addStatement(Statement statement) {
		this.statements.add(statement);
	}
	
	public void addVarsDef(List<VarDefinition> varsDef) {
		this.varDefinitions.addAll(varsDef);
	}
}