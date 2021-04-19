package codegenerator;

import ast.types.FuncType;
import ast.VarDefinition;
import ast.Statement;
import ast.FuncDefinition;

import visitor.AbstractVisitor;

import java.util.List;

public class OffsetVisitor extends AbstractVisitor {
	
	private int globalVarOffset = 0;
	
	@Override
	public Object visit(VarDefinition node, Object param) {
		super.visit(node, param);
		
		if (node.getScope() == 0) {
			node.setOffset(this.globalVarOffset);
			this.globalVarOffset += node.getType().numberOfBytes();
		}
		return param;
	}
	
	@Override
	public Object visit(FuncType node, Object param) {
		node.getType().accept(this, param);
		
		int offset = 4;
		List<VarDefinition> params = node.getParamTypes();
		
		for (int i = params.size() - 1; i >= 0; i--) {
			VarDefinition paramDef = params.get(i);
			paramDef.accept(this, param);
			
			paramDef.setOffset(offset);
			offset += paramDef.getType().numberOfBytes();
		}
		
		return param;
	}
	
	@Override
	public Object visit(FuncDefinition node, Object param) {
		node.getType().accept(this, param);
		
		int offset = 0;
		for (VarDefinition varDef : node.getVarDefinitions()) {
			varDef.accept(this, param);
			
			offset -= varDef.getType().numberOfBytes();
			varDef.setOffset(offset);
		}
		
		for (Statement statement : node.getStatements())
			statement.accept(this, param);
		
		return param;
	}
}