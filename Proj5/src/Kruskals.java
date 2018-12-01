import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Kruskals {

	private List<String> vertexList;
	private Queue<Edge> edgeQ;

	public void minSpanningTree() {
		List<Edge> result = new ArrayList<>();
		DisjSets disjSets = new DisjSets(vertexList.size());
		int edgeCount = 0;
		int totalWeight = 0;
		while (edgeCount < vertexList.size() - 1) {
			Edge edge = edgeQ.poll();
			int v1Index = vertexList.indexOf(edge.vertex1);
			int v2Index = vertexList.indexOf(edge.vertex2);
			int root1 = disjSets.find(v1Index);
			int root2 = disjSets.find(v2Index);
			if (root1 != root2) {
				result.add(edge);
				disjSets.union(root1, root2);
				edgeCount++;
			}
		}

		System.out.println("Result:");
		for (Edge e : result) {
			System.out.println(e);
			totalWeight += e.weight;
		}
		System.out.println("Total weight: " + totalWeight);

	}

	public void readGraph(String fileName) {
		String line = "";
		edgeQ = new PriorityQueue<>();
		vertexList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			while ((line = br.readLine()) != null) {
				String[] str = line.split(",");
				vertexList.add(str[0]);
				for (int i = 1; i < str.length / 2 + 1; i++) {
					Edge edge = new Edge(str[0], str[2 * i - 1], Integer.parseInt(str[2 * i]));
					edgeQ.add(edge);
				}
			}
		} catch(FileNotFoundException fnfe) {
			System.out.println("graph csv file not found");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class Edge implements Comparable<Edge> {
		private String vertex1;
		private String vertex2;
		private int weight;

		private Edge(String vertex1, String vertex2, int weight) {
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge edge) {
			return this.weight - edge.weight;
		}

		@Override
		public String toString() {
			return "Edge(" + vertex1 + ", " + vertex2 + ") weight=" + weight;
		}

	}

	public static void main(String[] args) {
		Kruskals kruskals = new Kruskals();
		kruskals.readGraph("src/project5_data.csv");
		kruskals.minSpanningTree();
	}
}
