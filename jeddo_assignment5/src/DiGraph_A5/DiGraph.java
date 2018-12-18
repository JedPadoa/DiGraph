package DiGraph_A5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class DiGraph implements DiGraphInterface{
	
	long numEdges;
	long numNodes;

	HashSet<Long> vertexIDs = new HashSet<Long>();
	HashSet<Long> edgeIDs = new HashSet<Long>();

	HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();

	public DiGraph() { // default constructor
		// explicitly include this
		// we need to have the default constructor
		// if you then write others, this one will still be there
	}

	@Override
	public boolean addNode(long num, String label) {
		
		if (num < 0) {
			
			return false;
			
		} else if (label == null) {
			return false;
			
		} else if (vertexIDs != null && vertexIDs.contains(num)) {
			// if idNum is not unique
			return false;
			
		} else if (vertices != null && vertices.containsKey(label)) {
			// if label is not unique
			return false;
			
		} else {
			numNodes++;
			vertexIDs.add(num);
			vertices.put(label, new Vertex(num, label));
			return true;
		}
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		
		Edge toAdd = new Edge(idNum, sLabel, dLabel, weight, eLabel);
		
		if (idNum < 0 || sLabel == null || dLabel == null) {
			return false;
			
		} else if (edgeIDs != null && edgeIDs.contains(idNum)) {
			return false;
			
		} else if (vertices.keySet() != null
				&& (!vertices.keySet().contains(sLabel) || !vertices.keySet().contains(dLabel))) {
			return false;
			
		} else if (vertices.get(sLabel).getOutEdges() != null
				&& vertices.get(sLabel).getOutEdges().containsKey(dLabel)) {
			// if tbere is already an edge between the two nodes
			return false;
			
		} else {
			
			numEdges++;
			vertices.get(sLabel).getOutEdges().put(dLabel, toAdd);
			vertices.get(dLabel).addInDegree();
			edgeIDs.add(idNum);
			return true;
			
		}

	}

	@Override
	public boolean delNode(String label) {
		if (vertices.containsKey(label) != true) {
			
			return false;
			
		} else {
			
			vertices.remove(label);
			
			numNodes--;
			
			return true;
		}
	}

	@Override
	public boolean delEdge(String s, String d) {
		
		if (s == null || d == null) {
			
			return false;
			
		} else if ((vertices.keySet().contains(s) && vertices.keySet().contains(d))
				&& (vertices.get(s).getOutEdges().containsKey(d))) {
			
			numEdges--;
			
			edgeIDs.remove(vertices.get(s).getOutEdges().get(d).getIdNum());
			
			vertices.get(s).getOutEdges().remove(d);
			
			vertices.get(d).subtractInDegree();
			
			return true;
		}
		
		return false;
	}

	@Override
	public long numNodes() {
		return numNodes;
	}

	@Override
	public long numEdges() {
		return numEdges;
	}

	@Override
	public String[] sort() {

		Queue<String> q = new LinkedList<String>();

		ArrayList<String> hashV = new ArrayList<String>();
		// TopoSort but as a hashset^
		// Edge[] eList = new Edge[edgeIDs.size()];
		for (Vertex v : vertices.values()) {
			
			if (v.getInDegree() == 0) {
				
				q.add(v.getLabel());
				
			}
			
		}

		while (q.size() > 0) {
			
			String temp = q.remove();
			
			hashV.add(temp);
			
			Collection<Edge> edge = vertices.get(temp).getOutEdges().values();
			for (Edge e : edge) {
				// vertices.get(e.getDLabel()).subtractInDegree();
				delEdge(e.getSLabel(), e.getDLabel());
				if (vertices.get(e.getDLabel()).getInDegree() == 0) {
					q.add(e.getDLabel());
				}

			}
		}
		
		if (hashV.size() != vertices.size()) {
			return null;
		}

		return hashV.toArray(new String[vertices.size()]);

	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		
		int size = 0;

		ShortestPathInfo[] shortest = new ShortestPathInfo[vertices.size()];

		MinBinHeap min = new MinBinHeap();

		min.insert(new EntryPair(label, 0));

		while (min.size() != 0) {

			Vertex n = vertices.get(min.getMin().getValue());
			long d = vertices.get(min.getMin().getValue()).getDistance();

			min.delMin();
			
			if (n.getKnown() == false) {
				n.setKnown();

				Collection<Edge> outEdges = n.getOutEdges().values();
				for (Edge e : outEdges) {
					Vertex a = vertices.get(e.getDLabel());
					if (d == 0 || a.getDistance() == 0 || n.getDistance() + e.getWeight() <= a.getDistance())
						a.setDistance(n.getDistance() + e.getWeight());

					if (a.getDistance() >= d + e.getWeight()) {
						a.setDistance(d + e.getWeight());
						// System.out.println(a.getLabel() + " - " + d + ", " +
						// e.getWeight());
						min.insert(new EntryPair(a.getLabel(), (int) (d + e.getWeight())));
					}
				}
				// System.out.println(n.getLabel() + ", " + n.getDistance());
				shortest[size] = new ShortestPathInfo(n.getLabel(), n.getDistance());
				size++;
			}
		}

		if (size != vertices.size()) {
			for (Vertex v : vertices.values()) {
				if (v.getKnown() == false) {

					shortest[size] = new ShortestPathInfo(v.getLabel(), -1);
					size++;
				}
			}
		}

		return shortest;
	}

}
