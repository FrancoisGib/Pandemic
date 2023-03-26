compil:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main.java

compil2:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main2.java

exec:
	java -cp jars/json.jar:classes pandemic.Main

exec2:
	java -cp jars/json.jar:classes pandemic.Main2

clean:
	rm -rf ./classes/*