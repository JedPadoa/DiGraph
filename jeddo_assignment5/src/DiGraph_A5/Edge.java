package DiGraph_A5;

public class Edge {
	private long num;
	private String s;
	private String d;
	private long weight;
	private String e;
	
	public Edge(long id, String source, String destination, long Weight, String edge) {
		num = id;
		s = source;
		d = destination;
		weight = Weight;
		e = edge;
	}
	
	public String getDLabel() {
		return d;
	}

	public String getSLabel() {
		return s;
	}

	public long getIdNum() {
		return num;
	}
	
	public long getWeight() {
		return weight;
	}
}
