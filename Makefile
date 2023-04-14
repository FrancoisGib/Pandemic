compil:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main.java

main:
	java -cp jars/json.jar:classes pandemic.Main

compil2:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main2.java

exec2:
	java -cp jars/json.jar:classes pandemic.Main2

<<<<<<< HEAD
clean:
	rm -rf ./classes/* ./test/pandemic/*.class ./test/pandemic/cards/*.class ./test/pandemic/jsonreader/*.class 
=======
short:
	javac -classpath jars/json.jar:src -d classes src/pandemic/ShortPathTest.java
	java -cp jars/json.jar:classes pandemic.ShortPathTest

clean:
	rm -rf ./classes/* ./test/pandemic/*.class ./test/pandemic/cards/*.class ./test/pandemic/jsonreader/*.class ./test/pandemic/actions/*.class
>>>>>>> François

test_compil:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main2.java
	javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/*.java
	javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/jsonreader/*.java
	javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/cards/*.java
	javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/player/*.java
<<<<<<< HEAD
=======
	javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/actions/*.java
>>>>>>> François

test:
	java -jar jars/test.jar -cp classes:test:jars/json.jar --scan-classpath --disable-banner