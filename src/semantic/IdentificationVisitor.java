package semantic;

import visitor.AbstractVisitor;
import symboltable.SymbolTable;

import ast.Program;
import ast.Definition;
import ast.VarDefinition;
import ast.FuncDefinition;
import ast.expresions.Variable;

import ast.types.ErrorType;
import errorhandler.EH;

public class IdentificationVisitor extends AbstractVisitor {
	
	private SymbolTable symbolTable;
	
	public Object visit(Variable node, Object param) {
		Definition def = this.symbolTable.find(node.getName());
		if (def != null)
			node.setDefinition(def);
		
		else
			new ErrorType("Variable '" + node.getName() + "' not defined", node.getLine(), node.getColumn());
		
		return super.visit(node, param);
	}
	
	public Object visit(VarDefinition node, Object param) {
		addDefinition(node);
		return super.visit(node, param);
	}
	
	public Object visit(FuncDefinition node, Object param) {
		this.symbolTable.set();
		
		addDefinition(node);
		Object toReturn = super.visit(node, param);
		
		this.symbolTable.reset();
		return toReturn;
	}
	
	public Object visit(Program node, Object param) {
		this.symbolTable = new SymbolTable();
		return super.visit(node, param);
	}
	
	public void addDefinition(Definition def) {
		if (! this.symbolTable.insert(def))
			new ErrorType("Symbol '" + def.getName() + "' already defined", def.getLine(), def.getColumn());
	}
}