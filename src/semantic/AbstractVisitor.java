package visitor;

import ast.expresions.*;
import ast.statements.*;
import ast.types.*;

import ast.VarDefinition;
import ast.FuncDefinition;
import ast.Program;

public class AbstractVisitor implements Visitor {
	
	@Override
	public Object visit(Variable node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(Arithmetic node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(Comparison node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(FuncCall node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(ArrayAccess node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(StructAccess node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(Cast node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(UnaryMinus node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(BoolNot node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(IntLiteral node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(CharLiteral node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(DoubleLiteral node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(VarAssigment node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(FuncReturn node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(IfCond node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(WhileLoop node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(Print node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(Input node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(CharType node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(IntType node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(DoubleType node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(ArrayType node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(StructType node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(FuncType node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(VoidType node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(ErrorType node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(VarDefinition node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(FuncDefinition node, Object param) {
		return null;
	}
	
	@Override
	public Object visit(Program node, Object param) {
		return null;
	}
}