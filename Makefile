compil:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main.java
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main2.java

exec1:
	java -cp jars/json.jar:classes pandemic.Main

exec2:
	java -cp jars/json.jar:classes pandemic.Main2