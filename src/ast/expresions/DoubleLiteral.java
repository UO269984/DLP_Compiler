package ast.expresions;

import ast.PosASTNode;
import ast.Expresion;
import visitor.Visitor;

public class DoubleLiteral extends PosASTNode implements Expresion {
	private double doubleNum;
	
	public DoubleLiteral(double doubleNum, int line, int column) {
		super(line, column);
		this.doubleNum = doubleNum;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}