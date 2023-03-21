# l2s4-projet-2023

# Equipe

- François GIBIER
- Hugo MATTON
- Oladimeji LATEEF
- Alexis CNOCKAERT

# Sujet

[Le sujet 2023](https://www.fil.univ-lille.fr/portail/index.php?dipl=L&sem=S4&ue=Projet&label=Documents)


# Commandes

Les commandes sont à éxécuter dans le dossier du projet. (Pas classes ou src).

## Compilation :

```
javac -classpath jars/json.jar:src -d classes src/pandemic/Main.java
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
javac -classpath classes:jars/junit-4.13.2.jar test/pandemic/*.java
```

### Execution :

```
java -jar jars/test.jar \
-cp classes:test:jars/json.jar --scan-classpath --disable-banner
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

# Livrables

## Livrable 1
Modélisation des villes, de la carte.

Nous avons choisi de réaliser créer les villes avec la classe Town et la carte avec la classe Map.

A partir de la deuxième semaine, nous nous sommes répartis le travail :
- François a créé les classes.
- Hugo et Alexis ont créé les classes de test.
- Oladimeji a fait le JsonReader afin de lire les données dans un fichier JSON.

Nous n'avons pas rencontré de difficultés particulières pour cette partie du projet, car les classes Town et Map sont relativement simples, là partie la plus difficile a été de trouver la meilleure façon d'initialiser toutes les villes avec le TownsJsonReader.
Nous avons choisi de faire une classe abstraite JsonReader et d'en créer une spécifique TownJsonReader afin de pouvoir rajouter des extensions sur les villes.

Pour montrer comment fonctionne Map, nous avons fait une classe Main qui affiche dans la console toutes les données exploités dans ville48.json.

### Atteinte des objectifs
La carte gère toute les villes du jeu, il y a deux façons d'implémenter les données des villes dans le jeu :
- Avec un fichier JSON, il faudra alors utiliser la fonction setMapWithJSON(String filename) qui va créer un TownsJsonReader qui va lire les données des villes dans le fichier JSON, si le fichier n'existe pas, la fonction lance une FileNotFoundException.
- Avec une ArrayList, on rentre manuellement les données dans la liste et on utilise la fonction setMap(liste) pour initialiser la carte.

### Difficultés restant à résoudre
Nous n'avons pas de difficultés à résoudre pour cette partie du projet, cependant il est probable que certaines classes subissent des modifications durant l'avancement du projet.

## Livrable 2

### Atteinte des objectifs

### Difficultés restant à résoudre

## Livrable 3

### Atteinte des objectifs

### Difficultés restant à résoudre

## Livrable 4

### Atteinte des objectifs

### Difficultés restant à résoudre

# Journal de bord

## Semaine 1
Modélisation du jeu, création de diagrammes UML sur lucid. Mise en commun des idées sur le projet.

## Semaine 2
Répartition des tâches à réaliser.
Création très succincte de la base de plusieurs classes telles que Player, Town, et les différentes cartes du jeu.

## Semaine 3
Travail exclusif sur la Map ainsi que sur les villes (Town), implémentation de celles-ci ainsi que début d'implémentation de leur classe de test.

## Semaine 4
Finitions sur map et town, rédaction du readme pour le livrable 1.

## Semaine 5
Commencement du code de Player ainsi que Cards.

## Semaine 6
Création de CardsStack et finition de Card et de ses sous-classes.

## Semaine 7
Finitions de la classe Player ainsi que l'initialisation des rôles comme Globetrotter.

## Semaine 8
Correction de Town pour pouvoir y mettre plusieurs maladies (nous pensions qu'une ville pouvait être infectée que par une maladie).
Changements dans CardStack ainsi que création de l'exception NoSuchDiseaseException (similaire à NoSuchTownException mais pour les maladies).

## Semaine 9

## Semaine 10

## Semaine 11

## Semaine 12
