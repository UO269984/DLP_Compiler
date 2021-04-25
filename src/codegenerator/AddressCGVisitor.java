package codegenerator;

import ast.types.Types;
import ast.types.StructType;
import ast.expresions.Variable;
import ast.expresions.ArrayAccess;
import ast.expresions.StructAccess;
import ast.VarDefinition;

public class AddressCGVisitor extends AbstractCGVisitor {
	
	private CodeGenerator cg;
	private ValueCGVisitor valueVisitor;
	
	public AddressCGVisitor(CodeGenerator codeGenerator) {
		this.cg = codeGenerator;
	}
	
	public void setValueVisitor(ValueCGVisitor valueVisitor) {
		this.valueVisitor = valueVisitor;
	}
	
	/*
	address[[Variable : expression -> id]]()=
		if expression.scope == 0
			<push> expression.definition.offset
		
		esle
			<push BP>
			<push> expression.definition.offset
			<add>
	*/
	@Override
	public Object visit(Variable node, Object param) {
		VarDefinition def = (VarDefinition) node.getDefinition();
		
		if (def.getScope() == 0)
			this.cg.push(def.getOffset());
		
		else {
			this.cg.pushBP();
			this.cg.push(def.getOffset());
			this.cg.add();
		}
		return param;
	}
	
	/*
	address[[ArrayAccess : expression -> array index]]()=
		address[[expression.array]])()
		value[[expression.index]])()
		<add>
	*/
	@Override
	public Object visit(ArrayAccess node, Object param) {
		node.getExpresion1().accept(this, param);
		node.getExpresion2().accept(this.valueVisitor, param);
		this.cg.add();
		
		return param;
	}
	
	/*
	address[[StructAccess : expression -> struct fieldName]]()=
		address[[struct]])()
		<push> struct.type.getOffset(fieldName)
		<add>
	*/
	@Override
	public Object visit(StructAccess node, Object param) {
		node.getExpresion().accept(this, param);
		this.cg.push(((StructType) node.getExpresion().getType()).getFieldOffset(node.getFieldName()));
		this.cg.add();
		
		return param;
	}
}