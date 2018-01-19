import java.util.*;

public class Main {
    public static void main(String[] args){

        System.out.println("Choose a choice (1 or 2)");
        System.out.println("1. Grid");
        System.out.println("2. Graph");

        Scanner console = new Scanner(System.in);
        //stores the choice provided to the user
        int choice = console.nextInt();

        ArrayList<AstarableNode> totalPath;

        if(choice == 1){
            Grid gridObject = new Grid();
            Grid grid = gridObject.JsonGetter(args);

            //terminates the program if the URL specified by the user is invalid or does not exist
            if (grid == null) {
                System.out.print("The given URL is invalid or does not exist.");
                System.exit(0);
            }
            totalPath = getGridDescAndComputePath(grid);
        } else if(choice == 2){
            Graph graphObject = new Graph();
            Graph graph = graphObject.JsonGetter(args);

            //terminates the program if the URL specified by the user is invalid or does not exist
            if (graph == null) {
                System.out.print("The given URL is invalid or does not exist.");
                System.exit(0);
            }
            totalPath = getGraphDescAndComputePath(graph/*, console*/);

        } else{
            totalPath = null;
            System.out.println("Invalid choice");
        }

        if(totalPath != null){
            for(AstarableNode node: totalPath ){
                System.out.println(node);
            }
        }else{
            System.out.println("There is no path available.");
            System.exit(0);
        }
    }

    /**
     * @param grid
     * @return list of nodes representing the best path from the start to the end position or node
     */
    public static ArrayList<AstarableNode> getGridDescAndComputePath(Grid grid){
        //stores the start position or node
        Position start = grid.getStart();

        //stores the end position or node
        Position end = grid.getEnd();

        //stores list of nodes representing the best path from the start to the end position or node
        ArrayList<AstarableNode> totalPath = AStar(start, end, grid);
        return totalPath;
    }

    /**
     * @param graph
    // * @param console
     * @return list of nodes representing the best path from the start to the end position or node
     */

    public static ArrayList<AstarableNode> getGraphDescAndComputePath(Graph graph/*, Scanner console*/){
        Scanner console = new Scanner(System.in);

        System.out.println("Enter the start city");
        String startCity = console.nextLine();
        JsonCity start = graph.createJsonCity(startCity);

        System.out.println("Enter the end city");
        String endCity = console.nextLine();
        JsonCity end = graph.createJsonCity(endCity);

        if(start == null || end == null){
            System.out.println("Invalid place");
            System.exit(0);
        }else{
            //stores list of nodes representing the best path from the start to the end position or node
            ArrayList<AstarableNode> totalPath;
            totalPath = AStar(start, end, graph);
            return totalPath;
        }

        return null;
    }

    /**
     *
     * @param start start node
     * @param end end or goal node
     * @param structure represent any data structure that is A*-able
     * @return a list of nodes representing the best path from the start to the end node or position
     */
    public static ArrayList<AstarableNode> AStar(AstarableNode start, AstarableNode end, Astarable structure){
        //stores set of nodes already evaluated
        Set<AstarableNode> closedSet = new HashSet<AstarableNode>();

        //stores set of currently discovered nodes that are not evaluated yet
        Set<AstarableNode> openSet = new HashSet<AstarableNode>();
        openSet.add(start);

        //stores each node along with the node from which it can most efficiently be reached
        //if a node can be reached from many nodes, cameFrom will eventually contain the
        //most efficient previous step.
        Map<AstarableNode, AstarableNode> cameFrom = new HashMap<AstarableNode, AstarableNode>();

        //stores each node and their cost of getting from the start node
        Map<AstarableNode, Double> gScore = setNodeAndCost(structure.getAllNodes());
        gScore.put(start, 0.0);

        //stores each node and the total cost of getting from the start node to the goal
        //by passing by that node. That value is partly known, partly heuristic.
        Map<AstarableNode, Double> fScore = setNodeAndCost(structure.getAllNodes());
        double heuristicCostEstimate = structure.getHeuristicCostEstimate(start, end);
        fScore.put(start, heuristicCostEstimate);

        while(!openSet.isEmpty()){
            //stores the position of the current node
            AstarableNode current = getNodeWithMinCost(openSet, fScore);
            if(current.equals(end)){
                return getReconstructPath(cameFrom, current);
            }
            openSet.remove(current);
            closedSet.add(current);

            //stores a set of neighbors of the current node
            Set<AstarableNode> currentNeighbors = structure.getCurrentNeighbors(current);

            for(AstarableNode neighbor : currentNeighbors){
                //ignore the neighbor which is already evaluated
                if(closedSet.contains(neighbor)){
                    continue;
                }

                //stores the distance from start to a neighbor
                double tentativeGScore = gScore.get(current) + structure.getDistCurrentNeighbor(current, neighbor);
                if(!openSet.contains(neighbor)){
                    openSet.add(neighbor);
                } else if(tentativeGScore >= gScore.get(neighbor)){
                    continue;
                }

                //This path is the best until now. Record it!
                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentativeGScore);
                fScore.put(neighbor, gScore.get(neighbor) + structure.getHeuristicCostEstimate(neighbor, end));
            }
        }

        return null;
     }

    /**
     * @return map containing nodes of any structure that is A*-able and their initial cost
     */
    public static Map<AstarableNode, Double> setNodeAndCost(Set<AstarableNode> allNodes){
        Map<AstarableNode, Double> eachNodeWithCost = new HashMap<AstarableNode, Double>();

        for(AstarableNode node: allNodes){
            eachNodeWithCost.put(node, Double.MAX_VALUE);
        }

        return eachNodeWithCost;
    }

    /**
     * @param openSet set of currently discovered nodes of any structure that is A*-able that are not evaluated yet
     * @param fScore map containing each node of any structure that is A*-able along with the total cost of getting from
     *               the start node to the goal by passing by that node
     * @return node of any structure that is A*-able with the minimum cost
     */
    public static AstarableNode getNodeWithMinCost(Set<AstarableNode> openSet, Map<AstarableNode, Double> fScore){
        AstarableNode nodeWithMinCost = null;
        double minCost = Double.MAX_VALUE;

        for(AstarableNode node: openSet){
            double cost = fScore.get(node);
            if(cost < minCost){
                nodeWithMinCost = node;
                minCost = cost;
            }
        }

        return nodeWithMinCost;
    }

    /**
     * @param cameFrom a map containing each node of any structure that is A*-able along with the node from which it can
     *                 most efficiently reached
     * @param current position of the current node of any structure that is A*-able
     * @return a list of nodes representing the best path from start to the end position
     */
    public static ArrayList<AstarableNode> getReconstructPath(Map<AstarableNode, AstarableNode> cameFrom, AstarableNode current){
        ArrayList<AstarableNode> totalPath = new ArrayList<AstarableNode>();
        totalPath.add(current);

        while(cameFrom.containsKey(current)){
            current = cameFrom.get(current);
            totalPath.add(current);
        }

        return totalPath;
    }
}
