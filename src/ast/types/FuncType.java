package ast.types;

import java.util.List;
import java.util.ArrayList;

import ast.Type;
import ast.VarDefinition;
import ast.NoPosASTNode;

public class FuncType extends NoPosASTNode implements Type {
	private List<VarDefinition> paramsTypes;
	private Type retType;
	
	public FuncType() {
		this.paramsTypes = new ArrayList<VarDefinition>();
	}
}