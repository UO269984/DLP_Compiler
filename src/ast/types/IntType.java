package ast.types;

import ast.Type;
import ast.NoPosASTNode;
import visitor.Visitor;

public class IntType extends NoPosASTNode implements Type {
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}