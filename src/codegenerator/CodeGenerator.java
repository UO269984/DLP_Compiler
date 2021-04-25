package codegenerator;

import ast.Type;
import ast.VarDefinition;

import java.util.Collections;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class CodeGenerator {
	
	public static final String INDENT = "\t";
	
	private PrintWriter out;
	private int indentAmount;
	private String curIndent;
	
	public CodeGenerator(String outFilename, String inFilename) {
		this.indentAmount = 0;
		updateCurIndent();
		
		try {
			this.out = new PrintWriter(outFilename);
		}
		catch (FileNotFoundException ex) {
			throw new RuntimeException("Can not open " + outFilename + " file");
		}
		this.source(inFilename);
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
	
	public void label(String name) {
		write(name + ":");
	}
	
	public void jmp(String labelName) {
		write("jmp " + labelName);
	}
	
	public void jz(String labelName) {
		write("jz " + labelName);
	}
	
	public void jnz(String labelName) {
		write("jnz " + labelName);
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