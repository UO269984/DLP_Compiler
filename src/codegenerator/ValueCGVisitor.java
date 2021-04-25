package codegenerator;

import ast.Type;
import ast.Expresion;
import ast.expresions.*;
import ast.types.Types;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class ValueCGVisitor extends AbstractCGVisitor {
	
	private CodeGenerator cg;
	private Map<String, Consumer<Type>> operationFuncs;
	private Map<Integer, Runnable> castsFuncs;
	private AddressCGVisitor addressVisitor;
	
	public ValueCGVisitor(CodeGenerator codeGenerator) {
		this.cg = codeGenerator;
		
		this.operationFuncs = new HashMap<String, Consumer<Type>>();
		this.operationFuncs.put("+", this.cg::add);
		this.operationFuncs.put("-", this.cg::sub);
		this.operationFuncs.put("*", this.cg::mul);
		this.operationFuncs.put("/", this.cg::div);
		this.operationFuncs.put("%", this.cg::mod);
		
		this.operationFuncs.put(">", this.cg::gt);
		this.operationFuncs.put("<", this.cg::lt);
		this.operationFuncs.put(">=", this.cg::ge);
		this.operationFuncs.put("<=", this.cg::le);
		this.operationFuncs.put("==", this.cg::eq);
		this.operationFuncs.put("!=", this.cg::ne);
		
		this.castsFuncs = new HashMap<Integer, Runnable>();
		Type charType = Types.getChar();
		Type intType = Types.getInt();
		Type doubleType = Types.getDouble();
		
		this.castsFuncs.put(Cast.getCastId(charType, intType), this.cg::b2i);
		this.castsFuncs.put(Cast.getCastId(intType, charType), this.cg::i2b);
		this.castsFuncs.put(Cast.getCastId(intType, doubleType), this.cg::i2f);
		this.castsFuncs.put(Cast.getCastId(doubleType, intType), this.cg::f2i);
		this.castsFuncs.put(Cast.getCastId(charType, doubleType), () -> {this.cg.b2i(); this.cg.i2f();});
		this.castsFuncs.put(Cast.getCastId(doubleType, charType), () -> {this.cg.f2i(); this.cg.i2b();});
	}
	
	public void setAddressVisitor(AddressCGVisitor addressVisitor) {
		this.addressVisitor = addressVisitor;
	}
	
	/*
	value[[Variable : var -> name]]()=
		address[[var]]()
		<load> var.type.suffix
	*/
	public Object visit(Variable node, Object param) {
		loadExpression(node, param);
		return param;
	}
	
	/*
	value[[Arithmetic : arithmetic -> operand]](operations)=
		value[[arithmetic.left]]()
		value[[arithmetic.right]]()
		operations.get(operand)(arithmetic.type)
	*/
	@Override
	public Object visit(Arithmetic node, Object param) {
		makeBinaryOperation(node, param);
		return param;
	}
	
	/*
	value[[Comparison : comparison -> operand]](operations)=
		value[[comparison.left]]()
		value[[comparison.right]]()
		operations.get(operand)(comparison.type)
		
	*/
	@Override
	public Object visit(Comparison node, Object param) {
		makeBinaryOperation(node, param);
		return param;
	}
	
	private void makeBinaryOperation(BinaryOperation operation, Object param) {
		operation.getExpresion1().accept(this, param);
		operation.getExpresion2().accept(this, param);
		this.operationFuncs.get(operation.getOperand()).accept(operation.getType()); //El accept es de la clase Consumer
	}
	
	/*
	value[[LogicOperation : logicOp -> operand]]()=
		value[[logicOp.left]]()
		value[[logicOp.right]]()
		
	*/
	@Override
	public Object visit(LogicOperation node, Object param) {
		node.getExpresion1().accept(this, param);
		node.getExpresion2().accept(this, param);
		
		switch (node.getOperand()) {
			case "&&":
				this.cg.and();
				break;
			
			case "||":
				this.cg.or();
				break;
			
			default:
				throw new IllegalStateException("Invalid logic operation operand");
		}
		return param;
	}
	
	/*
	value[[FuncCall : funcCall -> param*]]()=
		for param in param*
			value[[param]]()
		
		<call> funcCall.name
	*/
	@Override
	public Object visit(FuncCall node, Object param) {
		for (Expresion callParam : node.getParams())
			callParam.accept(this, param);
		
		this.cg.call(node.getName());
		return param;
	}
	
	/*
	value[[ArrayAccess : arrayAccess -> index]]()=
		address[[arrayAccess]]()
		<load> arrayAccess.type.suffix
	*/
	@Override
	public Object visit(ArrayAccess node, Object param) {
		loadExpression(node, param);
		return param;
	}
	
	/*
	value[[StructAccess : structAccess -> filedName]]()=
		address[[structAccess]]()
		<load> structAccess.type.suffix
	*/
	@Override
	public Object visit(StructAccess node, Object param) {
		loadExpression(node, param);
		return param;
	}
	
	private void loadExpression(Expresion exp, Object param) {
		exp.accept(this.addressVisitor, param);
		this.cg.load(exp.getType());
	}
	
	/*
	value[[Cast : cast -> castType expType castExp]](castsFuncs)=
		value[[castExp]]()
		castsFuncs.get(cast.castId)()
	*/
	@Override
	public Object visit(Cast node, Object param) {
		node.getExpresion().accept(this, param);
		this.castsFuncs.get(node.getCastId()).run();
		
		return param;
	}
	
	/*
	value[[UnaryMinus : unaryMinus -> expression]]()=
		<push> unaryMinus.type 0
		value[[expression]]()
		<add> unaryMinus.type
	*/
	@Override
	public Object visit(UnaryMinus node, Object param) {
		this.cg.push(node.getType(), 0);
		node.getExpresion().accept(this, param);
		this.cg.add(node.getType());
		
		return param;
	}
	
	/*
	value[[BoolNot : boolNot -> expression]]()=
		value[[expression]]()
		<not>
	*/
	@Override
	public Object visit(BoolNot node, Object param) {
		node.getExpresion().accept(this, param);
		this.cg.not();
		return param;
	}
	
	/*
	value[[IntLiteral : intLiteral -> literal]]()=
		<push> intLiteral.type.suffix literal
	*/
	@Override
	public Object visit(IntLiteral node, Object param) {
		this.cg.push(node.getType(), node.getLiteral());
		return param;
	}
	
	/*
	value[[CharLiteral : charLiteral -> literal]]()=
		<push> charLiteral.type.suffix literal
	*/
	@Override
	public Object visit(CharLiteral node, Object param) {
		this.cg.push(node.getType(), (int) node.getLiteral());
		return param;
	}
	
	/*
	value[[DoubleLiteral : doubleLiteral -> literal]]()=
		<push> doubleLiteral.type.suffix literal
	*/
	@Override
	public Object visit(DoubleLiteral node, Object param) {
		this.cg.push(node.getType(), node.getLiteral());
		return param;
	}
}