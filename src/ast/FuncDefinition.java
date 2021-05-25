package ast;

import java.util.List;
import java.util.LinkedList;

import ast.types.VoidType;
import visitor.Visitor;

public class FuncDefinition extends AbstractDefinition {
	
	private List<VarDefinition> varDefinitions;
	private int varsSize;
	private List<Statement> statements;
	
	public FuncDefinition(String name, Type funcType, int line, int column) {
		super(name, funcType, line, column);
		this.varDefinitions = new LinkedList<VarDefinition>();
		this.statements = new LinkedList<Statement>();
		this.varsSize = 0;
	}
	
	public List<VarDefinition> getVarDefinitions() {
		return this.varDefinitions;
	}
	
	public int getVarsSize() {
		return this.varsSize;
	}
	
	public void setVarsSize(int varsSize) {
		this.varsSize = varsSize;
	}
	
	public List<Statement> getStatements() {
		return this.statements;
	}
	
	public void addStatement(Statement statement) {
		this.statements.add(statement);
	}
	
	public void addVarsDef(List<VarDefinition> varsDef) {
		this.varDefinitions.addAll(varsDef);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}