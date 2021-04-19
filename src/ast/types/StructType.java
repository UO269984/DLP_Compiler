package ast.types;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import ast.Type;
import ast.VarDefinition;
import ast.ASTNode;

import visitor.Visitor;
import util.ErrorMSG;

public class StructType extends AbstractType {
	private Map<String, RecordField> fields;
	private int size;
	
	public StructType() {
		this.fields = new HashMap<String, RecordField>();
		this.size = 0;
	}
	
	public void addFields(List<VarDefinition> definitions) {
		for (VarDefinition def : definitions) {
			String name = def.getName();
			
			if (this.fields.containsKey(name))
				new ErrorType(ErrorMSG.getMsg("structError.repeatedField", name), def.getLine(), def.getColumn());
			
			else {
				this.fields.put(name, new RecordField(name, def.getType(), this.size));
				this.size += def.getType().numberOfBytes();
			}
		}
	}
	
	@Override
	public int numberOfBytes() {
		return this.size;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public Type structAccess(String fieldName, ASTNode node) {
		RecordField record = this.fields.get(fieldName);
		if (record == null)
			return new ErrorType(ErrorMSG.getMsg("structError.undefinedField", fieldName), node.getLine(), node.getColumn());
		
		else
			return record.getType();
	}
	
	@Override
	public String toString() {
		return "struct";
	}
}