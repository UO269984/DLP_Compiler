package codegenerator;

import visitor.Visitor;

import ast.expressions.*;
import ast.statements.*;
import ast.types.*;

import ast.Statement;
import ast.Definition;
import ast.VarDefinition;
import ast.FuncDefinition;
import ast.Program;

public abstract class AbstractCGVisitor implements Visitor {
	
	private static final String INVALID_CALL = "Invalid CG visitor call";
	
	@Override
	public Object visit(Variable node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(Arithmetic node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(Comparison node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
		
	@Override
	public Object visit(LogicOperation node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(FuncCall node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(ArrayAccess node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(StructAccess node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(Cast node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(UnaryMinus node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(BoolNot node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(EmptyExpression node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(IntLiteral node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(CharLiteral node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(DoubleLiteral node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(VarAssigment node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(FuncReturn node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(IfCond node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(WhileLoop node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(Print node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(Input node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(CharType node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(IntType node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(DoubleType node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(ArrayType node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(StructType node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(FuncType node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(VoidType node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(ErrorType node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(VarDefinition node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(FuncDefinition node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
	@Override
	public Object visit(Program node, Object param) {
		throw new IllegalStateException(INVALID_CALL);
	}
	
}