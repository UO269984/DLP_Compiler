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
		cg.converTo(arithmetic.right.type, arithmetic.type)
		
		value[[arithmetic.right]]()
		cg.converTo(arithmetic.left.type, arithmetic.type)
		operations.get(operand)(arithmetic.type)
	*/
	@Override
	public Object visit(Arithmetic node, Object param) {
		node.getExpresion1().accept(this, param);
		this.cg.convertTo(node.getExpresion1().getType(), node.getType());
		
		node.getExpresion2().accept(this, param);
		this.cg.convertTo(node.getExpresion2().getType(), node.getType());
		
		this.operationFuncs.get(node.getOperand()).accept(node.getType()); //El accept es de la clase Consumer
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
		node.getExpresion1().accept(this, param);
		node.getExpresion2().accept(this, param);
		this.operationFuncs.get(node.getOperand()).accept(node.getType()); //El accept es de la clase Consumer
		return param;
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
	value[[Cast : cast -> castType castExp]]()=
		value[[castExp]]()
		cg.converTo(castExp.type, castType)
	*/
	@Override
	public Object visit(Cast node, Object param) {
		node.getExpresion().accept(this, param);
		this.cg.convertTo(node.getExpresion().getType(), node.getCastType());
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