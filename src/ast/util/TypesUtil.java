package ast.util;

import ast.types.ErrorType;
import ast.Type;
import ast.ASTNode;

import util.ErrorMSG;

public class TypesUtil {
	public static Type checkSameTypes(Type type1, Type type2, ASTNode node, String errorName) {
		if (type1.equals(type2))
			return type1;
		
		else if (type2 instanceof ErrorType)
			return type2;
		
		else
			return new ErrorType(ErrorMSG.getMsg(errorName, type1.toString(), type2.toString()), node.getLine(), node.getColumn());
	}
	
	public static Type checkCast(Type type1, Type type2, ASTNode node) {
		if (type2.isBuiltInType() || type2 instanceof ErrorType)
			return type2;
		
		else
			return new ErrorType(ErrorMSG.getMsg("typeError.cast", type1.toString(), type2.toString()), node.getLine(), node.getColumn());
	}
}