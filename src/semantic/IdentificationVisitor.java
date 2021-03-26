package semantic;

import visitor.AbstractVisitor;
import symboltable.SymbolTable;

import ast.Program;
import ast.Definition;
import ast.VarDefinition;
import ast.FuncDefinition;
import ast.expresions.Variable;
import ast.expresions.FuncCall;
import ast.expresions.ExpressionWithDefinition;

import ast.types.ErrorType;
import errorhandler.EH;

public class IdentificationVisitor extends AbstractVisitor {
	
	private SymbolTable symbolTable;
	
	public Object visit(Variable node, Object param) {
		checkDefined(node);
		return super.visit(node, param);
	}
	
	public Object visit(FuncCall node, Object param) {
		checkDefined(node);
		return super.visit(node, param);
	}
	
	public Object visit(VarDefinition node, Object param) {
		addDefinition(node);
		return super.visit(node, param);
	}
	
	public Object visit(FuncDefinition node, Object param) {
		addDefinition(node);
		this.symbolTable.set();
		
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
			new ErrorType("'" + def.getName() + "' already defined", def.getLine(), def.getColumn());
	}
	
	public void checkDefined(ExpressionWithDefinition exp) {
		Definition def = this.symbolTable.find(exp.getName());
		if (def != null)
			exp.setDefinition(def);
		
		else
			new ErrorType("'" + exp.getName() + "' not defined", exp.getLine(), exp.getColumn());
	}
}