package ast.statements;

import ast.Expression;
import visitor.Visitor;

public class FuncReturn extends StatementWithExp {
	
	public FuncReturn(Expression retExp, int line, int column) {
		super(retExp, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}