package project;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Klassen hanterar bipartit matchning. En nod anses vara matchad om den ligger
 * i en båge. I varje graf finns en graf finns en source-nod och en sink-nod och
 * dessa utgör en del av flödesschemat. Det finns olika vägar mellan en
 * source-nod och sink-nod, genom andra noder. Vid exekvering av programmet
 * skrivs koppling mellan de mellanliggande noderna ut. Det är underförstått att
 * vägen alltid börjar från en source-nod och avslutas vid en sink-nod. Därav
 * sker ingen utskrift gällande de kopplingarna. Applikationen beräknar även det
 * maximala flödet för den aktuella grafen.
 * 
 * @authors Emma Shakespeare och Evelyn Gustavsson
 *
 */
public class Max_Flow {
	// Instansvariabler.
	static Scanner scanner = new Scanner(System.in);
	private static int[][] graph;
	private LinkedList<Integer> queue = new LinkedList<Integer>(); // Kö.
	private int[] path = new int[6]; // Väg.
	private boolean[] visited = new boolean[6]; // Boolean som beskriver om en
												// nod är besökt eller inte.

	// private int[] path;
	// private boolean visited[];
	// private boolean[][] boolGraph;
	// int matchLeft[];
	// int matchRight[];
	// int m, n;

	/**
	 * Metod som utgår från principen Bredden-Först.
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
	 * Metoden använder Ford Fulkersons algoritm för att hitta det maximala
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
						System.out.print("Koppling mellan nod: " + u + " och "
								+ v);
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
	 * @param rightSize
	 */
	public void createGraph(int leftSize, int rightSize) {
		this.graph = new int[leftSize][rightSize];

		// Sätter ut koppling mellan source noden och övrigaa noder på vänster
		// sida
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				if (i == 0 && j > 0 || i == graph.length - 1 && j < graph.length /2) {
					graph[i][j] = 1;
				} else {
					graph[i][j] = 0;
				}
			}
		}
	}

	// När j (bågarna) är större än noll men mindre än hälften av längden
	// (delat med två).
	// När i är lika stor som längden minus 1.
	// När i är det största elementet och j är den andra halvan...

	/**
	 * Returnerar en graf.
	 * 
	 * @return graph
	 */
	public int[][] getGraph() {
		return this.graph;
	}

	// public int maximumMatching() {
	// Arrays.fill(matchLeft, -1);
	// Arrays.fill(matchRight, -1);
	//
	// int count = 0;
	// for (int i = 0; i < m; i++) {
	// Arrays.fill(visited, false);
	// if (addConnection(i)) count++;
	// }
	// return count;
	// }

	/**
	 * Metod som ordnar så att det finns en indirekt koppling mellan
	 * source-noden och sink-noden genom andra noder.
	 * 
	 * @param left
	 * @param right
	 */
	public void addConnection(int left, int right) {

		graph[left][right] = 1;
		// public boolean addConnection(int u) {
		// for (int v = 0; v < n; v++) {
		// if (!boolGraph[u][v] || visited[v]) continue;
		// visited[v] = true;
		// if (matchRight[v] == -1 || addConnection(matchRight[v])) {
		// matchLeft[u] = v;
		// System.out.println("matchLeft: " + matchLeft);
		// matchRight[v] = u;
		// System.out.println("matchRight: " + matchRight);
		// return true;
		// }
		// }
		// return false;
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
	 * Metod för att testköra programmet.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Max_Flow run = new Max_Flow();

		System.out.println("Set the size of the graph...");
		System.out.println("Left side: ");
		int leftSize = getInput();
		System.out.println("Right side: ");
		int rightSize = getInput();
		System.out.println("The size of the graph is " + leftSize
				+ " on the left side and " + rightSize + " on the right side.");
		System.out.println();

		run.createGraph(leftSize, rightSize);
		run.printGrid(run.getGraph());

		// run.maximumMatching();

		// TO DO: En while loop som ber användaren att mata in connections.
		// Bortsett från kopplingar som går från sink-sink och source-source

		// run.addConnection(0,2);
		//
		//
		// System.out.println();
		// run.printGrid(run.getGraph());

		// run.fordFulkersonAlgorithm(graph, s, t);

		// System.out.println("The number of edges in the graph is: " +
		// run.fordFulkersonAlgorithm(graf, 0, 5));
	}
}