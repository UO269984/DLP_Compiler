package ast.statements;

import ast.Expresion;
import visitor.Visitor;

public class FuncReturn extends StatementWithExp {
	
	public FuncReturn(Expresion retExp, int line, int column) {
		super(retExp, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}