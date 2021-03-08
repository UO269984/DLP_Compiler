package ast.expresions;

import ast.PosASTNode;
import ast.Expresion;

public class CharLiteral extends PosASTNode implements Expresion {
	private char character;
	
	public CharLiteral(char character, int line, int column) {
		super(line, column);
		this.character = character;
	}
}