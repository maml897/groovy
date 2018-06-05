package groovy.aste;

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.ModuleNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.GStringExpression
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import groovy.util.logging.Slf4j

@Slf4j
@GroovyASTTransformation
public class StatementTransformation implements ASTTransformation {
	private def transformations = ['use' : 'using']

	@Override
	void visit(ASTNode[] nodes, SourceUnit source) {
		log.info("Source name = ${source.name}")
		ModuleNode ast = source.ast
		def blockStatement = ast.statementBlock

		blockStatement.visit(new CodeVisitorSupport() {
					void visitConstantExpression(ConstantExpression ce) {
						def name = ce.value
						if (transformations.containsKey(name)) {
							def newName = transformations[name]
							println("Transform Name => $name -> $newName")
							ce.value = newName
						} else {
							println("Skip Name => $name")
						}
					}

					public void visitArgumentlistExpression(ArgumentListExpression ale) {
						println("Arg List $ale.expressions")
						def expressions = ale.expressions
						expressions.eachWithIndex { expr, idx ->
							if(expr.getClass() == GStringExpression) {
								log.debug("Transform GString => String ($expr.text)")
								expressions[idx] = new ConstantExpression(expr.text)
							}
						}
						println("Transformed Arg List $ale.expressions")
						super.visitArgumentlistExpression(ale)
					}
				})
	}
}