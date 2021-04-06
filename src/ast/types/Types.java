package ast.types;

public class Types {
	private static final VoidType VOID = new VoidType();
	private static final CharType CHAR = new CharType();
	private static final IntType INT = new IntType();
	private static final DoubleType DOUBLE = new DoubleType();
	
	public static VoidType getVoid() {
		return VOID;
	}
	
	public static CharType getChar() {
		return CHAR;
	}
	
	public static IntType getInt() {
		return INT;
	}
	
	public static DoubleType getDouble() {
		return DOUBLE;
	}
}