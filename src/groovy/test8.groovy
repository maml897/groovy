package groovy

class test10{

	def hello(){
		'invoked hello() directly'
	}
	def invokeMethod(String name, Object args){
		"invoked method $name(${args.join(', ')})"
	}
}


def someGroovyClass = new test10()
someGroovyClass.metaClass.author = "Stephen King"


println someGroovyClass.hello()
println someGroovyClass.&hello()
println someGroovyClass.foo('Mark',19)
println someGroovyClass.author
