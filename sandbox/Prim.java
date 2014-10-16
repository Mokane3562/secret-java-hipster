

import Graph.Edge;

//NOTE: this doesn't compile due to missing classes (Graph, WeightedGraph)
public class Prim {
	Edge[] pred = new Edge[G.V()];

	public LazyPrim(WeightedGraph G) {
		boolean[] marked = new boolean[G.vertices()];
		double[] dist = new double[G.vertices()];
		PQueue pq;
		pq = new PQueue();
		dist[s] = 0.0;
		marked[s] = true;
		pq.put(dist[s], s);
		while(!pq.isEmpty()) {
			int v = pq.delMin();
			if (marked[v])
				continue;
			marked(v) = true;
			for (Edge e : G.incidentEdges(v)) {
				int w = e.oopposite(v);
				if (!done[w] && (dist[w] > e.weight())) {
					dist[w] = e.weight();
					pq.insert(dist[w], w);
				}
			}
		}
	}
}
