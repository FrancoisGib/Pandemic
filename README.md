# Pandemic

Pandemic est un jeu de stratégie où plusieurs épidémies se répandent sur une carte, le but est donc d'empêcher ces maladies de contaminer toute la carte.

# Commandes

Les commandes sont à éxécuter à la racine du projet.

## Compilation :

```
javac -classpath jars/json.jar:src -d classes src/pandemic/Main.java
```

Ou à partir du Makefile :

```
make main
```

## Execution :

```
java -cp jars/json.jar:classes pandemic.Main
```

## Documentation :

```
javadoc -classpath jars/json.jar:src -d docs -subpackages pandemic
```

## Tests :

### Compilation :

```
javac -classpath jars/json.jar:src -d classes src/pandemic/Main2.java
javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/*.java
javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/jsonreader/*.java
javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/cards/*.java
javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/player/*.java
```

Ou à partir du Makefile :

```
make test_compil
```

### Execution :

```
java -jar jars/test.jar \
-cp classes:test:jars/json.jar --scan-classpath --disable-banner
```

Ou à partir du Makefile :

```
make test
```

## JAR :

### Extraire :

```
cd classes; jar xvf ../jars/json.jar; cd ..
```

### Créer JAR :

```
jar cvfm appli.jar manifest.txt -C classes pandemic -C classes org
```

### Execution :

```
java -jar appli.jar
```
