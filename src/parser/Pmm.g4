grammar Pmm;

@header {
	import ast.*;
	import ast.types.*;
	import ast.statements.*;
	import ast.expressions.*;
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
		def='def' ID '(' paramsDef ')' {FuncType funcType = new FuncType($paramsDef.ast);}
		':' (buitInType {funcType.setRetType($buitInType.ast);})?
		{$ast = new FuncDefinition($ID.text, funcType, $def.getLine(), $def.getCharPositionInLine());}
		'{' (varDef {$ast.addVarsDef($varDef.ast);})* (statement {$ast.addStatement($statement.ast);})* '}';

mainDef returns [FuncDefinition ast]:
		def='def' funcName='main' '(' paramsDef ')' {FuncType funcType = new FuncType($paramsDef.ast);}
		':' (buitInType {funcType.setRetType($buitInType.ast);})?
		{$ast = new FuncDefinition($funcName.text, funcType, $def.getLine(), $def.getCharPositionInLine());}
		'{' (varDef {$ast.addVarsDef($varDef.ast);})* (statement {$ast.addStatement($statement.ast);})* '}';

statement returns [Statement ast]:
		statement_main ';' {$ast = $statement_main.ast;}
		| ifStatement {$ast = $ifStatement.ast;}
		| whileKey='while' expression ':' statements_block {
			$ast = new WhileLoop($expression.ast, $statements_block.ast, $whileKey.getLine(), $whileKey.getCharPositionInLine());
		};

ifStatement returns [IfCond ast]:
		ifKey='if' expression ':' ifStatements=statements_block
		{$ast = new IfCond($expression.ast, $ifStatements.ast, $ifKey.getLine(), $ifKey.getCharPositionInLine());}
		('else' elseStatements=statements_block {$ast.setElseStatements($elseStatements.ast);})?;

statement_main returns [Statement ast]:
		input='input' expressionList {$ast = new Input($expressionList.ast, $input.getLine(), $input.getCharPositionInLine());}
		| print='print' expressionList {$ast = new Print($expressionList.ast, $print.getLine(), $print.getCharPositionInLine());}
		| ret='return' expression {$ast = new FuncReturn($expression.ast, $ret.getLine(), $ret.getCharPositionInLine());}
		| toAsign=expression '=' value=expression {$ast = new VarAssigment($toAsign.ast, $value.ast, $toAsign.ast.getLine(), $toAsign.ast.getColumn());}
		| funcCall {$ast = $funcCall.ast;};

statements_block returns [List<Statement> ast = new ArrayList<Statement>()]:
		statement {$ast.add($statement.ast);}
		| '{' (statement {$ast.add($statement.ast);})* '}';

expressionList returns [List<Expression> ast = new ArrayList<Expression>()]:
		(expInList=expression ',' {$ast.add($expInList.ast);})* lastExp=expression {$ast.add($lastExp.ast);};

type returns [Type ast]:
		buitInType {$ast = $buitInType.ast;}
		| struct {$ast = $struct.ast;}
		| '[' INT_CONSTANT ']' type {$ast = new ArrayType($type.ast, LexerHelper.lexemeToInt($INT_CONSTANT.text));};

buitInType returns [Type ast]:
		'int' {$ast = Types.getInt();}
		| 'double' {$ast = Types.getDouble();}
		| 'char' {$ast = Types.getChar();};

struct returns [StructType ast = new StructType()]:
		'struct' '{' (varDef {$ast.addFields($varDef.ast);})* '}';

expression returns [Expression ast]:
		INT_CONSTANT {$ast = new IntLiteral(LexerHelper.lexemeToInt($INT_CONSTANT.text), $INT_CONSTANT.getLine(), $INT_CONSTANT.getCharPositionInLine());}
		| REAL_CONSTANT {$ast = new DoubleLiteral(LexerHelper.lexemeToReal($REAL_CONSTANT.text), $REAL_CONSTANT.getLine(), $REAL_CONSTANT.getCharPositionInLine());}
		| CHAR_CONSTANT {$ast = new CharLiteral(LexerHelper.lexemeToChar($CHAR_CONSTANT.text), $CHAR_CONSTANT.getLine(), $CHAR_CONSTANT.getCharPositionInLine());}
		| ID {$ast = new Variable($ID.text, $ID.getLine(), $ID.getCharPositionInLine());}
		| funcCall {$ast = $funcCall.ast;}
		| '(' expression ')' {$ast = $expression.ast;}
		| array=expression '[' index=expression ']' {$ast = new ArrayAccess($array.ast, $index.ast, $array.ast.getLine(), $array.ast.getColumn());}
		| structVar=expression '.' ID {$ast = new StructAccess($structVar.ast, $ID.text, $structVar.ast.getLine(), $structVar.ast.getColumn());}
		| lp='(' buitInType ')' expression {$ast = new Cast($expression.ast, $buitInType.ast, $lp.getLine(), $lp.getCharPositionInLine());}
		| minus='-' expression {$ast = new UnaryMinus($expression.ast, $minus.getLine(), $minus.getCharPositionInLine());}
		| not='!' expression {$ast = new BoolNot($expression.ast, $not.getLine(), $not.getCharPositionInLine());}
		| exp1=expression operand=('*' | '/' | '%') exp2=expression {$ast = new Arithmetic($exp1.ast, $exp2.ast, $operand.text, $exp1.ast.getLine(), $exp1.ast.getColumn());}
		| exp1=expression operand=('+' | '-') exp2=expression {$ast = new Arithmetic($exp1.ast, $exp2.ast, $operand.text, $exp1.ast.getLine(), $exp1.ast.getColumn());}
		| exp1=expression operand=('>' | '>=' | '<' | '<=' | '!=' | '==') exp2=expression {$ast = new Comparison($exp1.ast, $exp2.ast, $operand.text, $exp1.ast.getLine(), $exp1.ast.getColumn());}
		| exp1=expression operand=('&&' | '||') exp2=expression {$ast = new LogicOperation($exp1.ast, $exp2.ast, $operand.text, $exp1.ast.getLine(), $exp1.ast.getColumn());};

funcCall returns [FuncCall ast]:
		ID '(' funcCallParams ')' {$ast = new FuncCall($ID.text, $funcCallParams.ast, $ID.getLine(), $ID.getCharPositionInLine());};

funcCallParams returns [List<Expression> ast = new ArrayList<Expression>()]:
		(expressionList {$ast = $expressionList.ast;})?;

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