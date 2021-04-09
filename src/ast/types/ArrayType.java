package ast.types;

import ast.Type;
import ast.ASTNode;
import visitor.Visitor;
import util.ErrorMSG;

public class ArrayType extends ComposedType {
	private int size;
	
	public ArrayType(Type type, int size) {
		super(type);
		if (size < 0)
			throw new IllegalArgumentException("size negativo");
		
		this.size = size;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public Type arrayAccess(Type type, ASTNode node) {
		if (type.equals(Types.getInt()))
			return getType();
		
		else if (type instanceof ErrorType)
			return type;
		
		else
			return new ErrorType(ErrorMSG.getMsg("typeError.arrayAccess.index", type.toString()), node.getLine(), node.getColumn());
	}
	
	@Override
	public String toString() {
		return "array";
	}
}