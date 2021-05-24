package ast.statements;

import java.util.List;

import ast.Expression;
import visitor.Visitor;

public class Print extends StatementListExp {
	
	public Print(List<Expression> expressions, int line, int column) {
		super(expressions, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}