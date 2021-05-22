package visitor;

import ast.expresions.*;
import ast.statements.*;
import ast.types.*;

import ast.Expresion;
import ast.Statement;
import ast.Definition;
import ast.VarDefinition;
import ast.FuncDefinition;
import ast.Program;

public class AbstractVisitor implements Visitor {
	
	@Override
	public Object visit(Variable node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(Arithmetic node, Object param) {
		node.getExpresion1().accept(this, param);
		node.getExpresion2().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(Comparison node, Object param) {
		node.getExpresion1().accept(this, param);
		node.getExpresion2().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(LogicOperation node, Object param) {
		node.getExpresion1().accept(this, param);
		node.getExpresion2().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(FuncCall node, Object param) {
		for (Expresion funcParam : node.getParams())
			funcParam.accept(this, param);
		
		return param;
	}
	
	@Override
	public Object visit(ArrayAccess node, Object param) {
		node.getExpresion1().accept(this, param);
		node.getExpresion2().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(StructAccess node, Object param) {
		node.getExpresion().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(Cast node, Object param) {
		node.getCastType().accept(this, param);
		node.getExpresion().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(UnaryMinus node, Object param) {
		node.getExpresion().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(BoolNot node, Object param) {
		node.getExpresion().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(EmptyExpression node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(IntLiteral node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(CharLiteral node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(DoubleLiteral node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(VarAssigment node, Object param) {
		node.getValue().accept(this, param);
		node.getToAsign().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(FuncReturn node, Object param) {
		node.getExpresion().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(IfCond node, Object param) {
		node.getExpresion().accept(this, param);
		
		for (Statement statement : node.getIfStatements())
			statement.accept(this, param);
		
		for (Statement statement : node.getElseStatements())
			statement.accept(this, param);
		
		return param;
	}
	
	@Override
	public Object visit(WhileLoop node, Object param) {
		node.getExpresion().accept(this, param);
		
		for (Statement statement : node.getWhileStatements())
			statement.accept(this, param);
		
		return param;
	}
	
	@Override
	public Object visit(Print node, Object param) {
		for (Expresion exp : node.getExpresions())
			exp.accept(this, param);
		
		return param;
	}
	
	@Override
	public Object visit(Input node, Object param) {
		for (Expresion exp : node.getExpresions())
			exp.accept(this, param);
		
		return param;
	}
	
	@Override
	public Object visit(CharType node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(IntType node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(DoubleType node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(ArrayType node, Object param) {
		node.getType().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(StructType node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(FuncType node, Object param) {
		node.getType().accept(this, param);
		
		for (VarDefinition paramType : node.getParamTypes())
			paramType.accept(this, param);
		
		return param;
	}
	
	@Override
	public Object visit(VoidType node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(ErrorType node, Object param) {
		return param;
	}
	
	@Override
	public Object visit(VarDefinition node, Object param) {
		node.getType().accept(this, param);
		return param;
	}
	
	@Override
	public Object visit(FuncDefinition node, Object param) {
		node.getType().accept(this, param);
		
		for (VarDefinition varDef : node.getVarDefinitions())
			varDef.accept(this, param);
		
		for (Statement statement : node.getStatements())
			statement.accept(this, param);
		
		return param;
	}
	
	@Override
	public Object visit(Program node, Object param) {
		for (Definition def : node.getDefinitions())
			def.accept(this, param);
		
		return param;
	}
}