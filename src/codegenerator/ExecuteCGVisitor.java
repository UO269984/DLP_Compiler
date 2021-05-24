package codegenerator;

import ast.ASTNode;
import ast.Program;
import ast.Statement;
import ast.Expresion;
import ast.Type;
import ast.Definition;
import ast.VarDefinition;
import ast.FuncDefinition;

import ast.expresions.FuncCall;
import ast.types.FuncType;
import ast.types.Types;
import ast.statements.*;

public class ExecuteCGVisitor extends AbstractCGVisitor {
	
	private CodeGenerator cg;
	private ValueCGVisitor valueVisitor;
	private AddressCGVisitor addressVisitor;
	
	public ExecuteCGVisitor(CodeGenerator codeGenerator) {
		this.cg = codeGenerator;
		this.valueVisitor = new ValueCGVisitor(codeGenerator);
		this.addressVisitor = new AddressCGVisitor(codeGenerator);
		
		this.valueVisitor.setAddressVisitor(this.addressVisitor);
		this.addressVisitor.setValueVisitor(this.valueVisitor);
	}
	
	/*
	execute[[Program : program -> definition*]]()=
		<call> main
		<halt>
		for definition in definition*
			execute[[definition]]()
	*/
	@Override
	public Object visit(Program node, Object param) {
		this.cg.emptyLine();
		this.cg.addIndent();
		
		for (Definition definition : node.getDefinitions()) {
			if (definition instanceof VarDefinition)
				definition.accept(this, param);
		}
		this.cg.removeIndent();
		this.cg.emptyLine();
		this.cg.comment(" Main function call");
		
		this.cg.call("main");
		this.cg.halt();
		this.cg.emptyLine();
		
		for (Definition definition : node.getDefinitions()) {
			if (definition instanceof FuncDefinition)
				definition.accept(this, param);
		}
		
		return param;
	}
	
	/*
	execute[[FuncDefinition : definition -> name varDefinition* statement*]]()=
		<label> name
		<enter> definition.getLocalsSize()
		for statement in statement*
			execute[[statement]]()
	*/
	@Override
	public Object visit(FuncDefinition node, Object param) {
		this.cg.label(node.getName());
		this.cg.addIndent();
		
		this.cg.comment(" Params");
		FuncType funcType = (FuncType) node.getType();
		for (VarDefinition funcParam : funcType.getParamTypes())
			funcParam.accept(this, param);
		
		this.cg.comment(" Locals");
		for (VarDefinition def : node.getVarDefinitions())
			def.accept(this, param);
		
		this.cg.enter(node.getVarsSize());
		
		for (Statement statement : node.getStatements())
			statement.accept(this, node);
		
		this.cg.removeIndent();
		return param;
	}
	
	/*
	execute[[VarDefinition : definition -> name]]()=
	*/
	@Override
	public Object visit(VarDefinition node, Object param) {
		this.cg.varInfo(node);
		return param;
	}
	
	/*
	execute[[WhileLoop : statement -> condition statement*]]()=
		int label = cg.getLabel()
		
		inicioWhile label:
			value[[condition]]()
			<jz> endWhile label
			for statement in statement*
				execute[[statement]]()
		
		<jmp> inicioWhile label
		endWhile label:
	*/
	@Override
	public Object visit(WhileLoop node, Object param) {
		int labelNum = this.cg.getLabelCount();
		this.cg.label("whileStart", labelNum);
		this.cg.addIndent();
		
		node.getExpresion().accept(this.valueVisitor, param);
		this.cg.jz("whileEnd", labelNum);
		
		for (Statement whileStatement : node.getWhileStatements())
			whileStatement.accept(this, param);
		
		this.cg.jmp("whileStart", labelNum);
		
		this.cg.removeIndent();
		this.cg.label("whileEnd", labelNum);
		return param;
	}
	
	/*
	execute[[IfCond : statement -> condition ifStatements* elseStatements*]]()=
		int label = cg.getLabel()
		
		value[[condition]]()
		jz else label
			for statement in ifStatements*
				execute[[statement]]()
			
			<jmp> end label
		
		else label:
			for statement in elseStatements
				execute[[statement]]()
		end label:
			
	*/
	@Override
	public Object visit(IfCond node, Object param) {
		int labelNum = this.cg.getLabelCount();
		this.cg.addIndent();
		
		node.getExpresion().accept(this.valueVisitor, param);
		this.cg.jz("elseStart", labelNum);
		
		for (Statement ifStatement : node.getIfStatements())
			ifStatement.accept(this, param);
		
		this.cg.jmp("elseEnd", labelNum);
		this.cg.removeIndent();
		
		this.cg.label("elseStart", labelNum);
		this.cg.addIndent();
		for (Statement elseStatement : node.getElseStatements())
			elseStatement.accept(this, param);
		
		this.cg.removeIndent();
		this.cg.label("elseEnd", labelNum);
		return param;
	}
	
	/*
	execute[[Input : statement -> expression*]]()=
		for exp in expression*
			address[[exp]]()
			<in> exp.type.suffix
			<store> exp.type.suffix
	*/
	@Override
	public Object visit(Input node, Object param) {
		writeLine(node);
		
		for (Expresion exp : node.getExpresions()) {
			exp.accept(this.addressVisitor, param);
			this.cg.in(exp.getType());
			this.cg.store(exp.getType());
		}
		
		return param;
	}
	
	/*
	execute[[Print : statement -> expression*]]()=
		for exp in expression*
			value[[exp]]()
			<out> exp.type.suffix
	*/
	@Override
	public Object visit(Print node, Object param) {
		writeLine(node);
		
		for (Expresion exp : node.getExpresions()) {
			exp.accept(this.valueVisitor, param);
			this.cg.out(exp.getType());
		}
		
		return param;
	}
	
	/*
	execute[[VarAssigment : statement -> toAssign value]]()=
		address[[toAssign]]()
		value[[value]]()
		<store> toAssign.type.suffix
	*/
	@Override
	public Object visit(VarAssigment node, Object param) {
		writeLine(node);
		
		node.getToAsign().accept(this.addressVisitor, param);
		node.getValue().accept(this.valueVisitor, param);
		this.cg.store(node.getToAsign().getType());
		
		return param;
	}
	
	/*
	execute[[FuncCall : statement -> definition param*]]()=
		value[[(Expression) statement]]()
		
		if ((Expression) statement).type != VoidType
			<pop> ((Expression) statement).type.suffix
	*/
	@Override
	public Object visit(FuncCall node, Object param) {
		writeLine(node);
		node.accept(this.valueVisitor, param);
		
		Type retType = node.getType();
		if (retType != Types.getVoid())
			this.cg.pop(retType);
		
		return param;
	}
	
	/*
	execute[[FuncReturn : statement -> expression]](funcDef)=
		value[[expression]]()
		<ret> expression.type.numberOfBytes funcDef.varsSize funcDef.type.paramsSize
	*/
	@Override
	public Object visit(FuncReturn node, Object param) {
		writeLine(node);
		
		Expresion retExp = node.getExpresion();
		retExp.accept(this.valueVisitor, param);
		
		FuncDefinition funcDef = (FuncDefinition) param;
		this.cg.ret(retExp.getType().numberOfBytes(), funcDef.getVarsSize(), ((FuncType) funcDef.getType()).getParamsSize());
		return param;
	}
	
	private void writeLine(ASTNode node) {
		this.cg.emptyLine();
		this.cg.line(node.getLine());
	}
}