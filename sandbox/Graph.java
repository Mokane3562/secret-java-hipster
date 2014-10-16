

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Graph - An edge list implementation of the graph ADT
 * 
 * @author Michael Singleton
 */
public class Graph {
	/**
	 * Edge - an edge linking two vertex objects
	 */
	public static class Edge {
		protected Object o;
		protected Vertex v1;
		protected Vertex v2;

		/**
		 * Creates a new Edge
		 * 
		 * @param o the data held by the edge
		 * @param v1 the start vertex
		 * @param v2 the end vertex
		 */
		public Edge(Object o, Vertex v1, Vertex v2) {
			this.o = o;
			this.v1 = v1;
			this.v2 = v2;
		}

		public Edge(Vertex v1, Vertex v2) {
			o = null;
			this.v1 = v1;
			this.v2 = v2;
		}

		/**
		 * Returns the edge in the reverse direction
		 * 
		 * @return the edge in the reverse direction
		 */
		public Edge getReverse() { //optional. used for my own purpose
			return new Edge(o, v2, v1);
		}

		public Object getWeight() {
			return o;
		}

		/**
		 * Tests whether the given vertex is in the edge
		 * 
		 * @param v
		 *            the vertex to to be checked for
		 * @return true if v is in the edge
		 */
		public boolean hasVertex(Vertex v) { //
			if (v == v1 || v == v2)
				return true;
			else
				return false;
		}

		/**
		 * Returns a string representation of the edge object
		 * 
		 * @return a string representation of the edge object
		 */
		@Override
		public String toString() {
			return v1 + "-" + o + "-" + v2;
		}
	}

	/**
	 * Vertex - a vertex for a graph
	 */
	public static class Vertex {
		protected Object o;

		/**
		 * Creates a new Vertex
		 * 
		 * @param o
		 *            the data held by the vertex
		 */
		public Vertex(Object o) {
			this.o = o;
		}

		/**
		 * Returns a string representation of the vertex object
		 * 
		 * @return a string representation of the vertex object
		 */
		@Override
		public String toString() {
			return "(" + o + ")";
		}
	}

	public static void main(String[] args) {

		Vertex bobs = new Vertex("Bob's house");
		Vertex bk = new Vertex("Burger King");
		Vertex mall = new Vertex("Village Mall");
		Vertex[] vertices = { bobs, bk, mall };
		Edge bobsAndBk = new Edge(3, bobs, bk);
		Edge bobsAndMall = new Edge(10, bobs, mall);
		Edge bkAndMall = new Edge(15, bk, mall);
		Edge[] edges = { bobsAndBk, bobsAndMall, bkAndMall };
		Graph g = new Graph(vertices, edges);

		Vertex[] verticesTest = g.vertices();
		Edge[] edgesTest = g.edges();

		Edge[] incidentEdgesTest = g.incidentEdges(bk);

		Vertex oppositeTest = g.opposite(bobs, bobsAndMall);

		Vertex[] endVerticesTest = g.endVertices(bkAndMall);

		boolean areAdjacentTest = g.areAdjacent(bobs, bk);
		System.out.println("This is the shape of the graph. Edge weights are the time (in minutes) it takes Bob to walk from one vertex to another :\n\nBob's house -- 3 -- BurgerKing\n\t|\t    /\n\t10\t15\n\t|   /\nVillage Mall\n\n");
		System.out.printf("Actual:\t\t%s%nExpected:\t[(Bob's house), (Burger King), (Village Mall)]%n", Arrays.toString(verticesTest));
		System.out.printf("Actual:\t\t%s%nExpected:\t[(Bob's house)-3-(Burger King), (Bob's house)-10-(Village Mall), (Burger King)-15-(Village Mall)]%n", Arrays.toString(edgesTest));
		System.out.printf("Actual:\t\t%s%nExpected:\t[(Bob's house)-3-(Burger King), (Burger King)-15-(Village Mall)]%n", Arrays.toString(incidentEdgesTest));
		System.out.printf("Actual:\t\t%s%nExpected:\t(Village Mall)%n", oppositeTest);
		System.out.printf("Actual:\t\t%s%nExpected:\t[(Burger King), (Village Mall)]%n", Arrays.toString(endVerticesTest));
		System.out.printf("Actual:\t\t%b%nExpected:\ttrue%n", areAdjacentTest);
	}

	private ArrayList<Vertex> vs;

	private ArrayList<Edge> es;

	/**
	 * Creates a new Graph
	 * 
	 * @param vertexSet
	 *            a collection of vertices, duplicates will be automatically
	 * @param edgeSet
	 *            a collection of edges, duplicates and reverse directions will
	 *            be trimmed automatically
	 */
	public Graph(Vertex[] vertexSet, Edge[] edgeSet) {
		vs = new ArrayList<Vertex>();
		es = new ArrayList<Edge>();
		for (int i = 0; i < vertexSet.length; i++) {
			if (!vs.contains(vertexSet[i])) {
				vs.add(vertexSet[i]);
			}
		}
		for (int i = 0; i < edgeSet.length; i++) {
			if (!es.contains(edgeSet[i]) && !es.contains(edgeSet[i].getReverse())) {
				es.add(edgeSet[i]);
			}
		}
	}

	/**
	 * Tests whether two vertices are adjacent
	 * 
	 * @param v
	 *            the first vertex
	 * @param w
	 *            the second vertex
	 * @return true if v and w are adjacent
	 */
	public boolean areAdjacent(Vertex v, Vertex w) {
		boolean result = false;
		testForAdj: { // used to break for early return. see java block labeling
			for (Edge e : es) { //for every edge e
				if (e.hasVertex(v) && !e.hasVertex(w)) { //containing v but not w
					for (Edge f : es) { //for every edge f
						if (f.hasVertex(w) && !f.hasVertex(v)) { //containing w but not v
							if (opposite(v, e) == opposite(w, f)) { //if e and f share a common vertex that isn't v or w
								result = true;
								break testForAdj; //ends everything under testForAdj and skips to return
							}
						}
					}
				}
			}
		}
		return result;
	}

	public String BFSearch(Vertex v) {
		String list = "";
		int z = 0;
		Vertex u = new Vertex(null);
		ArrayList discEdges = new ArrayList();
		ArrayList crossEdges = new ArrayList();
		ArrayList discVerts = new ArrayList();
		ArrayList<ArrayList<Vertex>> l = new ArrayList();
		ArrayList<Vertex> l1 = new ArrayList();
		l1.add(v);
		l.add(l1);
		discVerts.add(v);
		while(!l.get(z).isEmpty()) {
			int y = 0;
			ArrayList<Vertex> l2 = new ArrayList();
			l.add(l2);
			for (int i = 0; i < l.get(z).size(); i++) {
				for (int j = 0; j < incidentEdges(l.get(z).get(i)).length; j++) {
					//ArrayList<Edge> ie = new ArrayList();
					List<Edge> ie = new ArrayList<Edge>(Arrays.asList(incidentEdges(l.get(z).get(i))));
					if (!discEdges.contains(ie.get(j))) {
						u = opposite(l.get(z).get(i), ie.get(j));
						if (!discVerts.contains(u)) {
							discEdges.add(ie.get(j));
							l2.add(u);
							discVerts.add(u);
							y++;
						} else
							crossEdges.add(ie.get(j));
					}
				}
			}
			z++;
		}
		while(!l.isEmpty()) {
			list = list + l.remove(0).toString();
		}
		return list;
	}

	/**
	 * Returns an array storing all the edges of the graph
	 * 
	 * @return the edges of the graph
	 */
	public Edge[] edges() {
		return es.toArray(new Edge[] {});
	}

	/**
	 * Returns an array storing the two end vertices of a given edge
	 * 
	 * @param e
	 *            the edge whos end vertices are to be returned
	 * @return the end vertices of edge e
	 */
	public Vertex[] endVertices(Edge e) {
		Vertex[] array = { e.v1, e.v2 };
		return array;
	}

	/**
	 * Returns an array storing all the edges incident on a given vertex
	 * 
	 * @param v
	 *            the vertex whos incident edges (if any) are to be returned
	 * @return the edges incident on vertex v
	 */
	public Edge[] incidentEdges(Vertex v) {
		ArrayList<Edge> array = new ArrayList<Edge>();
		for (Edge e : es) {
			if (e.hasVertex(v))
				array.add(e);
		}
		return array.toArray(new Edge[] {});
	}

	/**
	 * Returns the vertex opposite to a given vertex on a given edge
	 * 
	 * @param v
	 *            a vertex on edge e
	 * @param e
	 *            the edge containing vertex v and the vertex opposite to v
	 * @return the vertex opposite to vertex v on edge e
	 */
	public Vertex opposite(Vertex v, Edge e) {
		return (v == e.v1) ? e.v2 : e.v1; // shorthand for simple if/else situations. see ternary operator
	}

	public void PrimJarnik(Graph g) {
		Vertex v = vs.get(0);
		PQueue.Entry[] dist = new PQueue.Entry[vs.size()];
		dist[0] = new PQueue.Entry(0, v);
		for (int i = 1; i < vs.size(); i++) {
			dist[i] = new PQueue.Entry(Integer.MAX_VALUE, vs.get(i));
		}
		// new treeorsomething
		PQueue pq = new PQueue();
		for (int i = 0; i < vs.size(); i++) {
			pq.insert(dist[i].getKey(), new Edge(vs.get(i), null));
		}
		while(pq.size() != 0) {

		}
	}

	/**
	 * Returns a string representation of the graph
	 * 
	 * @return a string representation of the graph
	 */
	@Override
	public String toString() {
		return vs + " | " + es;
	}

	/**
	 * Returns an array storing all the vertices of the graph
	 * 
	 * @return the vertices of the graph
	 */
	public Vertex[] vertices() {
		return vs.toArray(new Vertex[] {});
	}
}