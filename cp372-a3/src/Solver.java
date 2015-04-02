import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * Implements a Link State Algorithm using Dijkstra's least-cost path graph
 * searching. Additionally, produces a forwarding table that is based on the
 * given input.
 * 
 * @author Vaughan Hilts, Brandon Smith
 *
 */
public class Solver {

	/**
	 * The main entry point to the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Preparing to compute Link State Algorithm...");
		System.out.println("Please enter the number of routers: ");

		// Fetch the next int
		int routerCount = Integer.parseInt(scanner.nextLine());

		//
		// Get all the rows
		System.out.println("Enter the rows: ");
		List<String> rows = new ArrayList<String>();
		for (int i = 0; i < routerCount; i++) {
			rows.add(scanner.nextLine());
		}
		scanner.close();
		String[] values;
		// Add a sample graph we know about
		List<RouterNode> nodes = new ArrayList<RouterNode>();// { a, b, c, d };
		for (int i = 0; i < routerCount; i++) {
			RouterNode temp = new RouterNode();
			nodes.add(temp);

		}

		for (int i = 0; i < routerCount; i++) {
			String row = rows.get(i);
			values = row.split(" ");
			for (int cur = 0; cur < routerCount; cur++) {
				int cost = Integer.parseInt(values[cur]);
				if (cost != -1) {
					nodes.get(i).addEdge(new GraphEdge(nodes.get(cur), (cost)));
				}
			}
		}

		// Create our structure

		for (RouterNode source : nodes) {

			// Reset all the distances
			resetDistances(nodes);

			// Set the shortest paths from everyone from the source
			setShortestPaths(source, nodes);

			List<ArrayList<RouterNode>> totalPaths = new ArrayList<ArrayList<RouterNode>>();
			Queue<RouterNode> destinations = new LinkedList<RouterNode>();

			// Now, finish by printing by going getting the list
			for (RouterNode destination : nodes) {

				if (destination == source)
					continue;


				// Gets the shortest path to the destination from this source
				List<RouterNode> shortestPath = getShortestPath(destination, routerCount);

				totalPaths.add((ArrayList<RouterNode>) shortestPath);

				destinations.add(destination);
			}

			System.out.println("Forwarding Table for " + source);
			printForwardingTableForList(totalPaths, destinations, source);

		}

	}

	private static void printForwardingTableForList(
			List<ArrayList<RouterNode>> totalPaths,
			Queue<RouterNode> destinations, RouterNode source) {

		System.out.format("%17s%17s%17s\n", new Object[] { "To", "Cost",
				"Next Hop" });

		for (ArrayList<RouterNode> list : totalPaths) {

			
			// Print the top sections
			RouterNode destination = destinations.remove();

			
			int cost = destination.smallestCost;
			System.out.format("%17s", destination);

			if (list.size() > 0) {
				list.add(destination);
				System.out.format("%17s", cost);
				System.out.format("%17s", list.get(1));
			} else {
				System.out.format("%17s", "--");
				System.out.format("%17s", "-1");
			}
			System.out.println();

		}

	}

	private static List<RouterNode> getShortestPath(RouterNode destination, int routerCount) {

		List<RouterNode> order = new ArrayList<RouterNode>();

		RouterNode current = destination.previous;
		
		while (current != null) {
			
			order.add(current);
			if (current.previous != null)
				current = current.previous;
			else
				current = null;
		}

		Collections.reverse(order);
	
		return order;
	}

	private static void resetDistances(List<RouterNode> nodes) {
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
	private static void setShortestPaths(RouterNode source,
			List<RouterNode> nodes) {

		// The source would obviously have the smallest cost being set to 0 on
		// itself
		source.smallestCost = 0;

		// Implementation:
		// http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Using_a_priority_queue

		PriorityQueue<RouterNode> q = new PriorityQueue<RouterNode>();

		// Add only the source
		q.add(source);
		
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
