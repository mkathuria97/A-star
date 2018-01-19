/**
 * Created by mayankkathuria on 2/27/17.
 */
public class JsonEdge {
    private String node1;
    private String node2;
    private int weight;

    public JsonEdge(String node1, String node2, int weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    public String getNode1() {
        return node1;
    }

    public String getNode2() {
        return node2;
    }

    public int getWeight() {
        return weight;
    }
}
