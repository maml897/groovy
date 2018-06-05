package groovy.astest

class MyASTTest {
    static def main(args) {
        def parent = MyASTTest.classLoader
        def loader = new GroovyClassLoader(parent)
        def gclass = loader.parseClass(new File("src/main/java/groovy/astest/Person.groovy"))
    }
}
