package visitor;

import ast.expresions.*;
import ast.statements.*;
import ast.types.*;

import ast.VarDefinition;
import ast.FuncDefinition;
import ast.Program;

public interface Visitor {
	
	//Expressions
	public Object visit(Variable node, Object param);
	public Object visit(Arithmetic node, Object param);
	public Object visit(Comparison node, Object param);
	
	public Object visit(FuncCall node, Object param);
	public Object visit(ArrayAccess node, Object param);
	public Object visit(StructAccess node, Object param);
	
	public Object visit(Cast node, Object param);
	public Object visit(UnaryMinus node, Object param);
	public Object visit(BoolNot node, Object param);
	
	public Object visit(IntLiteral node, Object param);
	public Object visit(CharLiteral node, Object param);
	public Object visit(DoubleLiteral node, Object param);
	
	//Statements
	public Object visit(VarAssigment node, Object param);
	public Object visit(FuncReturn node, Object param);
	
	public Object visit(IfCond node, Object param);
	public Object visit(WhileLoop node, Object param);
	
	public Object visit(Print node, Object param);
	public Object visit(Input node, Object param);
	
	//Types
	public Object visit(CharType node, Object param);
	public Object visit(IntType node, Object param);
	public Object visit(DoubleType node, Object param);
	
	public Object visit(ArrayType node, Object param);
	public Object visit(StructType node, Object param);
	
	public Object visit(FuncType node, Object param);
	public Object visit(VoidType node, Object param);
	public Object visit(ErrorType node, Object param);
	
	//Definitions
	public Object visit(VarDefinition node, Object param);
	public Object visit(FuncDefinition node, Object param);
	
	//Program
	public Object visit(Program node, Object param);
}