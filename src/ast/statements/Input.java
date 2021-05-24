package ast.statements;

import java.util.List;

import ast.Expression;
import visitor.Visitor;

public class Input extends StatementListExp {
	
	public Input(List<Expression> expressions, int line, int column) {
		super(expressions, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}