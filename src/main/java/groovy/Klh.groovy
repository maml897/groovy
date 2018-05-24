package groovy

/**
 * groovy的柯里化
 */
def test = { a,b,c -> a+b+c }      
def testfun = test.curry(10)   
println testfun(3, 4)       



def tellFortunes(closure) {
	Date date = new Date("09/20/2012")
	postFortune = closure.curry(date)
	postFortune "Your day is filled with ceremony"
	postFortune "You are features, not bugs"
}

tellFortunes() {date,fortune ->
	println "Fortune for ${date} is '${fortune}'"
}