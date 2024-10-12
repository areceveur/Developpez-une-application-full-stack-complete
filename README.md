# Projet 6 - MDD - Monde de Dev

## Description du projet
MDD (Monde de Dev) est un réseau social dédié aux développeurs, visant à faciliter les échanges et la collaboration entre pairs.
Le but est d'aider les développeurs à se connecter, à partager des articles et à commenter des sujets liés à la programmation.
MDD pourrait également devenir une source de recrutement pour les entreprises cherchant des talents dans le domaine du développement.

Cette version est un **MVP** (Minimum Viable Product) déployé en interne,
permettant aux utilisateurs de s'abonner à des thèmes liés à la programmation comme **Java**, **JavaScript**, **Python** ou **Web3**.
Les utilisateurs peuvent publier des articles, suivre des thèmes et commenter des publications.

## Fonctionnalités

L'application permet :

- Aux utilisateurs de s'inscrire et de se connecter
- Afficher des articles triés chronologiquement
- Parcourir la liste des thèmes de programmation et de s'y abonner
- Écrire des articles et des commentaires
- Gérer son profil utilisateur, en modifiant ses informations personnelles et en gérant les abonnements aux thèmes

## Technologies utilisées

- **Back-end** : Spring Boot avec Spring Security pour la sécurisation via JWT
- **Front-end** : Angular, pour une interface utilisateur dynamique et réactive
- **Base de données** : MySQL avec Spring Data JPA pour la gestion des données
- **Authentification** : JSON Web Token (JWT) pour la gestion des sessions utilisateur
- **Tests** : 
  - Junit et Mockito pour les tests unitaires back-end
  - Jest pour les tests front-end

## Installation

1. Cloner le repository

> git clone https://github.com/areceveur/Developpez-une-application-full-stack-complete.git
>
> cd Developpez-une-application-full-stack-complete

2. Back-end

Assurez vous d'avoir Java17 et Maven installés.

Configurez la base de données à partir du fichier back/scr/main/resources/script.sql

Lancer le back-end

> mvn: spring-boot:run

3. Front-end

Assurez-vous d'avoir Node.js et Angular CLI installés.

Installez les dépendances du projet :

> cd front
> 
> npm install

Lancer le serveur de déveleppement

> ng serve

4. Accéder à l'application

- Front-end : http://localhost:4200
- Back-end : http://localhost:8080

## Structure du projet

/back       -> Code du back-end (Java, Spring Boot)

/front      -> Code du front-end (Angular)

/cypress    -> Tests end-to-end (Cypress)

## API endpoints

- POST /api/auth/login : Authentification de l'utilisateur.
- POST /api/auth/register : Inscription d'un nouvel utilisateur.
- GET /api/articles : Récupérer tous les articles.
- POST /api/articles/create : Créer un nouvel article.
- POST /api/articles/{id}/comments : Ajouter un commentaire à un article.
- GET /api/themes : Liste de tous les thèmes.



## Tests

- Back-end : les tests unitaires sont disponibles avec JUnit et Mockito.

Lancer les tests :

> mvn test

- Front-end : Lles tests unitaires pour Angular sont configurés avec Jest.

Lancer les tests :

> npm run test

## Contributeurs

- Orlando : Responsable du projet
- Heidi : Développeur back-end initial
- Juana : Designer UX
- Anne-Elodie : Développeur chargé de finaliser le MVP