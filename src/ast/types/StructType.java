package ast.types;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import ast.Type;
import ast.NoPosASTNode;
import ast.VarDefinition;

public class StructType extends NoPosASTNode implements Type {
	private Set<RecordField> fields;
	
	public StructType() {
		this.fields = new HashSet<RecordField>();
	}
	
	public void addFields(List<VarDefinition> definitions) {
		definitions.forEach(def -> this.fields.add(new RecordField(def.getName(), def.getType(), 0)));
	}
}