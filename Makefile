clean:
	rm -rf ./classes/* ./test/pandemic/*.class ./test/pandemic/cards/*.class ./test/pandemic/jsonreader/*.class ./test/pandemic/actions/*.class ./test/pandemic/player/*.class ./jar/jeu.jar ./docs

test:
	javac -classpath jar/json.jar:src -d classes src/pandemic/Main.java
	javac -classpath classes:jar/junit-4.13.2.jar test/pandemic/*.java
	javac -classpath classes:jar/junit-4.13.2.jar test/pandemic/jsonreader/*.java
	javac -classpath classes:jar/junit-4.13.2.jar test/pandemic/cards/*.java
	javac -classpath classes:jar/junit-4.13.2.jar test/pandemic/player/*.java
	javac -classpath classes:jar/junit-4.13.2.jar test/pandemic/actions/*.java
	java -jar jar/test.jar -cp classes:test:jar/json.jar --scan-classpath --disable-banner

doc:
	javadoc -classpath jar/json.jar:src -d docs -subpackages pandemic

cls:
	javac -classpath jar/json.jar:src -d classes src/pandemic/Main.java

jeu.jar:
	cd classes; jar xvf ../jar/json.jar; cd ..
	jar cvfm jar/jeu.jar manifest.txt -C classes pandemic -C classes org

play:
	java -jar jar/jeu.jar villes48.json 4 1