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

        // ===== EXERCICE 1 QUESTION E =====
        // graphe.parcoursLargeur(1);

        // ===== EXERCICE 1 QUESTION F =====
        // graphe.parcoursLargeur();

        // ===== EXERCICE 3 =====
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
