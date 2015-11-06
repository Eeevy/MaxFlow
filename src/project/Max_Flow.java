package project;

import java.util.ArrayList;
import java.util.LinkedList;

public class Max_Flow {
	private LinkedList<Integer> queue = new LinkedList<Integer>();
	private int[][] graf = new int[6][6];
	private int[] path = new int[6];
	private boolean[] visited = new boolean[6];

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
					if (v < 5 && u > 0) {
						System.out.print("Mellan nod:" + u + "och " + v);
						System.out.println();
					}
				}

				v = path[v];

			}
			maxFlow += flowCapacity;

		}
		return maxFlow;

	}

	public static void main(String[] args) {
		int[][] graf = { { 0, 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 1 },
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
		System.out.println("Maxflow för din graf är!:"
				+ run.fordFulkersonAlgorithm(graf, 0, 5));

	}

}
