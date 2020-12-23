package graph;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Création d'un graphe :
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        GraphSimple graphe = new GraphSimple(size);
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
        scanner.close();
        boolean res = graphe.testConnexity();
        System.out.format("Connexity : %s\n", res ? true : false);
        graphe.toMatrix();
        graphe.print();

    }
}
