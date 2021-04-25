package ast.types;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import ast.Expresion;
import ast.Type;
import ast.VarDefinition;
import ast.ASTNode;

import visitor.Visitor;
import util.ErrorMSG;

public class FuncType extends ComposedType {
	private List<VarDefinition> paramsTypes;
	private int paramsSize;
	
	public FuncType(List<VarDefinition> params) {
		super(Types.getVoid());
		this.paramsTypes = params;
		this.paramsSize = params.stream().mapToInt(def -> def.getType().numberOfBytes()).sum();
	}
	
	public List<VarDefinition> getParamTypes() {
		return this.paramsTypes;
	}
	
	public int getParamsSize() {
		return this.paramsSize;
	}
	
	public Type getRetType() {
		return getType();
	}
	
	public void setRetType(Type retType) {
		setType(retType);
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public Type funcCall(List<Expresion> params, ASTNode node) {
		if (params.size() != this.paramsTypes.size())
			return new ErrorType(ErrorMSG.getMsg("funcCallError.numParams", this.paramsTypes.size(), params.size()),
				node.getLine(), node.getColumn());
		
		else {
			Iterator<VarDefinition> expectedParamsIt = this.paramsTypes.iterator();
			Iterator<Expresion> recvParamsIt = params.iterator();
			
			for (int i = 1; expectedParamsIt.hasNext(); i++) {
				VarDefinition curExpectedParam = expectedParamsIt.next();
				Expresion curRecvParam = recvParamsIt.next();
				
				if (! curExpectedParam.getType().equals(curRecvParam.getType()))
					new ErrorType(ErrorMSG.getMsg("funcCallError.typeParam",
						i, curRecvParam.getType(), curExpectedParam.getType()), curRecvParam.getLine(), curRecvParam.getColumn());
			}
			return getType(); //El tipo de retorno de la función
		}
	}
	
	@Override
	public String toString() {
		return "function";
	}
}