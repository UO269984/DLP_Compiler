if not exist bin (
	mkdir bin
)
javac -encoding utf-8 -d bin -cp "src;lib/antlr-4.9.1-complete.jar;lib/introspector.jar"^
	src/util/*.java^
	src/ast/*.java^
	src/ast/util/*.java^
	src/ast/expresions/*.java^
	src/ast/statements/*.java^
	src/ast/types/*.java^
	src/parser/*.java^
	src/errorhandler/*.java^
	src/symboltable/*.java^
	src/visitor/*.java^
	src/semantic/*.java^
	src/codegenerator/*.java^
	src/*.java