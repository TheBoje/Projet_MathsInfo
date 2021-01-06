package graph;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Cette méthode a pour seul but de tester les méthodes de la classe 
        // GraphSimple.
        // Création d'un graphe :
        Scanner scanner = new Scanner(System.in);
        // Lecture de la taille du graphe
        int size = scanner.nextInt();
        // Initialisation du graphe
        GraphSimple graphe = new GraphSimple(size);
        // Lecture des données d'entrée
        // Pour chaque ligne, on lit le flot de données, et on l'applique au graphe.
        for (int i = 0; i < size; i++) {
            int sommet = scanner.nextInt();
            int[] temp = new int[size];
            int count = 0;
            while (scanner.hasNextInt()) {
                int nextInt = scanner.nextInt();
                if (nextInt == 0) {
                    break;
                } else {
                    temp[count] = nextInt;
                    count++;
                }
            }
            int[] newArray = new int[count];
            System.arraycopy(temp, 0, newArray, 0, count);
            graphe.setAdjacencyList(sommet, newArray);
        }
        // Le flot de données est terminé, on ferme alors le scanner (entrée de données)
        scanner.close();

        // Exercice 1 :
        // a. Voir Enum_Color.java

        // b. Voir GraphSimple.java, section ATTRIBUTS

        // c. Voir GraphSimple.java, section METHODES, méthode initParcoursLargeur

        // d. Voir GraphSimple.java, section METHODES, méthode parcoursLargeur
        // Note : On peut lancer l'algo de parcours en largeur soit sans argument
        // (sommet aléatoire) soit avec un entier en argument (qui est le sommet de
        // départ du parcours)

        // e. On test l'algorithme de parcours sur le graphe Petersen via la commande
        // suivante :
        // java graph.Main < data/graph-002.alists
        // avec la section EXERCICE 1 QUESTION E décommentée (voir ci-dessous)

        // f. On utilise la commande suivante :
        // java graph.Main < data/graph-003.alists
        // avec la section EXERCICE 1 QUESTION F décommentée (voir ci-dessous)
        // Suite au parcours en largeur, on remarque que le graphe n'est pas connexe. Il
        // est composé d'au moins 2 composantes connexes.
        // Le résultat du parcours (sur un sommet aléatoire) est le suivant :
        // Resultat parcours :
        // 1 : [Color : Green  | Distance : -1 | Parent : -1 ]
        // 2 : [Color : Red    | Distance : 2  | Parent :  3 ]
        // 3 : [Color : Red    | Distance : 1  | Parent :  4 ]
        // 4 : [Color : Red    | Distance : 0  | Parent :  4 ]
        // 5 : [Color : Green  | Distance : -1 | Parent : -1 ]
        // 6 : [Color : Green  | Distance : -1 | Parent : -1 ]
        // 7 : [Color : Red    | Distance : 1  | Parent :  4 ]
        // 8 : [Color : Red    | Distance : 1  | Parent :  4 ]
        // 9 : [Color : Green  | Distance : -1 | Parent : -1 ]
        // 10 : [Color : Green | Distance : -1 | Parent : -1 ]
        // 11 : [Color : Green | Distance : -1 | Parent : -1 ]
        // 12 : [Color : Red   | Distance : 1  | Parent :  4 ] 
        // 13 : [Color : Green | Distance : -1 | Parent : -1 ]

        // g. Voir GraphSimple.java, section METHODES, méthode parcoursComplet.

        // Exercice 3 :
        // Voir GraphSimple.java, section METHODES, méthodes testConnexity
        // (test la connéxité en un sommet du graphe), countComposantesConnexe.
        // On peut utiliser la commande suivante pour tester l'algorithme
        // java graph.Main < data/graph-002.alists
        // avec la section EXERCICE 3 décommentée (voir ci-dessous)

        // Note : pour chacun des exercices, il est aussi possible d'utiliser
        // un autre graphe en entrée via son tableau d'adjacence (.alist).

        // ===== EXERCICE 1 QUESTION E =====
        // Commande : java graph.Main < data/graph-002.alists
        // Note : n'oubliez pas de compiler le code apres avoir décommenté.
        // ===== CODE =====
        // graphe.parcoursLargeur(1);

        // ===== EXERCICE 1 QUESTION F =====
        // Commande : java graph.Main < data/graph-003.alists
        // Note : n'oubliez pas de compiler le code apres avoir décommenté.
        // ===== CODE =====
        // graphe.parcoursLargeur();

        // ===== EXERCICE 3 =====
        // Commande : java graph.Main < data/graph-002.alists
        // Note : n'oubliez pas de compiler le code apres avoir décommenté.
        // ===== CODE =====
        //boolean res = graphe.testConnexity();
        //System.out.format("Connexity : %s\n", res ? true : false);
        //int count = graphe.countComposantesConnexe();
        //System.out.format("Composantes connexes : %d\n", count);

        // Conversion de la alist en matrice d'adjacence
        graphe.toMatrix();
        // Affichage des données
        graphe.print();
    }
}
