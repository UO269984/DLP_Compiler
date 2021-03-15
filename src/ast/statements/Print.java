package ast.statements;

import java.util.List;

import ast.Expresion;
import visitor.Visitor;

public class Print extends StatementListExp {
	
	public Print(List<Expresion> expresions, int line, int column) {
		super(expresions, line, column);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}