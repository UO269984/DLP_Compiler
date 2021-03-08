package ast.types;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import ast.Type;
import ast.NoPosASTNode;
import ast.VarDefinition;

public class StructType extends NoPosASTNode implements Type {
	private Set<RecordField> fields;
	private String repeatedField;
	
	public StructType() {
		this.fields = new HashSet<RecordField>();
		this.repeatedField = null;
	}
	
	public void addFields(List<VarDefinition> definitions) {
		if (this.repeatedField == null) {
			
			for (VarDefinition def : definitions) {
				if (! this.fields.add(new RecordField(def.getName(), def.getType(), 0))) {
					this.repeatedField = def.getName();
					return;
				}
			}
		}
	}
	
	public Type getTypeOrError() {
		if (this.repeatedField == null)
			return this;
		
		else
			return new ErrorType("Repeated field '" + this.repeatedField + "' in struct", getLine(), getColumn());
	}
}