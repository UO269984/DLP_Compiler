package semantic;

import visitor.AbstractVisitor;
import ast.statements.VarAssigment;
import ast.statements.Input;
import ast.types.ErrorType;
import errorhandler.EH;

import ast.Expresion;
import ast.expresions.Variable;
import ast.expresions.ArrayAccess;
import ast.expresions.StructAccess;

public class LValueVisitor extends AbstractVisitor {
	
	private static final String LVALUE_ERROR_MSG = "Cannot assign value to ";
	
	@Override
	public Object visit(Variable node, Object param) {
		node.setLValue(true);
		return param;
	}
	
	@Override
	public Object visit(ArrayAccess node, Object param) {
		node.setLValue(true);
		return param;
	}
	
	@Override
	public Object visit(StructAccess node, Object param) {
		node.setLValue(true);
		return param;
	}
	
	@Override
	public Object visit(VarAssigment node, Object param) {
		super.visit(node, param);
		
		Expresion toAsign = node.getToAsign();
		if (! toAsign.getLValue())
			new ErrorType(LVALUE_ERROR_MSG + toAsign.getClass().getName(), toAsign.getLine(), toAsign.getColumn());
		
		return null;
	}
	
	@Override
	public Object visit(Input node, Object param) {
		super.visit(node, param);
		
		for (Expresion exp : node.getExpresions()) {
			if (! exp.getLValue())
				new ErrorType(LVALUE_ERROR_MSG + exp.getClass().getName(), exp.getLine(), exp.getColumn());
		}
		
		return null;
	}
}