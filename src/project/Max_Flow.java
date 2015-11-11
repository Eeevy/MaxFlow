package project;

import java.util.LinkedList;

/*
 * TODO 1: Initiera en graf med storleken som användaren matar in. Den ska heta createGraph(int leftSize, int rightSize).
 */

/*
 * TODO 2: Se till att det finns en koppling mellan source-noder och sink-noder. Skapa metoden addConnection(int left, int right).
 */

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
	private LinkedList<Integer> queue = new LinkedList<Integer>(); // Kö.
	// private int[][] graf = new int[6][6];
	private int[] path = new int[6]; // Väg.
	private boolean[] visited = new boolean[6]; // Boolean som beskriver om en
												// nod är besökt eller inte.
	private int[][] graph;//Tilldelas storlek och värden av användarinput

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
	 * Skapar en graf med storleken som användaren matar in. 
	 * Ser till att det finns en koppling mellan alla bgar 
	 * från och till source-noden och sink-noden
	 * 
	 * @param leftSize
	 * @param rightSize
	 */
	public void createGraph(int leftSize, int rightSize) {
		this.graph = new int[leftSize][rightSize];
		for(int i = 0; i < graph.length;i++){
			for(int j = 0; j < graph.length;j++){
				
			}
		}
		

	}
	/**
	 * Metoden skriver ut en visualisering av en graf
	 */
	public void printGrid(int[][] graph)
	{
	   for(int i = 0; i < graph.length; i++)
	   {
	      for(int j = 0; j < graph[i].length; j++)
	      {
	         System.out.printf("%5d ", graph[i][j]);
	      }
	      System.out.println();
	   }
	}

	/**
	 * Metod som ordnar så att det finns en koppling mellan source-noden och
	 * sink-noden.
	 * 
	 * @param left
	 * @param right
	 */
	public void addConnection(int left, int right) {
		
	}

	/**
	 * Metod för att testköra programmet. Initierar grafen med 1:or och 0:or.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int[][] graf = { { 0, 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0 } };
		

		// int[][] graf = {
		// {0,16,13,0,0,0},
		// {0,0,10,12,0,0},
		// {0,4,0,0,14,0},
		// {0,0,9,0,0,20},
		// {0,0,0,7,0,4},
		// {0,0,0,0,0,0}
		// };

		Max_Flow run = new Max_Flow();
		System.out.println("The number of edges in the graph is: " + run.fordFulkersonAlgorithm(graf, 0, 5));
		run.printGrid(graf);
	}
}