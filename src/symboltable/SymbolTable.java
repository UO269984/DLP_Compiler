package symboltable;

import java.util.List;
import java.util.ArrayList;
import ast.Definition;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
	
	private List<Map<String, Definition>> table;
	private int scope;
	
	public SymbolTable() {
		this.table = new ArrayList<Map<String, Definition>>();
		this.scope = -1;
		set();
	}

	public void set() {
		this.table.add(new HashMap<String, Definition>());
		this.scope++;
	}
	
	public void reset() {
		this.table.remove(this.scope--);
	}
	
	public boolean insert(Definition definition) {
		definition.setScope(this.scope);
		return this.table.get(this.scope).put(definition.getName(), definition) == null;
	}
	
	public Definition find(String id) {
		for (int i = scope; i >= 0; i--) {
			Definition definition = table.get(i).get(id);
			
			if (definition != null)
				return definition;
		}
		return null;
	}

	public Definition findInCurrentScope(String id) {
		return this.table.get(this.scope).get(id);
	}
}