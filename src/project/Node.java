package project;

import java.util.List;

/**
 * Klass som behandlar tillsättning och hämtning av nya aktuella värden avseende
 * instansvariablerna 'edges' och 'visited', genom get- och set-metoder.
 * 
 * @author Emma Shakespeare och Evelyn Gustavsson
 *
 * @param <V>
 */
public class Node<V> {
	// Instansvariabler.
	public List<Node<V>> edges;
	public boolean visited;

	/**
	 * Returnerar det nya värdet för grafens bågar.
	 * 
	 * @return edges - bågarna.
	 */
	public List<Node<V>> getEdges() {
		return edges;
	}

	/**
	 * Sätter värdet på bågarna.
	 * 
	 * @param edges
	 *            - tar in bågarna som inparameter.
	 */
	public void setEdges(List<Node<V>> edges) {
		this.edges = edges;
	}

	/**
	 * Returnerar true om noden är besökt och på motsvarande sätt false om den
	 * är obesökt.
	 * 
	 * @return visited true eller false
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Sätter värdet till variabeln visited beroende på om den är besökt eller
	 * inte.
	 * 
	 * @param visited
	 *            true eller false som inparameter.
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}