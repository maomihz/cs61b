import java.util.List;
import java.util.LinkedList;

/*Kruskals.java*/

/**
 * Implementation of Kruskal's algorithm for MSTs
 * 
 * @author
 */

public class Kruskals {

	/**
	 * Returns a new EdgeWeightedGraph that is an MST of the input graph
	 * 
	 * @param g
	 *            the input graph
	 * @return an MST of the original graph
	 */
	public static EdgeWeightedGraph mst(EdgeWeightedGraph g) {
		
		// Create a new graph
		EdgeWeightedGraph myGraph = new EdgeWeightedGraph(g.E());
		
		// a list of all edges
		List<Edge> edgeList = new LinkedList<Edge>();
		
		for (Edge e : g.edges()) {
			edgeList.add(e);
		}
		
		//bubbleSort the edges
		for (int i = 0; i < edgeList.size(); i++) {
			for (int j = 0; j < edgeList.size() - i - 1; j++) {
				if (edgeList.get(j).compareTo(edgeList.get(j + 1)) > 0) { //greater
					Edge temp = edgeList.get(j + 1);
					edgeList.set(j + 1, edgeList.get(j));
					edgeList.set(j, temp);
				}
			}
		}
		
		UF set = new UF(myGraph.V());
		
		for (Edge e : edgeList) {
			if (set.find(e.either()) != set.find(e.other(e.either()))) {
				myGraph.addEdge(e);
			}
			set.union(e.either(), e.other(e.either()));
		}
		
		
		return myGraph;
	}
	
	public static void main(String[] args) {
		UF set = new UF(5);
		set.union(1, 0);
		System.out.println(set.find(0));
	}

}
