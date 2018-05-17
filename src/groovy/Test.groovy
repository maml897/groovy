package groovy
import com.wish.common.excel.*;

def PoiExcelReader reader;
def PoiExcelWriter writer;
def PoiExcelSheet sheet;

def excel(path,sheetIndex = 0,closure) {
	println("in:$path");
	this.maml=0;
	reader = new PoiExcelReader(path);
	reader.selSheet(sheetIndex);
	writer = new PoiExcelWriter();
	sheet = writer.createSheet("结果信息");

	closure();
	return { x->println "out:$x";writer.write(x) };
}

def getColumns(index) {
	def list=[];
	for (int x = 0; x < reader.getCurRows(); x++) {
		list.add(reader.getOneCell(index, x));
	}
	list;
}

//列
def column(c){
	def list = c();
	for (i in  0..list.size()-1) {
		sheet.addCell(maml, i, list.get(i))
	}
	maml++;
	this
}

def getColumn(){
	def list = getColumns(maml);
	for (i in  0..list.size()-1) {
		sheet.addCell(maml, i, list.get(i))
	}
	maml++;
}

def c = { return [1, 2]}
def averge(a){
	return { return [1, 2, 4, 6, 8]}
}

//多文件，合并单元格
excel("e://test.xls"){
	column averge("a")
	column
	column c
}("e://testout.xls")