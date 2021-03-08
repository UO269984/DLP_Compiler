grammar Pmm;

@header {
	import ast.*;
	import ast.types.*;
	import ast.statements.*;
	import ast.expresions.*;
}

//Sintáctico

program returns [Program ast = new Program()]:
		(
			funcDef {$ast.add($funcDef.ast);}
			| varDef {$ast.addVarsDef($varDef.ast);}
		)* mainDef {$ast.add($mainDef.ast);} EOF;

varDef returns [List<VarDefinition> ast = new ArrayList<VarDefinition>()]:
		(idList=ID ',' {$ast.add(new VarDefinition($idList.text, $idList.getLine(), $idList.getCharPositionInLine()));})*
		lastId=ID ':' type ';' {
			$ast.add(new VarDefinition($lastId.text, $lastId.getLine(), $lastId.getCharPositionInLine()));
			$ast.forEach(var -> var.setType($type.ast));
		};

paramDef returns [VarDefinition ast]:
		ID ':' buitInType
		{$ast = new VarDefinition($ID.text, $buitInType.ast, $ID.getLine(), $ID.getCharPositionInLine());};

paramsDef returns [List<VarDefinition> ast = new ArrayList<VarDefinition>()]:
		(
			(paramInList=paramDef ',' {$ast.add($paramInList.ast);})*
			lastParam=paramDef {$ast.add($lastParam.ast);}
		)?;

funcDef returns [FuncDefinition ast]:
		def='def' ID '(' paramsDef ')'
		{$ast = new FuncDefinition($ID.text, $paramsDef.ast, $def.getLine(), $def.getCharPositionInLine());}
		':' (buitInType {$ast.setType($buitInType.ast);})?
		'{' (varDef {$ast.addVarsDef($varDef.ast);})* (statement {$ast.addStatement($statement.ast);})* '}';

mainDef returns [FuncDefinition ast]:
		def='def' funcName='main' '(' paramsDef ')'
		{$ast = new FuncDefinition($funcName.text, $paramsDef.ast, $def.getLine(), $def.getCharPositionInLine());}
		':' (buitInType {$ast.setType($buitInType.ast);})?
		'{' (varDef {$ast.addVarsDef($varDef.ast);})* (statement {$ast.addStatement($statement.ast);})* '}';

statement returns [Statement ast]:
		statement_main ';' {$ast = $statement_main.ast;}
		| ifStatement {$ast = $ifStatement.ast;}
		| whileKey='while' expresion ':' statements_block {
			$ast = new WhileLoop($expresion.ast, $statements_block.ast, $whileKey.getLine(), $whileKey.getCharPositionInLine());
		};

ifStatement returns [IfCond ast]:
		ifKey='if' expresion ':' ifStatements=statements_block
		{$ast = new IfCond($expresion.ast, $ifStatements.ast, $ifKey.getLine(), $ifKey.getCharPositionInLine());}
		('else' elseStatements=statements_block {$ast.setElseStatements($elseStatements.ast);})?;

statement_main returns [Statement ast]:
		input='input' expresionList {$ast = new Input($expresionList.ast, $input.getLine(), $input.getCharPositionInLine());}
		| print='print' expresionList {$ast = new Print($expresionList.ast, $print.getLine(), $print.getCharPositionInLine());}
		| ret='return' expresion {$ast = new FuncReturn($expresion.ast, $ret.getLine(), $ret.getCharPositionInLine());}
		| toAsign=expresion '=' value=expresion {$ast = new VarAssigment($toAsign.ast, $value.ast, $toAsign.ast.getLine(), $toAsign.ast.getColumn());}
		| funcCall {$ast = $funcCall.ast;};

statements_block returns [List<Statement> ast = new ArrayList<Statement>()]:
		statement {$ast.add($statement.ast);}
		| '{' (statement {$ast.add($statement.ast);})* '}';

expresionList returns [List<Expresion> ast = new ArrayList<Expresion>()]:
		(expInList=expresion ',' {$ast.add($expInList.ast);})* lastExp=expresion {$ast.add($lastExp.ast);};

type returns [Type ast]:
		buitInType {$ast = $buitInType.ast;}
		| struct {$ast = $struct.ast;}
		| '[' INT_CONSTANT ']' type {$ast = new ArrayType($type.ast, LexerHelper.lexemeToInt($INT_CONSTANT.text));};

buitInType returns [Type ast]:
		'int' {$ast = new IntType();}
		| 'double' {$ast = new DoubleType();}
		| 'char' {$ast = new CharType();};

struct returns [StructType ast = new StructType()]:
		'struct' '{' (varDef {$ast.addFields($varDef.ast);})* '}';

expresion returns [Expresion ast]:
		INT_CONSTANT {$ast = new IntLiteral(LexerHelper.lexemeToInt($INT_CONSTANT.text), $INT_CONSTANT.getLine(), $INT_CONSTANT.getCharPositionInLine());}
		| REAL_CONSTANT {$ast = new DoubleLiteral(LexerHelper.lexemeToReal($REAL_CONSTANT.text), $REAL_CONSTANT.getLine(), $REAL_CONSTANT.getCharPositionInLine());}
		| CHAR_CONSTANT {$ast = new CharLiteral(LexerHelper.lexemeToChar($CHAR_CONSTANT.text), $CHAR_CONSTANT.getLine(), $CHAR_CONSTANT.getCharPositionInLine());}
		| ID {$ast = new Variable($ID.text, $ID.getLine(), $ID.getCharPositionInLine());}
		| funcCall {$ast = $funcCall.ast;}
		| '(' expresion ')' {$ast = $expresion.ast;}
		| array=expresion '[' index=expresion ']' {$ast = new ArrayAccess($array.ast, $index.ast, $array.ast.getLine(), $array.ast.getColumn());}
		| structVar=expresion '.' ID {$ast = new StructAccess($structVar.ast, $ID.text, $structVar.ast.getLine(), $structVar.ast.getColumn());}
		| lp='(' buitInType ')' expresion {$ast = new Cast($expresion.ast, $buitInType.ast, $lp.getLine(), $lp.getCharPositionInLine());}
		| minus='-' expresion {$ast = new UnaryMinus($expresion.ast, $minus.getLine(), $minus.getCharPositionInLine());}
		| not='!' expresion {$ast = new BoolNot($expresion.ast, $not.getLine(), $not.getCharPositionInLine());}
		| exp1=expresion operand=('*' | '/' | '%') exp2=expresion {$ast = new Arithmetic($exp1.ast, $exp2.ast, $operand.text, $exp1.ast.getLine(), $exp1.ast.getColumn());}
		| exp1=expresion operand=('+' | '-') exp2=expresion {$ast = new Arithmetic($exp1.ast, $exp2.ast, $operand.text, $exp1.ast.getLine(), $exp1.ast.getColumn());}
		| exp1=expresion operand=('>' | '>=' | '<' | '<=' | '!=' | '==') exp2=expresion {$ast = new Comparison($exp1.ast, $exp2.ast, $operand.text, $exp1.ast.getLine(), $exp1.ast.getColumn());}
		| exp1=expresion operand=('&&' | '||') exp2=expresion {$ast = new Comparison($exp1.ast, $exp2.ast, $operand.text, $exp1.ast.getLine(), $exp1.ast.getColumn());};

funcCall returns [FuncCall ast]:
		ID '(' funcCallParams ')' {$ast = new FuncCall($funcCallParams.ast, $ID.getLine(), $ID.getCharPositionInLine());};

funcCallParams returns [List<Expresion> ast = new ArrayList<Expresion>()]:
		(expresionList {$ast = $expresionList.ast;})?;

//Léxico

LINE_COMMENT: '#'.*?'\r'?('\n'|EOF) -> skip;
MULTILINE_COMMENT: '"""'.*?'"""' -> skip;

WS: [\n\t\r ]+ -> skip;

fragment
LETTER: [a-zA-Z];

ID: (LETTER|'_')(LETTER|[0-9_])*;

INT_CONSTANT: '0'|([1-9][0-9]*);

fragment
REAL_NUM: (INT_CONSTANT?'.'?[0-9]+)|(INT_CONSTANT'.'?[0-9]*);

fragment
REAL_EXPONENT: [eE][+\-]?INT_CONSTANT;

REAL_CONSTANT: REAL_NUM(REAL_EXPONENT)?;
CHAR_CONSTANT: '\''(~[']|('\\'([nt']|INT_CONSTANT)))'\'';