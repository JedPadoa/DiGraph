package DiGraph_A5;

import java.util.concurrent.ConcurrentHashMap;

public class Vertex {
	
	private long idNum;
	private String label;
	private ConcurrentHashMap<String, Edge> outEdges;
	private ConcurrentHashMap<String, Edge> inEdges;
	private int inDegree;
	private boolean known;
	private long distance;
	private Vertex priorVertex;

	public Vertex(long idNum, String label) {
		
		this.idNum = idNum;
		this.label = label;
		this.inEdges = new ConcurrentHashMap<String, Edge>();
		this.outEdges = new ConcurrentHashMap<String, Edge>();
		this.inDegree = 0;
		this.distance = 0;
		this.priorVertex = null;
	}

	public ConcurrentHashMap<String, Edge> getInEdges() {
		return inEdges;
	}

	public ConcurrentHashMap<String, Edge> getOutEdges() {
		return outEdges;
	}

	public int getInDegree() {
		return inDegree;
	}

	public void addInDegree() {
		inDegree++;
	}

	public void subtractInDegree() {
		inDegree--;
	}

	public String getLabel() {
		return label;
	}
	
	public boolean getKnown() {
		return known;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long x) {
		distance = x;
	}

	public Vertex getPriorVertex() {
		return priorVertex;
	}

	public void addToShortestPathLength(long x) {
		distance += x;
	}

	public void setKnown() {
		known = true;
	}
}
