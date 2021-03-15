package ast.types;

import java.util.List;
import java.util.ArrayList;

import ast.Type;
import ast.VarDefinition;
import ast.NoPosASTNode;
import visitor.Visitor;

public class FuncType extends NoPosASTNode implements Type {
	private List<VarDefinition> paramsTypes;
	private Type retType;
	
	public FuncType(List<VarDefinition> params) {
		this.paramsTypes = params;
		this.retType = new VoidType();
	}
	
	public void setRetType(Type retType) {
		this.retType = retType;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}