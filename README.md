Manuel d'utilisation du Framework WINTER MVC
    - Prendre le fichier winter.jar et le mettre dans le lib du projet web
    - Dans le fichier web.xml
        Declarer un servlet de réception de toutes les requêtes, instance de la classe mg.itu.framework.controller.FrontController
        (Son url pattern sera donc /)
        Choisir un package dans lequel on mettra tous les controllers. Son nom sera stocké dans un init-param associé au FrontController