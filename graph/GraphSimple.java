package graph;

import java.util.LinkedList;
import java.util.Random;

public class GraphSimple {
    private int[][] adjacencyTab;
    private int[][] adjacencyMatrix;
    private Enum_Color[] colors;
    private int[] distances;
    private int[] parents;
    private int[] composantesConnexes;
    private boolean hasDoneLargeur = false;
    private boolean isConnexe = false;

    public GraphSimple(int n) {
        adjacencyTab = new int[n][];
    }

    public void setAdjacencyList(int sommet, int[] adjacencyList) {
        this.adjacencyTab[sommet - 1] = new int[adjacencyList.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            this.adjacencyTab[sommet - 1][i] = adjacencyList[i];
        }
    }

    public int[] getAdjacencyList(int sommet) {
        return this.adjacencyTab[sommet - 1];
    }

    public int order() {
        return this.adjacencyTab.length;
    }

    public int degree(int sommet) {
        return this.adjacencyTab[sommet].length;
    }

    public boolean isVertex(int sommet) {
        return (sommet <= this.order() && sommet > 1);
    }

    public boolean isEdge(int x, int y) {
        boolean result = false;
        for (int i : this.adjacencyTab[x]) {
            if (i == y) {
                result = true;
                break;
            }
        }
        return result;
    }

    public int[][] toMatrix() {
        this.adjacencyMatrix = new int[this.adjacencyTab.length][this.adjacencyTab.length];
        for (int i = 0; i < this.adjacencyTab.length; i++) {
            for (int j = 0; j < this.adjacencyTab.length; j++) {
                this.adjacencyMatrix[i][j] = 0;
            }
        }
        for (int i = 0; i < this.adjacencyTab.length; i++) {
            for (int j = 0; j < this.adjacencyTab[i].length; j++) {
                this.adjacencyMatrix[i][this.adjacencyTab[i][j] - 1] = 1;
            }
        }
        return this.adjacencyMatrix;
    }

    public int[][] fromMatrix() {

        for (int i = 0; i < this.adjacencyMatrix.length; i++) {
            int count = 0;
            for (int j = 0; j < this.adjacencyMatrix.length; j++) {
                if (this.adjacencyMatrix[i][j] != 0) {
                    count++;
                }
            }
            this.adjacencyTab[i] = new int[count + 1];
            this.adjacencyTab[i][0] = i + 1;
            int temp = 1;
            for (int j = 1; j < this.adjacencyMatrix.length; j++) {
                if (this.adjacencyMatrix[i][j] != 0) {
                    this.adjacencyTab[i + 1][temp] = j + 1;
                    temp++;
                }
            }
        }
        return this.adjacencyTab;
    }

    public void setColor(int sommet, Enum_Color color) {
        this.colors[sommet - 1] = color;
    }

    public Enum_Color getColor(int sommet) {
        return this.colors[sommet - 1];
    }

    public void setDistance(int sommet, int distance) {
        this.distances[sommet - 1] = distance;
    }

    public int getDistance(int sommet) {
        return this.distances[sommet - 1];
    }

    public void setParent(int sommet, int parent) {
        this.parents[sommet - 1] = parent;
    }

    public int getParent(int sommet) {
        return this.parents[sommet - 1];
    }

    public void setConnexe(boolean isConnexe) {
        this.isConnexe = isConnexe;
    }

    public boolean getConnexe() {
        return this.isConnexe;
    }

    public void setComposanteConnexe(int sommet, int valeur) {
        this.composantesConnexes[sommet - 1] = valeur;
    }

    public int getComposanteConnexe(int sommet) {
        return this.composantesConnexes[sommet - 1];
    }

    public void initComposanteConnexe() {
        this.composantesConnexes = new int[this.order()];
        for (int i = 1; i <= this.order(); i++) {
            this.setComposanteConnexe(i, 0);
        }
    }

    public void initParcoursLargeur() {
        if (parents == null) {
            parents = new int[this.order()];
        }
        if (distances == null) {
            distances = new int[this.order()];
        }
        if (colors == null) {
            colors = new Enum_Color[this.order()];
        }
        for (int i = 0; i < this.order(); i++) {
            colors[i] = Enum_Color.Green;
            parents[i] = -1;
            distances[i] = -1;
        }
    }

    public void parcoursLargeurAux(int sommet_depart) {
        LinkedList<Integer> file = new LinkedList<>();
        file.add(sommet_depart);
        this.setDistance(sommet_depart, 0);
        this.setColor(sommet_depart, Enum_Color.Orange);
        this.setParent(sommet_depart, 0); // le parent du sommet de départ = 0 ou lui-meme ? sommet
                                          // 0 -> null

        while (file.size() > 0) {
            int x = file.getFirst();
            file.removeFirst();
            for (int y : this.getAdjacencyList(x)) {
                if (y != x && this.getColor(y) == Enum_Color.Green) {
                    this.setColor(y, Enum_Color.Orange);
                    this.setDistance(y, this.getDistance(x) + 1);
                    this.setParent(y, x);
                    file.addLast(y);
                }
            }
            this.setColor(x, Enum_Color.Red);
        }
        hasDoneLargeur = true;
        System.out.println("Parcours en largeur terminé\n");
    }

    public void parcoursLargeur() {
        initParcoursLargeur();
        Random rn = new Random();
        int sommet = rn.nextInt(this.order() - 1) + 1;
        parcoursLargeurAux(sommet);
    }

    public void parcoursLargeur(int sommet) {
        initParcoursLargeur();
        parcoursLargeurAux(sommet);
    }

    public void parcoursComplet() {
        initParcoursLargeur();
        for (int i = 1; i <= this.order(); i++) {
            if (this.getColor(i) == Enum_Color.Green) {
                parcoursLargeurAux(i);
            }
        }
    }

    public boolean testConnexityAux() {
        boolean isConnexe = true;
        for (int i = 1; i <= this.order(); i++) {
            if (this.getColor(i) == Enum_Color.Green) {
                isConnexe = false;
                break;
            }
        }
        this.setConnexe(isConnexe);
        return isConnexe;
    }

    public boolean testConnexity() {
        parcoursLargeur();
        return testConnexityAux();
    }

    public int countComposantesConnexe() {
        int res = 0;
        this.initComposanteConnexe();
        for (int i = 1; i <= this.order(); i++) {
            if (this.getComposanteConnexe(i) == 0) {
                parcoursLargeur(i);
                for (int j = 1; j <= this.order(); j++) {
                    if (this.getColor(j) == Enum_Color.Red) {
                        // Dans le cas ou la couleur du sommet est rouge, c'est à
                        // dire que le sommet fait partie de la composante connexe
                        // étudiée
                        this.setComposanteConnexe(j, i);
                    }
                }
                res++;
            }
        }
        return res; // TODO
    }

    public void printMatrix() {
        System.out.print("Matrix :\n");
        for (int i = 0; i < this.adjacencyMatrix.length; i++) {
            for (int j = 0; j < this.adjacencyMatrix[0].length; j++) {
                System.out.format("%d ", this.adjacencyMatrix[i][j]);
            }
            System.out.format("\n");
        }
    }

    public void printAdjacencyTab() {
        System.out.print("Adjacency tab :\n");
        for (int i = 0; i < this.adjacencyTab.length; i++) {
            System.out.format("%d ", i + 1);
            for (int j = 0; j < this.adjacencyTab[i].length; j++) {
                System.out.format("%d ", this.adjacencyTab[i][j]);
            }
            System.out.format("\n");
        }
    }

    public void printParcours() {
        System.out.print("Resultat parcours :\n");
        for (int i = 1; i <= this.order(); i++) {
            System.out.format("%d : [Color : %s | Distance : %d | Parent : %d]\n", i, this.getColor(i).toString(),
                    this.getDistance(i), this.getParent(i));

        }
    }

    public void printComposanteConnexe() {
        System.out.println("Composante connexe :");
        for (int i = 1; i <= this.order(); i++) {
            System.out.format("[Sommet : %d | CC : %d]\n", i, this.getComposanteConnexe(i));
        }
    }

    public void print() {
        if (this.adjacencyMatrix != null) {
            printMatrix();
        }
        if (this.adjacencyTab != null) {
            printAdjacencyTab();
        }
        if (this.hasDoneLargeur) {
            printParcours();
        }
        if (this.composantesConnexes != null) {
            printComposanteConnexe();
        }
    }
}