package project;

import java.util.List;

public class Node<V> {
	public List<Node<V>> edges;
	public boolean visited;
	public List<Node<V>> getEdges() {
		return edges;
	}
	public void setEdges(List<Node<V>> edges) {
		this.edges = edges;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
