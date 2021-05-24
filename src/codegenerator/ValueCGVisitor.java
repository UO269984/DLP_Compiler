package codegenerator;

import ast.Type;
import ast.Expresion;
import ast.expresions.*;
import ast.types.Types;

public class ValueCGVisitor extends AbstractCGVisitor {
	
	private CodeGenerator cg;
	private AddressCGVisitor addressVisitor;
	
	public ValueCGVisitor(CodeGenerator codeGenerator) {
		this.cg = codeGenerator;
	}
	
	public void setAddressVisitor(AddressCGVisitor addressVisitor) {
		this.addressVisitor = addressVisitor;
	}
	
	/*
	value[[Variable : expression -> definition name]]()=
		address[[expression]]()
		<load> expression.type.suffix
	*/
	public Object visit(Variable node, Object param) {
		loadExpression(node, param);
		return param;
	}
	
	/*
	value[[Arithmetic : expression -> operand left right]](operations)=
		value[[left]]()
		cg.converTo(right.type, expression.type)
		
		value[[right]]()
		cg.converTo(left.type, expression.type)
		operations.get(operand)(expression.type)
	*/
	@Override
	public Object visit(Arithmetic node, Object param) {
		node.getExpresion1().accept(this, param);
		this.cg.convertTo(node.getExpresion1().getType(), node.getType());
		
		node.getExpresion2().accept(this, param);
		this.cg.convertTo(node.getExpresion2().getType(), node.getType());
		
		this.cg.binaryOperation(node);
		return param;
	}
	
	/*
	value[[Comparison : expression -> operand left right]](operations)=
		value[[left]]()
		value[[right]]()
		operations.get(operand)(expression.type)
		
	*/
	@Override
	public Object visit(Comparison node, Object param) {
		node.getExpresion1().accept(this, param);
		node.getExpresion2().accept(this, param);
		this.cg.binaryOperation(node); //El accept es de la clase Consumer
		return param;
	}
	
	/*
	value[[LogicOperation : expression -> operand left right]]()=
		value[[left]]()
		value[[right]]()
		
		if operand == "&&"
			<and>
		
		else if operand == "||"
			<or>
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
	value[[FuncCall : expression -> definition param*]]()=
		for param in param*
			value[[param]]()
		
		<call> definition.name
	*/
	@Override
	public Object visit(FuncCall node, Object param) {
		for (Expresion callParam : node.getParams())
			callParam.accept(this, param);
		
		this.cg.call(node.getName());
		return param;
	}
	
	/*
	value[[ArrayAccess : expression -> array index]]()=
		address[[expression]]()
		<load> expression.type.suffix
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
	value[[Cast : expression -> castType castExp]]()=
		value[[castExp]]()
		cg.converTo(castExp.type, expression.type)
	*/
	@Override
	public Object visit(Cast node, Object param) {
		node.getExpresion().accept(this, param);
		this.cg.convertTo(node.getExpresion().getType(), node.getType());
		return param;
	}
	
	/*
	value[[UnaryMinus : expression -> innerExpression]]()=
		<push> expression.type 0
		value[[innerExpression]]()
		cg.converTo(innerExpression.type, expression.type)
		<sub> expression.type
	*/
	@Override
	public Object visit(UnaryMinus node, Object param) {
		this.cg.push(node.getType(), 0);
		node.getExpresion().accept(this, param);
		this.cg.convertTo(node.getExpresion().getType(), node.getType());
		this.cg.sub(node.getType());
		
		return param;
	}
	
	/*
	value[[BoolNot : expression -> expression]]()=
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
	value[[EmptyExpression : expression -> ]]()=
	*/
	@Override
	public Object visit(EmptyExpression node, Object param) {
		return param;
	}
	
	/*
	value[[IntLiteral : expression -> literal]]()=
		<push> expression.type.suffix literal
	*/
	@Override
	public Object visit(IntLiteral node, Object param) {
		this.cg.push(node.getType(), node.getLiteral());
		return param;
	}
	
	/*
	value[[CharLiteral : expression -> literal]]()=
		<push> expression.type.suffix literal
	*/
	@Override
	public Object visit(CharLiteral node, Object param) {
		this.cg.push(node.getType(), (int) node.getLiteral());
		return param;
	}
	
	/*
	value[[DoubleLiteral : expression -> literal]]()=
		<push> expression.type.suffix literal
	*/
	@Override
	public Object visit(DoubleLiteral node, Object param) {
		this.cg.push(node.getType(), node.getLiteral());
		return param;
	}
}