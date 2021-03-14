package ast;

import java.util.List;
import java.util.LinkedList;

import ast.types.VoidType;

public class FuncDefinition extends AbstractDefinition implements Definition {
	
	private List<VarDefinition> varDefinitions;
	private List<Statement> statements;
	
	public FuncDefinition(String name, Type funcType, int line, int column) {
		super(name, funcType, line, column);
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