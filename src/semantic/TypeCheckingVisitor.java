package semantic;

import visitor.AbstractVisitor;
import ast.statements.VarAssigment;
import ast.statements.Input;
import ast.statements.IfCond;
import ast.statements.WhileLoop;
import ast.statements.FuncReturn;

import ast.types.FuncType;
import ast.types.ErrorType;
import errorhandler.EH;

import ast.Expresion;
import ast.expresions.*;

import ast.VarDefinition;
import ast.FuncDefinition;

import ast.Statement;
import ast.Type;
import ast.types.Types;
import util.ErrorMSG;

public class TypeCheckingVisitor extends AbstractVisitor {
	
	//El valor por defecto del LValue en todos los nodos es false
	@Override
	public Object visit(Variable node, Object param) {
		super.visit(node, param);
		
		node.setLValue(true);
		node.setType(node.getDefinition() != null ? node.getDefinition().getType() : createNotDefinedError(node));
		return param;
	}
	
	@Override
	public Object visit(ArrayAccess node, Object param) {
		super.visit(node, param);
		
		node.setLValue(true);
		node.setType(node.getExpresion1().getType().arrayAccess(node.getExpresion2().getType(), node));
		return param;
	}
	
	@Override
	public Object visit(StructAccess node, Object param) {
		super.visit(node, param);
		
		node.setLValue(true);
		node.setType(node.getExpresion().getType().structAccess(node.getFieldName(), node));
		return param;
	}
	
	@Override
	public Object visit(EmptyExpression node, Object param) {
		super.visit(node, param);
		
		node.setType(Types.getVoid());
		return param;
	}
	
	@Override
	public Object visit(CharLiteral node, Object param) {
		super.visit(node, param);
		
		node.setType(Types.getChar());
		return param;
	}
	
	@Override
	public Object visit(IntLiteral node, Object param) {
		super.visit(node, param);
		
		node.setType(Types.getInt());
		return param;
	}
	
	@Override
	public Object visit(DoubleLiteral node, Object param) {
		super.visit(node, param);
		
		node.setType(Types.getDouble());
		return param;
	}
	
	@Override
	public Object visit(Comparison node, Object param) {
		super.visit(node, param);
		
		node.setType(node.getExpresion1().getType().comparison(node.getExpresion2().getType(), node));
		return param;
	}
	
	@Override
	public Object visit(LogicOperation node, Object param) {
		super.visit(node, param);
		
		node.setType(node.getExpresion1().getType().logic(node.getExpresion2().getType(), node));
		return param;
	}
	
	@Override
	public Object visit(BoolNot node, Object param) {
		super.visit(node, param);
		
		node.setType(node.getExpresion().getType().logic(node));
		return param;
	}
	
	@Override
	public Object visit(UnaryMinus node, Object param) {
		super.visit(node, param);
		
		node.setType(node.getExpresion().getType().arithmetic(node));
		return param;
	}
	
	@Override
	public Object visit(Cast node, Object param) {
		super.visit(node, param);
		
		node.setType(node.getExpresion().getType().canBeCastTo(node.getCastType(), node));
		return param;
	}
	
	@Override
	public Object visit(Arithmetic node, Object param) {
		super.visit(node, param);
		
		node.setType(node.getExpresion1().getType().arithmetic(node.getExpresion2().getType(), node));
		return param;
	}
	
	@Override
	public Object visit(FuncCall node, Object param) {
		super.visit(node, param);
		
		if (node.getDefinition() != null)
			node.setType(node.getDefinition().getType().funcCall(node.getParams(), node));
		
		else
			node.setType(createNotDefinedError(node));
		
		return param;
	}
	
	@Override
	public Object visit(IfCond node, Object param) {
		node.getExpresion().accept(this, param);
		checkLogicExp(node.getExpresion(), "'if' condition");
		
		for (Statement statement : node.getIfStatements())
			statement.accept(this, param);
		
		for (Statement statement : node.getElseStatements())
			statement.accept(this, param);
		
		return param;
	}
	
	@Override
	public Object visit(WhileLoop node, Object param) {
		node.getExpresion().accept(this, param);
		checkLogicExp(node.getExpresion(), "'while' loop");
		
		for (Statement statement : node.getWhileStatements())
			statement.accept(this, param);
		
		return param;
	}
	
	private void checkLogicExp(Expresion exp, String statementName) {
		if (! exp.getType().isLogical())
			new ErrorType(ErrorMSG.getMsg("typeInStatement.notLogic", statementName), exp.getLine(), exp.getColumn());
	}
	
	@Override
	public Object visit(VarAssigment node, Object param) {
		super.visit(node, param);
		
		Expresion toAsign = node.getToAsign();
		if (! toAsign.getLValue())
			createLValueError(toAsign);
		
		node.getValue().getType().promotesTo(toAsign.getType(), node);
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
	
	@Override
	public Object visit(FuncDefinition node, Object param) {
		node.getType().accept(this, param);
		
		//Comprobación de tipos de la función
		FuncType funcType = (FuncType) node.getType();
		Type retType = funcType.getRetType();
		
		if (! (retType.isBuiltInType() || retType.equals(Types.getVoid())))
			new ErrorType(ErrorMSG.getMsg("funcDefError.invalidRetType", retType), node.getLine(), node.getColumn());
		
		if (funcType.getRetType() == Types.getVoid()) {
			int numStatements = node.getStatements().size();
			int line = (numStatements != 0 ? node.getStatements().get(numStatements - 1).getLine() : node.getLine()) + 1;
			int col = node.getColumn();
			
			node.addStatement(new FuncReturn(new EmptyExpression(line, col), line, col));
		}
		
		int paramIndex = 1;
		for (VarDefinition funcParam : funcType.getParamTypes()) {
			if (! funcParam.getType().isBuiltInType())
				new ErrorType(ErrorMSG.getMsg("funcDefError.invalidParamType", paramIndex, funcParam.getType()), funcParam.getLine(), funcParam.getColumn());
			
			paramIndex++;
		}
		
		//Recorrido de los hijos
		for (VarDefinition varDef : node.getVarDefinitions())
			varDef.accept(this, param);
		
		for (Statement statement : node.getStatements())
			statement.accept(this, node);
		
		return param;
	}
	
	@Override
	public Object visit(FuncReturn node, Object param) {
		super.visit(node, param);
		Type retType = ((FuncType) ((FuncDefinition) param).getType()).getRetType();
		node.getExpresion().getType().promotesTo(retType, node);
		
		return param;
	}
	
	private void createLValueError(Expresion exp) {
		String[] expresionName = exp.getClass().getName().split("\\.");
		new ErrorType(ErrorMSG.getMsg("LValueError", expresionName[expresionName.length - 1]), exp.getLine(), exp.getColumn());
	}
	
	private Type createNotDefinedError(ExpressionWithDefinition exp) {
		return new ErrorType(ErrorMSG.getMsg("symbolError.notDefined", exp.getName()), exp.getLine(), exp.getColumn());
	}
}