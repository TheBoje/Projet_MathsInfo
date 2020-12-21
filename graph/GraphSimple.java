package graph;

import java.util.LinkedList;

public class GraphSimple {
    private int[][] adjacencyTab;
    private int[][] adjacencyMatrix;
    private Enum_Color[] colors;
    private int[] distances;
    private int[] parents;
    private boolean hasDoneLargeur = false;

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

    public void initParcoursLargeur() {
        parents = new int[this.order()];
        distances = new int[this.order()];
        colors = new Enum_Color[this.order()];
        for (int i = 0; i < this.order(); i++) {
            colors[i] = Enum_Color.Green;
        }
    }

    public void parcoursLargeur(int sommet_depart) {
        initParcoursLargeur();
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
        for (int i = 0; i < this.order(); i++) {
            System.out.format("%d : [Color : %s | Distance : %d | Parent : %d]\n", i + 1,
                    this.getColor(i + 1).toString(), this.getDistance(i + 1), this.getParent(i + 1));

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
    }
}