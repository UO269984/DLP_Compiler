@echo off

echo Parser
java -ea -cp "bin;lib/antlr-4.9.1-complete.jar" parser.LexerTest ..\P3\input.txt

echo Semantic
java -ea -cp "bin;lib/antlr-4.9.1-complete.jar" parser.TestRig ..\P4\input.txt

echo Symbol Table
java -ea -cp "bin;lib/antlr-4.9.1-complete.jar" symboltable.SymbolTableTest

echo on