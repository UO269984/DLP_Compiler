package ast.expressions;

import java.util.List;
import java.util.ArrayList;

import ast.Statement;
import ast.Expression;
import ast.FuncDefinition;
import visitor.Visitor;

public class FuncCall extends ExpressionWithDefinition implements Statement {
	private FuncDefinition function;
	private List<Expression> parameters;
	
	public FuncCall(String name, List<Expression> parameters, int line, int column) {
		super(name, line, column);
		this.function = null;
		this.parameters = parameters;
	}
	
	public List<Expression> getParams() {
		return this.parameters;
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
}