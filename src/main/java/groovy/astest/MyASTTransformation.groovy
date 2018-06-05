package groovy.astest

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class MyASTTransformation  implements ASTTransformation{
	@Override
	void visit(ASTNode[] nodes, SourceUnit source) {
		println("MyASTTransformation visit()  $nodes")
	}
}
