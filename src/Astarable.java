import java.util.Set;

public interface Astarable {
    Set<AstarableNode> getAllNodes();
    double getHeuristicCostEstimate(AstarableNode node, AstarableNode end);
    Set<AstarableNode> getCurrentNeighbors(AstarableNode current);
    int getDistCurrentNeighbor(AstarableNode current, AstarableNode neighbor);
}
