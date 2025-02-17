Manuel d'utilisation du Framework WINTER MVC

Installation et initialisation
    - Prendre le fichier winter.jar et le mettre dans le lib du projet web
    - Dans le fichier web.xml
        Declarer un servlet de réception de toutes les requêtes, instance de la classe mg.itu.framework.controller.FrontController
        (Son url pattern sera donc /)
        Choisir un package dans lequel on mettra tous les controllers. Son nom sera stocké dans un init-param dont le nom est controllerspackage associé au FrontController
        Declarer un filtre mg.itu.framework.controller.LastRequestFilter pour récupérer la dernière requête

Utilisation MVC
    - Pour chaque classe qui sera un controller, ajouter l'annotation annotation.Controller
    - Pour chaque méthode dans une classe controller qui sera associée à un URL, ajouter l'annotation annotaton.GET avec une url-pattern valide de la forme
    "/**"
    - Chaque méthode devra être annotée avec annotation.GET avec une valeur valide d'url-pattern
    - Les méthodes devront retourner soit un String soit un ModelView

    MODELVIEW
        - Il s'agit d'un objet qu'on construit avec un URL qui représente la view et dans lequel on peut stocker des objets qui seront des attributs de requête.
        - Utiliser la fonction addObject(key,value) pour ajouter un attribut à la requête qui sera utilisable dans le view

Utilisation RestApi JSON
    Même processus que pour le MVC mais:
    - Chaque controller devra être annoté "RestApi"
    - Le controleur peut retourner n'importe quelle valeur qui sera ensuite retournée en JSON
