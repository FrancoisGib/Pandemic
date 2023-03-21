compil:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main.java
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main2.java
	javac -classpath jars/json.jar:src -d classes src/pandemic/MainTest.java

exec1:
	java -cp jars/json.jar:classes pandemic.Main

exec2:
	java -cp jars/json.jar:classes pandemic.Main2

exec3:
	java -cp jars/json.jar:classes pandemic.MainTest