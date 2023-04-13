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

### Atteinte des objectifs
La carte gère toute les villes du jeu, il y a deux façons d'implémenter les données des villes dans le jeu :
- Avec un fichier JSON, il faudra alors utiliser la fonction setMapWithJSON(String filename) qui va créer un TownsJsonReader qui va lire les données des villes dans le fichier JSON, si le fichier n'existe pas, la fonction lance une FileNotFoundException.
- Avec une ArrayList, on rentre manuellement les données dans la liste et on utilise la fonction setMap(liste) pour initialiser la carte.

### Difficultés restant à résoudre
Nous n'avons pas de difficultés à résoudre pour cette partie du projet, cependant il est probable que certaines classes subissent des modifications durant l'avancement du projet.

## Livrable 2
Modélisation des cartes, des joueurs et des rôles

Nous avons choisi de réaliser les cartes avec une classe Card et la gestion des cartes dans le jeu est assuré par la classe CardsStack dans le package cards.
Les joueurs sont représentés par une classe abstraite Player et les différents rôles hérites donc de cette classe, on a les rôles Doctor, Globetrotter, Expert et Scientist.

### Atteinte des objectifs
L'initialisation du jeu se fait dans la classe Game, il faut l'initialiser avec une map, des joueurs et des maladies.

Se fait ensuite une succession d'actions qui initialiseront le jeu :
- initCards -> Initialise les cartes du jeu et met en place les paquets de carte.
- initPlayersHand -> Initialise la main de chacun des joueurs et sépare les cartes joueurs en 4 paquets, puis les rassemble en ajoutant 4 cartes epidémie et enfin mélange le paquet de cartes.
- initInitialTown -> Choisi une ville parmi toutes les villes et place tous les joueurs dessus.
- initialInfection -> Réalise l'infection initiale du jeu en prenant 9 cartes dans le paquet des cartes infection avec ce schéma d'infection : 3 premières : 1 cube, 3 suivantes : 2 cube, 3 dernières : 3 cubes.

Après cette succession d'actions, le jeu peut enfin commencer.

### Difficultés restant à résoudre
Nous n'avons pas de difficultés restantes à résoudre, cependant comme indiqué lors du dernier livrable, nous avons dû modifier quelques classes comme Town ou Map car les villes peuvent être infectées par plusieurs maladies à la fois. Nous avons déjà implémenté les rôles ainsi que les actions, mais celles-ci seront sûrement modifiés par la suite.

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
Changements dans Town encore, pour avoir plusieurs clusters à la fois si besoin, ainsi que changements dans cardstack, la défausse sera gérée plus haut dans game (plus besoin du boolean discard), actions modifiées afin de récupérer la valeur de l'action avant puis lancer la méthode run d'une action avec celle-ci. Quelques fix sur le scanner de game pour gérer un mauvais input du joueur, quelques fonctions inutiles supprimées et d'autres simplifiées (setInfectionState par exemple).

## Semaine 10

## Semaine 11

## Semaine 12
