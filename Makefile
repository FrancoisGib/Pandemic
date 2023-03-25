compil:
	javac -classpath jars/json.jar:src -d classes src/pandemic/Main.java

exec:
	java -cp jars/json.jar:classes pandemic.Main

clean:
	rm -rf ./classes/*