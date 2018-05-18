package groovy

class SomeGroovyClass {

	def property1 = 'ha'
	def field2 = 'ho'
	def field4 = 'hu'

	def getField1() {
		return 'getHa'
	}

	def getProperty(String name) {
		if (name != 'field3')
			return metaClass.getProperty(this, name)
		else
			return 'field3'
	}
}

def someGroovyClass = new SomeGroovyClass()

println someGroovyClass.field1
println someGroovyClass.field2
println someGroovyClass.field3
println someGroovyClass.field4