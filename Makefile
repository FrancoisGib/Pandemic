compil:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main.java

compil2:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main2.java

exec:
	java -cp jars/json.jar:classes pandemic.Main

exec2:
	java -cp jars/json.jar:classes pandemic.Main2

clean:
	rm -rf ./classes/* ./test/pandemic/*.class

compil_test:
	javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/*.java

exec_test:
	java -jar jars/test.jar -cp classes:test:jars/json.jar --scan-classpath --disable-banner