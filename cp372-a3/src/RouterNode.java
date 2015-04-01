import java.util.ArrayList;
import java.util.List;


/**
 * Represents a single node for a router, keeps a list of the edges connected to it
 * when needed
 * @author Vaughan Hilts
 *
 */
public class RouterNode implements Comparable<RouterNode> {

	// This is mostly used for keeping track of RouterNode's internally
	private static char _internalNameCounter = 'A';
	private char _name;
	
	private List<GraphEdge> _edges = new ArrayList<GraphEdge>();
	
	public int smallestCost = Integer.MAX_VALUE;
	public RouterNode previous = null;

	public RouterNode() {
		
		// Assign the name
		_name = _internalNameCounter;
		
		// Increment the internal counter
		_internalNameCounter++;
	}
	
	
	
	
	public List<GraphEdge> getEdges() {
		return _edges;
	}
	
	public void addEdge(GraphEdge edge) {
		_edges.add(edge);
	}


	@Override
	public int compareTo(RouterNode o) {
		// TODO: Implement if it's actually needed
		return Integer.compare(this.smallestCost, o.smallestCost);
	}
	
	@Override
	public String toString() {
		return Character.toString(_name);
	}

	
}
