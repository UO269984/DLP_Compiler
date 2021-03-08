package ast.expresions;

import ast.PosASTNode;
import ast.Expresion;

public class IntLiteral extends PosASTNode implements Expresion {
	private int intNum;
	
	public IntLiteral(int intNum, int line, int column) {
		super(line, column);
		this.intNum = intNum;
	}
}