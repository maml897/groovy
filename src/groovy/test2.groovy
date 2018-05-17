package groovy

class Person1 {
	String name = "Fred"

	def getProperty(String n){
		def metaProperty = Person1.metaClass.getMetaProperty(n)
		def result
		if(metaProperty) {
			result = metaProperty.getProperty(this)
		}
		else {
			result = "Flintstone"
		}
		result
	}
}

//Person1.metaClass.getProperty = { String name ->
//	def metaProperty = Person1.metaClass.getMetaProperty(name)
//	def result
//	if(metaProperty) result = metaProperty.getProperty(delegate)
//	else {
//		result = "Flintstone"
//	}
//	result
//}

def p = new Person1()

println p.name
println p.other