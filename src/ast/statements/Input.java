package ast.statements;

import java.util.List;

import ast.Expresion;

public class Input extends StatementListExp {
	
	public Input(List<Expresion> expresions, int line, int column) {
		super(expresions, line, column);
	}
}