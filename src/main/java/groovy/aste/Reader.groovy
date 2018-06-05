package groovy.aste

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

import groovyjarjarantlr.Parser

public class Reader {
	static def createNewShell() {
		def secureCustomizer = new SecureASTCustomizer()
		secureCustomizer.with {
			methodDefinitionAllowed = false // user will not be able to define methods
			importsWhitelist = [] // empty whitelist means imports are disallowed
			staticImportsWhitelist = [] // same for static imports
			staticStarImportsWhitelist = []
		}

		def astCustomizer = new ASTTransformationCustomizer(new StatementTransformation())
		def config = new CompilerConfiguration()
		config.addCompilationCustomizers(secureCustomizer,astCustomizer)
		
		new GroovyShell(config)
	}

	
	static def main(args) {
		def delta=new File("src/main/java/groovy/aste/example.groovy")
		def deltaName = delta.name
		def dslCode = """{-> $delta.text}"""
		
		def shell = createNewShell()
		def deltaObject = shell.evaluate(dslCode, deltaName)
		
//		def shell = createNewShell()
//		shell.setVariable('robot', "maml")
//		println shell.evaluate("robot");
		
	}
	
	
}