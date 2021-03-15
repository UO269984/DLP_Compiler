package ast.statements;

import ast.PosASTNode;
import ast.Statement;
import ast.Expresion;
import visitor.Visitor;

public class VarAssigment extends PosASTNode implements Statement {
	private Expresion toAsign;
	private Expresion value;
	
	public VarAssigment(Expresion toAsign, Expresion value, int line, int column) {
		super(line, column);
		this.toAsign = toAsign;
		this.value = value;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}