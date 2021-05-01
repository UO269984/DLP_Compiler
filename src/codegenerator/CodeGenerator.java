package codegenerator;

import ast.Type;
import ast.types.Types;
import ast.expresions.Cast;
import ast.expresions.BinaryOperation;
import ast.VarDefinition;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class CodeGenerator {
	
	public static final String INDENT = "\t";
	
	private PrintWriter out;
	private int indentAmount;
	private String curIndent;
	private int labelCount;
	
	private Map<String, Consumer<Type>> operationFuncs;
	private Map<Integer, Runnable> castsFuncs;
	
	public CodeGenerator(String outFilename, String inFilename) {
		this.indentAmount = 0;
		updateCurIndent();
		this.labelCount = 0;
		
		try {
			this.out = new PrintWriter(outFilename);
		}
		catch (FileNotFoundException ex) {
			throw new RuntimeException("Can not open " + outFilename + " file");
		}
		this.source(inFilename);
		
		this.operationFuncs = new HashMap<String, Consumer<Type>>();
		this.operationFuncs.put("+", this::add);
		this.operationFuncs.put("-", this::sub);
		this.operationFuncs.put("*", this::mul);
		this.operationFuncs.put("/", this::div);
		this.operationFuncs.put("%", this::mod);
		
		this.operationFuncs.put(">", this::gt);
		this.operationFuncs.put("<", this::lt);
		this.operationFuncs.put(">=", this::ge);
		this.operationFuncs.put("<=", this::le);
		this.operationFuncs.put("==", this::eq);
		this.operationFuncs.put("!=", this::ne);
		
		this.castsFuncs = new HashMap<Integer, Runnable>();
		Type charType = Types.getChar();
		Type intType = Types.getInt();
		Type doubleType = Types.getDouble();
		
		this.castsFuncs.put(Cast.getCastId(charType, charType), () -> {});
		this.castsFuncs.put(Cast.getCastId(intType, intType), () -> {});
		this.castsFuncs.put(Cast.getCastId(doubleType, doubleType), () -> {});
		this.castsFuncs.put(Cast.getCastId(charType, intType), this::b2i);
		this.castsFuncs.put(Cast.getCastId(intType, charType), this::i2b);
		this.castsFuncs.put(Cast.getCastId(intType, doubleType), this::i2f);
		this.castsFuncs.put(Cast.getCastId(doubleType, intType), this::f2i);
		this.castsFuncs.put(Cast.getCastId(charType, doubleType), () -> {this.b2i(); this.i2f();});
		this.castsFuncs.put(Cast.getCastId(doubleType, charType), () -> {this.f2i(); this.i2b();});
	}
	
	public void convertTo(Type expType, Type castType) {
		this.castsFuncs.get(Cast.getCastId(expType, castType)).run();
	}
	
	public void binaryOperation(BinaryOperation operation) {
		this.operationFuncs.get(operation.getOperand()).accept(operation.getType()); //El accept es de la clase Consumer
	}
	
	private void write(String toWrite) {
		this.out.write(this.curIndent + toWrite + "\n");
	}
	
	public void close() {
		this.out.close();
	}
	
	public void simpleInst(String inst, Type type) {
		write(inst + type.suffix());
	}
	
	public void pushBP() {
		write("pusha BP");
	}
	
	public <T>void push(T toPush) {
		write("push " + toPush);
	}
	
	public <T>void push(Type type, T toPush) {
		write("push" + type.suffix() + " " + toPush);
	}
	
	public <T>void pusha(T toPush) {
		write("pusha " + toPush);
	}
	
	public void load(Type type) {
		simpleInst("load", type);
	}
	
	public void store(Type type) {
		simpleInst("store", type);
	}
	
	public void pop(Type type) {
		simpleInst("pop", type);
	}
	
	public void dup(Type type) {
		simpleInst("dup", type);
	}
	
	public void add() {
		write("add");
	}
	
	public void add(Type type) {
		simpleInst("add", type);
	}
	
	public void sub(Type type) {
		simpleInst("sub", type);
	}
	
	public void mul(Type type) {
		simpleInst("mul", type);
	}
	
	public void div(Type type) {
		simpleInst("div", type);
	}
	
	public void mod(Type type) {
		simpleInst("mod", type);
	}
	
	public void gt(Type type) {
		simpleInst("gt", type);
	}
	
	public void lt(Type type) {
		simpleInst("lt", type);
	}
	
	public void ge(Type type) {
		simpleInst("ge", type);
	}
	
	public void le(Type type) {
		simpleInst("le", type);
	}
	
	public void eq(Type type) {
		simpleInst("eq", type);
	}
	
	public void ne(Type type) {
		simpleInst("ne", type);
	}
	
	public void and() {
		write("and");
	}
	
	public void or() {
		write("or");
	}
	
	public void not() {
		write("not");
	}
	
	public void out(Type type) {
		simpleInst("out", type);
	}
	
	public void in(Type type) {
		simpleInst("in", type);
	}
	
	public void b2i() {
		write("b2i");
	}
	
	public void i2f() {
		write("i2f");
	}
	
	public void f2i() {
		write("f2i");
	}
	
	public void i2b() {
		write("i2b");
	}
	
	public void label(String name, int labelNum) {
		write(getLabelName(name, labelNum) + ":");
	}
	
	private String getLabelName(String name, int labelNum) {
		return name + "_" + labelNum;
	}
	
	public void label(String name) {
		write(name + ":");
	}
	
	public void jmp(String labelName, int labelNum) {
		write("jmp " + getLabelName(labelName, labelNum));
	}
	
	public void jz(String labelName, int labelNum) {
		write("jz " + getLabelName(labelName, labelNum));
	}
	
	public void jnz(String labelName, int labelNum) {
		write("jnz " + getLabelName(labelName, labelNum));
	}
	
	public void call(String functionName) {
		write("call " + functionName);
	}
	
	public void enter(int num) {
		write("enter " + num);
	}
	
	public void ret(int retSize, int localsSize, int paramsSize) {
		write("ret " + retSize + ", " + localsSize + ", " + paramsSize);
	}
	
	public void halt() {
		write("halt");
	}
	
	public void source(String code) {
		write("#source \"" + code + "\"");
	}
	
	public void line(int line) {
		write("#line " + line);
	}
	
	public void comment(String comment) {
		write("'" + comment);
	}
	
	public void varInfo(VarDefinition def) {
		comment(" * " + def.getType() + " " + def.getName() + " (offset " + def.getOffset() + ")");
	}
	
	public void emptyLine() {
		write("");
	}
	
	public int getLabelCount() {
		return this.labelCount++;
	}
	
	public void addIndent() {
		this.indentAmount++;
		updateCurIndent();
	}
	
	public void removeIndent() {
		this.indentAmount--;
		updateCurIndent();
	}
	
	private void updateCurIndent() {
		this.curIndent = String.join("", Collections.nCopies(this.indentAmount, INDENT));
	}
}