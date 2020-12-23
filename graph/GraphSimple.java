package graph;

import java.util.LinkedList;
import java.util.Random;

public class GraphSimple {

    // ========== ATTRIBUTS ========== //

    // Tableau d'adjacence. voir fichiers .alists
    private int[][] adjacencyTab;
    // Matrice d'adjacence, voir fichiers .matrix
    private int[][] adjacencyMatrix;

    // Relatif au parcours en largeur du graphe
    // Green = Pas encore exploré
    // Orange = Dans la file d'attente
    // Rouge = A été exploré
    private Enum_Color[] colors;
    // Relatif au parcours en largeur du graphe
    // Distance entre le sommet(i) et le sommet initial du parcours en largeur
    private int[] distances;
    // Relatif au parcours en largeur du graphe
    // Indique (par un entier) quel est le parent du sommet(i)
    private int[] parents;

    // Relatif au calcul du nombre de composantes connexes du graphe
    // Stock l'indice de la composante connexe de chaque sommet(i).
    private int[] composantesConnexes;

    private boolean hasDoneLargeur = false;
    private boolean isConnexe = false;

    // ========== CONSTRUCTEUR ========== //

    public GraphSimple(int order) {
        adjacencyTab = new int[order][];
    }

    // ========== GETTERS ========== //

    // Return la liste d'adjacence de <sommet>
    // Note : sommet appartient à [1; this.order()]
    public int[] getAdjacencyList(int sommet) {
        if (this.isVertex(sommet)) {
            return this.adjacencyTab[sommet - 1];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // Retourne l'ordre du graphe
    public int order() {
        return this.adjacencyTab.length;
    }

    // Retourne le degree de <sommet>
    public int degree(int sommet) {
        if (this.isVertex(sommet)) {
            return this.adjacencyTab[sommet].length;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // Retourne si <sommet> est bien un vertex
    public boolean isVertex(int sommet) {
        return (sommet <= this.order() && sommet > 1);
    }

    // Retourne si deux sommets forment une edge
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

    public Enum_Color getColor(int sommet) {
        if (this.isVertex(sommet)) {
            return this.colors[sommet - 1];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int getDistance(int sommet) {
        if (this.isVertex(sommet)) {
            return this.distances[sommet - 1];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int getParent(int sommet) {
        if (this.isVertex(sommet)) {
            return this.parents[sommet - 1];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean getConnexe() {
        return this.isConnexe;
    }

    public int getComposanteConnexe(int sommet) {
        if (this.isVertex(sommet)) {
            return this.composantesConnexes[sommet - 1];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // ========== SETTERS ========== //

    public void setAdjacencyList(int sommet, int[] adjacencyList) {
        if (this.isVertex(sommet)) {
            this.adjacencyTab[sommet - 1] = new int[adjacencyList.length];
            for (int i = 0; i < adjacencyList.length; i++) {
                this.adjacencyTab[sommet - 1][i] = adjacencyList[i];
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setColor(int sommet, Enum_Color color) {
        if (this.isVertex(sommet)) {
            this.colors[sommet - 1] = color;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setDistance(int sommet, int distance) {
        if (this.isVertex(sommet)) {
            this.distances[sommet - 1] = distance;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setParent(int sommet, int parent) {
        if (this.isVertex(sommet)) {
            this.parents[sommet - 1] = parent;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void setConnexe(boolean isConnexe) {
        this.isConnexe = isConnexe;
    }

    public void setComposanteConnexe(int sommet, int valeur) {
        if (this.isVertex(sommet)) {
            this.composantesConnexes[sommet - 1] = valeur;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // ========== METHODES ========== //

    // Permet de transformer le tableau de listes d'adjacence en une matrice
    // d'adjacence. La matrice est stockée dans la variable adjacencyMatrix
    // Note : Pour pouvoir lancer cette méthode, il est impératif que le tableau de
    // listes d'adjacence existe
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

    // Permet de transformer la matrice d'adjacence en son tableau de listes
    // d'adjacence.
    // Note : Pour pouvoir lancer cette méthode, il est impératif que la matrice
    // d'adjacence <adjacencyMatrix> existe
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

    // Responsable de l'initialisation des données relatives à l'études des
    // composantes connexes.
    public void initComposanteConnexe() {
        this.composantesConnexes = new int[this.order()];
        for (int i = 1; i <= this.order(); i++) {
            this.setComposanteConnexe(i, 0);
        }
    }

    // Responsable de l'initialisation des données relatives au parcours en largeur
    // du graphe. Cette initialisation regarde les listes <parents>, <distance> et
    // <colors>
    // Note : si les listes existent déjà, alors on se contente de rétablir les
    // valeurs initiales
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

    // Parcours en largeur du graphe.
    // L'algorithme suit le code couleur suivant pour chaque sommet du graphe :
    // - Green = Le sommet n'a pas encore été exploré
    // - Orange = Le sommet est dans la file d'attente, il sera bientôt exploré
    // - Rouge = Le sommet a été exploré
    // Lors de l'execution de cet algorithme, on défini pour chaque point traversé
    // plusieurs données :
    // - Distance = Nombre d'unité qui sépare le sommet au sommet de départ de
    // l'algorithme
    // - Parent = Sommet auquel il est relié selon le parcours en Largeur. On note
    // que le parent du sommet initial est le sommet initial
    public void parcoursLargeurAux(int sommet_depart) {
        LinkedList<Integer> file = new LinkedList<>();
        // Initilisation de l'algorithme
        file.add(sommet_depart);
        this.setDistance(sommet_depart, 0);
        this.setColor(sommet_depart, Enum_Color.Orange);
        this.setParent(sommet_depart, sommet_depart);
        // Etude successive de chaque sommet dans la file d'attente.
        while (file.size() > 0) {
            // Etude du sommet suivant dans la liste
            int x = file.getFirst();
            file.removeFirst();
            // Passage dans tous les sommets adjacents du sommet x étudié
            for (int y : this.getAdjacencyList(x)) {
                // Dans le cas ou le sommet adjacent n'a pas encore été visité, on l'ajoute dans
                // la file
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
    }

    // Lance le parcours en largeur pour un sommet aléatoire du graphe
    public void parcoursLargeur() {
        Random rn = new Random();
        int sommet = rn.nextInt(this.order() - 1) + 1;
        parcoursLargeur(sommet);
    }

    // Initialise et lance le parcours en largeur pour un sommet spécifique
    public void parcoursLargeur(int sommet) {
        initParcoursLargeur();
        parcoursLargeurAux(sommet);
    }

    // Lance le parcours en largeur de chaque sommet du graphe
    public void parcoursComplet() {
        initParcoursLargeur();
        for (int i = 1; i <= this.order(); i++) {
            if (this.getColor(i) == Enum_Color.Green) {
                parcoursLargeurAux(i);
            }
        }
    }

    // Test de connexité du graphe.
    // Note : il est nécessaire d'avoir réalisé un parcours en largeur du graphe
    // pour avoir un résultat cohérent
    // Si le graphe possède une seule composante connexe, alors retourne true, sinon
    // retourne false
    public boolean testConnexityAux() {
        boolean isConnexe = true;
        for (int i = 1; i <= this.order(); i++) {
            // On sait que le graphe n'est pas connexe si, après un parcours en largeur, un
            // des points n'a pas été visité, c'est à dire que la couleur à laquelle il est
            // associé est Vert
            if (this.getColor(i) == Enum_Color.Green) {
                isConnexe = false;
                break;
            }
        }
        this.setConnexe(isConnexe);
        return isConnexe;
    }

    // Initialise et lance le teste de connexité du graphe.
    public boolean testConnexity() {
        parcoursLargeur();
        return testConnexityAux();
    }

    // Compte le nombre de composantes connexe du graphe.
    public int countComposantesConnexe() {
        int res = 0;
        this.initComposanteConnexe();
        // Pour compter le nombre de composantes connexes du graphe, on fait un parcours
        // en largeur. On ajoute alors tous les points qui on été parcourus (qui sont de
        // couleur Rouge) dans la même composante connexe. On parcours alors un des
        // sommet qui n'a pas encore été étudié, jusqu'à ce que tous les points soient
        // associé à une composante connexe.
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
        return res;
    }

    // ========== I/O ========== //
    // L'ensemble de ces fonctions servent uniquement à l'affichage des résultat, et
    // permettent de vérifier l'entrée des données et le résultat des calculs.

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