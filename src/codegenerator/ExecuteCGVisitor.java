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
	execute[[FuncDefinition : def -> name statement*]]()=
		<label> name
		<enter> def.getLocalsSize()
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
		
		if (funcType.getRetType() == Types.getVoid())
			new FuncReturn(null, 0, 0).accept(this, node);
		
		this.cg.removeIndent();
		return param;
	}
	
	@Override
	public Object visit(VarDefinition node, Object param) {
		this.cg.varInfo(node);
		return param;
	}
	
	/*
	execute[[Input : inputStatement -> exp*]]()=
		for exp in exp*
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
	execute[[Print : printStatement -> exp*]]()=
		for exp in exp*
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
	execute[[VarAssigment : assign -> toAssign value]]()=
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
	execute[[FuncCall : funcCall ->]]()=
		value[[funcCall]]()
		
		if funcCall.type.retType != VoidType
			<pop> funcCall.type.retType.suffix
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
	execute[[FuncReturn : ret -> exp]](funcDef)=
		if exp != null
			value[[exp]]()
		
		<ret> exp.type.numberOfBytes funcDef.varsSize funcDef.type.paramsSize
	*/
	@Override
	public Object visit(FuncReturn node, Object param) {
		writeLine(node);
		
		Expresion retExp = node.getExpresion();
		int retSize = 0;
		if (retExp != null) {
			retExp.accept(this.valueVisitor, param);
			retSize = retExp.getType().numberOfBytes();
		}
		
		FuncDefinition funcDef = (FuncDefinition) param;
		this.cg.ret(retSize, funcDef.getVarsSize(), ((FuncType) funcDef.getType()).getParamsSize());
		return param;
	}
	
	private void writeLine(ASTNode node) {
		this.cg.emptyLine();
		this.cg.line(node.getLine());
	}
}