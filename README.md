# BidWik - Système d'enchères en ligne

## Points Clés

- **Plateforme complète** : Créée avec Spring Boot pour le backend et Angular pour le frontend.
- **Fonctionnalités utilisateurs** : Inscription, connexion, gestion de profil et inscription aux enchères.
- **Gestion des enchères** : Liste des articles, placement d'enchères, historique et compte à rebours pour chaque mise.
- **Mises à jour en temps réel** : Utilisation de WebSockets avec STOMP pour recevoir des notifications instantanées lors de nouvelles enchères.
- **Sécurité renforcée** : Authentification sécurisée, protection des données avec Spring Security et chiffrement des mots de passe.
- **Participation multiple** : Possibilité de participer simultanément à plusieurs enchères.

## Introduction

Ce système d'enchères en ligne permet aux utilisateurs d'acheter et de vendre des articles via des enchères. Avec une interface conviviale, le projet offre :
- Des mises à jour en temps réel pour suivre les enchères.
- Un compte à rebours pour ajouter de l'urgence dans chaque enchère.
- Un historique complet des enchères pour consulter les actions précédentes.
- Une authentification sécurisée, garantissant que seules les personnes autorisées peuvent placer des enchères.

## Fonctionnalités Principales

1. **Gestion des utilisateurs**  
    - Inscription et connexion sécurisées.
    - Gestion complète du profil utilisateur.

2. **Liste des articles**  
    - Les vendeurs peuvent lister des articles en précisant le prix de départ et la durée de l'enchère.

3. **Enchères en temps réel**  
    - Placement d'enchères par les acheteurs.
    - Notifications instantanées via WebSockets pour les nouvelles mises.

4. **Sécurité**  
    - Protection des données et des communications via HTTPS.
    - Implémentation de Spring Security et chiffrement des mots de passe.

## Détail Intéressant

L'utilisation de **WebSockets** pour les mises à jour en temps réel, notamment pour informer instantanément des nouvelles enchères, améliore considérablement l'expérience utilisateur. La configuration avec **STOMP** permet une communication bidirectionnelle efficace, essentielle pour des interactions rapides et fluides.
