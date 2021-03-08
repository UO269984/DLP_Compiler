package ast.statements;

import java.util.List;

import ast.Expresion;

public class Print extends StatementListExp {
	
	public Print(List<Expresion> expresions, int line, int column) {
		super(expresions, line, column);
	}
}