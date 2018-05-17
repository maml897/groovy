package groovy

class Person {
	String name = "Fred"
}

def methodName = "Bob"

Person.metaClass."changeNameTo${methodName}" = {-> delegate.name = "Bob" }

def p = new Person()

println p.name
p.changeNameToBob()
println p.name