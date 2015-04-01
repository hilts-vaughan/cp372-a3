import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Implements a Link State Algorithm using Dijkstra's least-cost path graph
 * searching. Additionally, produces a forwarding table that is based on the
 * given input.
 * 
 * @author Vaughan Hilts
 *
 */
public class Solver {

	/**
	 * The main entry point to the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// System.out.println("Please enter the number of rows: ");
		//
		// Scanner scanner = new Scanner(System.in);
		//
		// // Fetch the next int
		// int rowsCount = scanner.nextInt();
		//
		// // Get all the rows
		// System.out.println("Enter the rows: ");
		// List<String> rows = new ArrayList<String>();
		// for(int i = 0; i < rowsCount; i++) {
		// rows.add(scanner.nextLine());
		// }
		//
		// scanner.close();

		// Add a sample graph we know about
		RouterNode a = new RouterNode();
		RouterNode b = new RouterNode();
		RouterNode c = new RouterNode();
		RouterNode d = new RouterNode();

		// Set it up so starting from A you should be able to traverse to D
		a.addEdge(new GraphEdge(b, 5));
		b.addEdge(new GraphEdge(a, 6));
		b.addEdge(new GraphEdge(c, 10));
		c.addEdge(new GraphEdge(d, 12));
		d.addEdge(new GraphEdge(a, 112));

		// Create our structure
		RouterNode[] nodes = { a, b, c, d };

		for (RouterNode source : nodes) {

			// Reset all the distances
			resetDistances(nodes);

			// Set the shortest paths from everyone from the source
			setShortestPaths(source, nodes);

			// Now, finish by printing by going getting the list
			for(RouterNode destination : nodes) {
				
				System.out.println(source + " to " + destination);
				
				// Gets the shortest path to the destination from this source
				List<RouterNode> shortestPath = getShortestPath(destination);
				
				System.out.println(shortestPath);
			}
			

		}

	}

	private static List<RouterNode> getShortestPath(RouterNode destination) {

		List<RouterNode> order = new ArrayList<RouterNode>();
		
		RouterNode current = destination.previous;
		
		while (current != null) {						
			order.add(current);
			if (current.previous != null)
				current = current.previous;
			else
				current = null;
		}
		
		return order;
	}

	private static void resetDistances(RouterNode[] nodes) {
		for (RouterNode node : nodes) {
			node.smallestCost = Integer.MAX_VALUE;
			node.previous = null;
		}
	}

	/**
	 * Performs a best search and assigns the weights where possible
	 * 
	 * @param source
	 */
	private static void setShortestPaths(RouterNode source, RouterNode[] nodes) {

		// The source would obviously have the smallest cost being set to 0 on
		// itself
		source.smallestCost = 0;

		// Implementation:
		// http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Using_a_priority_queue

		PriorityQueue<RouterNode> q = new PriorityQueue<RouterNode>();

		for (RouterNode node : nodes) {
			if (node != source) {
				node.smallestCost = Integer.MAX_VALUE;
				node.previous = null;
			}

			q.add(node);
		} // initial setup loop

		while (!q.isEmpty()) {

			RouterNode smallestNode = q.poll();

			for (GraphEdge e : smallestNode.getEdges()) {

				RouterNode neighbour = e.getTo();
				int alt = smallestNode.smallestCost + e.getCost();

				if (alt < neighbour.smallestCost) {
					q.remove(neighbour);
					neighbour.smallestCost = alt;
					neighbour.previous = smallestNode;
					q.add(neighbour);
				}
			}
		}

	}

}
