package groovy

def fun(a){
	a("jajaa");
}
fun{
	println it;
}

println "------------------------------------"

def fun1(a,b){
	println "$a"
}
fun1"ddd","dd"

println "------------------------------------"

def fun2(a,b){
	b()
}
fun2("ddd"){
	println "cc"
}