

import Graph.Edge;

//NOTE: this doesn't compile due to missing classes (Graph, WeightedGraph, MinPQplus)
public class LazyPrim {
	Edge[] pred = new Edge[G.V()];//pred[v] is edge attaching v to MST

	public LazyPrim(WeightedGraph G) {
		boolean[] marked = new boolean[G.V()];//marks vertices in MST
		double[] dist = new double[G.V()];//distance to MST
		MinPQplus<Double, Integer> pq;
		pq = new MinPQplus<Double, Integer>();//key-value PQ
		dist[s] = 0.0;
		marked[s] = true;
		pq.put(dist[s], s);
		while(!pq.isEmpty()) {
			int v = pq.delMin();
			if (marked[v])
				continue;//get next vertex
			marked(v) = true;
			for (Edge e : G.adj(v)) {
				int w = e.other(v);
				if (!done[w] && (dist[w] > e.weight())) {
					dist[w] = e.weight();//add to PQ any vertices brought closer to S by v
					pq.insert(dist[w], w);
				}
			}
		}
	}
}
