import parser.*;

import org.antlr.v4.runtime.*;

import ast.Program;
import errorhandler.EH;
import visitor.Visitor;
import semantic.TypeCheckingVisitor;
import semantic.IdentificationVisitor;

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;
import codegenerator.OffsetVisitor;
import codegenerator.ExecuteCGVisitor;
import codegenerator.CodeGenerator;

public class Main {
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Error - To tun the compiler use:\n\tMain input-file output-file");
			return;
		}
		
		// create a lexer that feeds off of input CharStream
		CharStream input = CharStreams.fromFileName(args[0]);
		PmmLexer lexer = new PmmLexer(input);
		
		// create a parser that feeds off the tokens buffer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		PmmParser parser = new PmmParser(tokens);
		Program ast = parser.program().ast;
		
		//Show the AST
		//IntrospectorModel model = new IntrospectorModel("Program", ast);
		//new IntrospectorTree("Introspector", model);
		
		runVisitor(new IdentificationVisitor(), ast);
		runVisitor(new TypeCheckingVisitor(), ast);
		
		//Check errors 
		if (EH.getEH().hasErrors())
			EH.getEH().showErrors(System.err); //Show errors
		
		else {
			runVisitor(new OffsetVisitor(), ast);
			
			CodeGenerator codeGenerator = new CodeGenerator(args[1], args[0]);
			runVisitor(new ExecuteCGVisitor(codeGenerator), ast);
			codeGenerator.close();
		}
	}
	
	private static void runVisitor(Visitor visitor, Program program) {
		program.accept(visitor, null);
	}
}