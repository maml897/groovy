
def ok(i){
	println i
	new Test99();
}

class Test99{
	def getTest1(i){
		println i
		return new Test88()
	}
}

class Test88{
	def test2(i){
		println i
		"okok"
	}
}


ok 6 test1 9 test2 "ok"


ok 6 test1 9
new Test99() test1 9
