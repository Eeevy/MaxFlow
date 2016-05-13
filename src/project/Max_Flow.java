package project;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Klassen representerar en bipartit matchning. I varje graf finns en source-nod
 * och en sink-nod, vilka utgör en del av flödesschemat. Inledningsvis ombes
 * användaren att mata in antal noder på vänster respektive höger sida av
 * grafen. Vidare uppmanas denne även att mata in kopplingar mellan noderna på
 * respektive sida av grafen. Programmet ser till att det alltid finns en
 * koppling mellan source och grafens vänstra noder samt mellan sink och grafens
 * högra noder. Vid exekvering av programmet appliceras dessa kopplingar och med
 * hjälp av en Ford-Fulkerson-algoritm samt Bredden-Först-sökning undersöks
 * antalet distinkta vägar i grafen. Programmet skriver slutligen ut detta antal
 * samt de kopplingar som använts.
 * 
 * @authors Emma Shakespeare och Evelyn Gustavsson
 *
 */
public class Max_Flow {
	// Instansvariabler.
	static Scanner scanner = new Scanner(System.in);
	private static int[][] graph;
	private LinkedList<Integer> queue = new LinkedList<Integer>(); // Kö.
	private int[] path; // Väg.
	// Boolean som beskriver om en nod är besökt eller inte.
	private boolean[] visited;

	/**
	 * Metod som letar efter en unik (obesökt) väg i grafen, genom en
	 * Bredden-Först-sökning.
	 * 
	 * @param graph
	 *            - en tvådimensionell heltalsarray som en graf
	 * @param source
	 *            - den nod som är längst till vänster.
	 * @param sink
	 *            - den nod som är längst till höger.
	 * @return pathFound - Returnerar en boolean som är true om en väg hittas
	 *         och false om den inte hittas.
	 */
	public boolean bfs(int[][] graph, int source, int sink) {
		boolean pathFound = false;

		for (int i = 0; i < graph.length; i++) {
			visited[i] = false;
		}

		queue.add(source);
		visited[source] = true;
		path[source] = -1;

		while (queue.size() != 0) {
			int element = queue.removeFirst();

			for (int j = 0; j < graph.length; j++) {
				if (!visited[j] && graph[element][j] > 0) {
					visited[j] = true;
					path[j] = element;
					queue.add(j);
				}
			}
		}

		if (visited[sink]) {
			pathFound = true;
		}
		return pathFound;
	}

	/**
	 * Metoden använder Ford Fulkersons-algoritm för att hitta det maximala
	 * flödet i nätverket.
	 * 
	 * @param graph
	 *            - en tvådimensionell heltalsarray som en graf
	 * 
	 * @param source
	 *            - den nod som är längst till vänster.
	 * @param sink
	 *            - den nod som är längst till höger.
	 * @return maxFlow - det maximala flödet.
	 */
	public int fordFulkersonAlgorithm(int[][] graph, int source, int sink) {
		int maxFlow = 0;

		while (bfs(graph, source, sink)) {
			int v = sink;
			int flowCapacity = Integer.MAX_VALUE;
			while (v != source) {
				int u = path[v];
				int weight = graph[u][v];
				flowCapacity = Math.min(flowCapacity, weight);
				v = path[v];
			}

			v = sink;
			while (v != source) {
				int u = path[v];
				graph[u][v] -= flowCapacity;
				graph[v][u] += flowCapacity;
				if (graph[v][u] > 0) {
					if (v < sink && u > source) {
						System.out.print("Koppling mellan nod: " + u + " och " + v);
						System.out.println();
					}
				}
				v = path[v];
			}
			maxFlow += flowCapacity;
		}
		return maxFlow;
	}

	/**
	 * Initierar en graf med storleken som användaren matar in.
	 * 
	 * @param leftSize
	 *            - Antal noder på vänster sida.
	 * @param rightSize
	 *            - Antal noder på höger sida.
	 */
	public void createGraph(int leftSize, int rightSize) {
		// Lägger till source på vänstersidan och sink på högersidan.
		int leftNodes = leftSize + rightSize + 2;
		int rightNodes = leftSize + rightSize + 2;
		int largeArraySize = leftNodes + rightNodes;
		this.path = new int[largeArraySize];
		this.visited = new boolean[largeArraySize];
		this.graph = new int[leftNodes][rightNodes];

		// Sätter ut koppling mellan source noden och övriga noder på vänster
		// sida
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {

				if ((i == 0 && j > 0 && j <= leftSize )
						|| (j == graph.length - 1 && i >= (graph.length - (rightSize + 1)) && i < graph.length - 1)) {
					graph[i][j] = 1;
//	                graph[i][graph.length - 1] = 1;

				} else {
					graph[i][j] = 0;
				}
			}
		}
	}

	/**
	 * Returnerar en graf.
	 * 
	 * @return graph
	 */
	public int[][] getGraph() {
		return this.graph;
	}

	/**
	 * Metod som ordnar så att det finns en indirekt koppling mellan
	 * source-noden och grafens vänstra noder samt mellan sink-noden och grafens
	 * högra noder.
	 * 
	 * @param left - Representerar
	 *            koppling på grafens vänstra sida.
	 * @param right
	 *            - Representerar koppling på grafens högra sida.
	 */
	public void addConnection(int left, int right) {
		graph[left][right] = 1;
	}

	/**
	 * Skriver ut en visuell version om grafen.
	 */
	public void printGrid(int[][] graph) {
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				System.out.printf("%4d ", graph[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * Läser in och returnerar användarens inmatning.
	 *  
	 * @return input
	 */
	private static int getInput() {
		int input = scanner.nextInt();
		return input;
	}

	/**
	 * Metod för att exekvera programmet.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Max_Flow run = new Max_Flow();

		System.out.println("Set the size of the graph...");
		System.out.print("Left side: ");
		int leftSize = getInput();
		System.out.print("Right side: ");
		int rightSize = getInput();
		System.out.println(
				"The size of the graph is " + leftSize + " on the left side and " + rightSize + " on the right side.");
		System.out.println();

		run.createGraph(leftSize, rightSize);
		System.out.println("Initialized graph with connections between source and sink");
		run.printGrid(run.getGraph());

		System.out.print(
				"\nNow it's time to add some connections from the nodes on the left side to the nodes on the right side. \nHow many connections would you like to add? ");

		int nmbOfConnections = getInput();
		System.out.println("Alright! You've chosen to add " + nmbOfConnections + " connections. Let's go!");

		for (int i = 1; i <= nmbOfConnections; i++) {
			System.out.println("Connection #" + i + ":");
			System.out.print("Start node (1-" + leftSize +"): ");
			int connectionLeft = getInput();
			System.out.print("End node (" + (leftSize+1) +"-"+ (leftSize +rightSize)+"): ");
			int connectionRight = getInput();
			System.out.println();
			System.out.println("Your input: " + connectionLeft + " " + connectionRight);
			run.addConnection(connectionLeft, connectionRight);
		}

		System.out.println("Graph visualized with connections applied");
		run.printGrid(run.getGraph());

		System.out.println("The max flow in current graph: "
				+ run.fordFulkersonAlgorithm(graph, 0, graph.length - 1));
		System.out.println("New Graph");
		run.printGrid(run.getGraph());
	}
}