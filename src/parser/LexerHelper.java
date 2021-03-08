package parser;

import java.util.HashMap;

public class LexerHelper {
	
	private static HashMap<Character, Character> scapedChars = new HashMap<Character, Character>();
	
	static {
		scapedChars.put('n', '\n');
		scapedChars.put('t', '\t');
		scapedChars.put('\'', '\'');
	}
	
	public static int lexemeToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}

	// TODO: Implement the lexemeToChar and lexemeToReal methods	
	
	public static double lexemeToReal(String str) {
		try {
			return Double.parseDouble(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}
	
	public static char lexemeToChar(String str) {
		int strLen = str.length();
		
		if (strLen < 3 || str.charAt(0) != '\'' || str.charAt(strLen - 1) != '\'')
			throw new IllegalArgumentException("El parámetro no es un char: " + str);
		
		if (str.charAt(1) == '\\') { //Caracter escapado
			Character c = scapedChars.get(str.charAt(2));
			if (c != null)
				return c;
			
			else if (strLen >= 4 && strLen <= 6) //Caracter en código ASCII
				return (char) (int) Integer.valueOf(str.substring(2, strLen - 1));
			
			else
				throw new IllegalArgumentException("Longitud inválida");
		}
		
		else if (strLen == 3)
			return str.charAt(1);
		
		else
			throw new IllegalArgumentException("Longitud inválida");
	}
}