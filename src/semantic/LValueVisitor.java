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
	
	//El valor por defecto del LValue en todos los nodos es false
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
			createLValueError(toAsign);
		
		return null;
	}
	
	@Override
	public Object visit(Input node, Object param) {
		super.visit(node, param);
		
		for (Expresion exp : node.getExpresions()) {
			if (! exp.getLValue())
				createLValueError(exp);
		}
		
		return null;
	}
	
	private void createLValueError(Expresion exp) {
		String[] expresionName = exp.getClass().getName().split("\\.");
		new ErrorType(LVALUE_ERROR_MSG + expresionName[expresionName.length - 1], exp.getLine(), exp.getColumn());
	}
}