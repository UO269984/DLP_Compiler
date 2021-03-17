package ast.types;

import java.util.List;
import java.util.ArrayList;

import ast.Type;
import ast.VarDefinition;
import visitor.Visitor;

public class FuncType extends ComposedType {
	private List<VarDefinition> paramsTypes;
	
	public FuncType(List<VarDefinition> params) {
		super(new VoidType());
		this.paramsTypes = params;
	}
	
	public List<VarDefinition> getParamTypes() {
		return this.paramsTypes;
	}
	
	public void setRetType(Type retType) {
		setType(retType);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}