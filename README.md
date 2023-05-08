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
Si une commande du makefile ne s'exécute pas, il faut rajouter le flag -B

## Compilation :

```bash
make cls
```

## Execution :

```bash
java -cp jars/json.jar:classes pandemic.Main villes48.json 4
```

Pour pouvoir jouer soi-même au jeu, il faut rajouter un autre argument quelconque.
```bash
java -cp jars/json.jar:classes pandemic.Main villes48.json 4 1
```

## Documentation :

```bash
make doc
```

## Tests :

### Compilation :

```bash
make test
```

## JAR :

### Créer JAR :

```bash
make jeu.jar
```

### Execution :

```bash
java -jar jar/jeu.jar villes48.json 4
```

```bash
java -jar jar/jeu.jar villes48.json 3
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

Nous avons choisi de réaliser les cartes avec une classe **Card** et la gestion des cartes dans le jeu est assuré par la classe **CardsStack** dans le package cards.
Les joueurs sont représentés par une classe abstraite **Player** et les différents rôles hérites donc de cette classe, on a les rôles Doctor, Globetrotter, Expert et Scientist.

### Atteinte des objectifs
L'initialisation du jeu se fait dans la classe **Game**, il faut l'initialiser avec une map, des joueurs et des maladies.

Se fait ensuite une succession d'actions qui initialiseront le jeu :
- ***initCards*** -> Initialise les cartes du jeu et met en place les paquets de carte.
- ***initPlayersHand*** -> Initialise la main de chacun des joueurs et sépare les cartes joueurs en 4 paquets, puis les rassemble en ajoutant 4 cartes epidémie et enfin mélange le paquet de cartes.
- ***initInitialTown*** -> Choisi une ville parmi toutes les villes et place tous les joueurs dessus.
- ***initialInfection*** -> Réalise l'infection initiale du jeu en prenant 9 cartes dans le paquet des cartes infection avec ce schéma d'infection : 3 premières : 1 cube, 3 suivantes : 2 cube, 3 dernières : 3 cubes.

Après cette succession d'actions, le jeu peut enfin commencer.

### Difficultés restant à résoudre
Nous n'avons pas de difficultés restantes à résoudre, cependant comme indiqué lors du dernier livrable, nous avons dû modifier quelques classes comme Town ou Map car les villes peuvent être infectées par plusieurs maladies à la fois. Nous avons déjà implémenté les rôles ainsi que les actions, mais celles-ci seront sûrement modifiés par la suite.

## Livrable 3
Nous avons décidé de modéliser les actions à l'aide d'un interface **Action** et chacune des actions possèdent une méthode ***chooseParameter*** qui permet à l'utilisateur de choisir parmi les propositions liées aux actions. De plus, l'interface implémente une méthode ***run*** pour lancer l'action et une ***requirements*** qui assure que l'action est bien réalisable pour ce joueur.
Pour faciliter les actions, les joueurs ont été modifiés, un joueur est maintenant caractérisé par son nom et son rôle (enum Role) et la classe **Joueur** n'est plus une classe abstraite. Grâce à ces changements, il est maintenant très facile de rajouter des actions.
Les disease ont aussi subit des changements liés au nombre de cubes disponibles pour une maladie.

### Atteinte des objectifs
Les objectifs sont atteints, il ne reste plus que quelques détails à régler, notamment le nombre de clusters maximal ainsi que certaines conditions de défaites.

## Livrable 4
Les derniers détails ont été réglés, nous avons terminé les dernières méthodes de tests ainsi que la documentation. Certaines classes et méthodes ont été simplifiées en terme de complexité de code (notamment certaines actions et la classe game). Nous avons rendu le jeu plus ergonomique pour être joué dans le terminal.

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
Changements de quelques méthodes dans game, création de certains Tests, update des UML ainsi que réflexion sur certaines améliorations (supprimer certaines méthodes presque inutiles ou simplifiables), reflexion pour move (rechercher les villes atteignables avec le nombre d'actions restantes pour faciliter les déplacements sur de longues distances). 

## Semaine 10
Optimisations de certaines méthodes et actions (surtout move), code de dernières méthodes de tests.

## Semaine 11
Derniers fix du jeu et réécriture des diagrammes UML (version finale)

## Semaine 12
Préparation de la soutenance.