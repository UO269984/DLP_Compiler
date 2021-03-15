package ast.statements;

import ast.PosASTNode;
import ast.Statement;
import ast.Expresion;
import visitor.Visitor;

public class FuncReturn extends PosASTNode implements Statement {
	private Expresion retExp;
	
	public FuncReturn(Expresion retExp, int line, int column) {
		super(line, column);
		this.retExp = retExp;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}