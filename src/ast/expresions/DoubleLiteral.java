package ast.expresions;

import ast.PosASTNode;
import ast.Expresion;

public class DoubleLiteral extends PosASTNode implements Expresion {
	private double doubleNum;
	
	public DoubleLiteral(double doubleNum, int line, int column) {
		super(line, column);
		this.doubleNum = doubleNum;
	}
}