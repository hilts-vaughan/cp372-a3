
/**
 * A generic implementation of a graph edge
 * @author Vaughan Hilts
 *
 */
public class GraphEdge {

	// 
	private final RouterNode _to;
	private final int _cost;
	

	public RouterNode getTo() {
		return _to;
	}

	public int getCost() {
		return _cost;
	}

	public GraphEdge(RouterNode _to, int _cost) {	
		this._to = _to;
		this._cost = _cost;
	}
	
	
	
	
	
	
}
