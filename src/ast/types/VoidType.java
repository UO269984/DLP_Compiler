package ast.types;

import ast.NoPosASTNode;
import ast.Type;
import visitor.Visitor;

public class VoidType extends NoPosASTNode implements Type {
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}