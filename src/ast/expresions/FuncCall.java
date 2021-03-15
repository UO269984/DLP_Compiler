package ast.expresions;

import java.util.List;
import java.util.ArrayList;

import ast.PosASTNode;
import ast.Statement;
import ast.Expresion;
import ast.FuncDefinition;
import visitor.Visitor;

public class FuncCall extends PosASTNode implements Statement, Expresion {
	private FuncDefinition function;
	private List<Expresion> parameters;
	
	public FuncCall(List<Expresion> parameters, int line, int column) {
		super(line, column);
		this.function = null;
		this.parameters = parameters;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}