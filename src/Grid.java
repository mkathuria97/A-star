import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class Grid implements Astarable{
    //stores the dimension of the grid
    //testingdfg
    private int dimension;

    //stores the start position
    private Position start;

    //stores the end position
    private Position end;

    //stores a list of obstacles in the grid
    private Position[] obstacles;

    /**
     * @return the dimension of the grid
     */
    public int getDimension(){
        return dimension;
    }

    /**
     * @return the start position
     */
    public Position getStart(){
        return start;
    }

    /**
     * @return the end position
     */
    public Position getEnd(){
        return end;
    }

    /**
     * @return obstacles in the grid
     */
    public Position[] getObstacles(){
        return obstacles;
    }

    /**
     *
     * @return all the nodes of a grid
     */
    @Override
    public Set<AstarableNode> getAllNodes(){
        Set<AstarableNode> allNodesInGrid = new HashSet<AstarableNode>();

        for(int xCoordinate = 0; xCoordinate < dimension; xCoordinate++) {
            for (int yCoordinate = 0; yCoordinate < dimension; yCoordinate++) {
                AstarableNode node = new Position(xCoordinate, yCoordinate);
                allNodesInGrid.add(node);
            }
        }

        return allNodesInGrid;
    }

    /**
     * @param node or a position in the grid
     * @param end goal or the end position
     * @return an estimate of the minimum cost from the node to the goal
     */
    @Override
    public double getHeuristicCostEstimate(AstarableNode node, AstarableNode end){
        double dx = Math.abs(((Position)node).getX() - ((Position)end).getX());
        double dy = Math.abs(((Position)node).getY() - ((Position)end).getY());
        return dx + dy;
    }

    /**
     *
     * @param current current position in the grid
     * @param neighbor position of the neighbor
     * @return distance between the current and neighbor position
     */
    @Override
    public int getDistCurrentNeighbor(AstarableNode current, AstarableNode neighbor){
        return 1;
    }

    /**
     *
     * @param current position of the current node in the grid
     * @return set containing neighbors of the current node
     */
    public Set<AstarableNode> getCurrentNeighbors(AstarableNode current){
        Set<AstarableNode> allNodesInGrid = getAllNodes();
        Set<AstarableNode> obstaclesInGrid = getSetOfObstacles(obstacles);
        Set<AstarableNode> currentNeighbors = new HashSet<AstarableNode>();
        AstarableNode neighborNorth = new Position(((Position)current).getX() - 1, ((Position)current).getY());
        AstarableNode neighborEast = new Position(((Position)current).getX(), ((Position)current).getY() + 1);
        AstarableNode neighborWest = new Position(((Position)current).getX(), ((Position)current).getY() - 1);
        AstarableNode neighborSouth = new Position(((Position)current).getX() + 1, ((Position)current).getY());
        if(isNeighborValid(allNodesInGrid, neighborNorth, obstaclesInGrid)){
            currentNeighbors.add(neighborNorth);
        }
        if(isNeighborValid(allNodesInGrid, neighborEast, obstaclesInGrid)){
            currentNeighbors.add(neighborEast);
        }
        if(isNeighborValid(allNodesInGrid, neighborWest, obstaclesInGrid)){
            currentNeighbors.add(neighborWest);
        }
        if(isNeighborValid(allNodesInGrid, neighborSouth, obstaclesInGrid)){
            currentNeighbors.add(neighborSouth);
        }
        return currentNeighbors;
    }

    /**
     * @param allNodesInGrid set containing all nodes of the grid
     * @param neighbor position of the neighboring node
     * @param obstacles set of obstacles in the grid
     * @return true if the position of he neighbor node is valid and false otherwise
     */
    private static boolean isNeighborValid(Set<AstarableNode> allNodesInGrid, AstarableNode neighbor, Set<AstarableNode> obstacles){
        if(allNodesInGrid.contains(neighbor) && !(obstacles.contains(neighbor))){
            return true;
        }
        return false;
    }

    /**
     * @param obstacles an array containing a list of obstacles in the grid
     * @return a set of obstacles in the grid
     */
    private static Set<AstarableNode> getSetOfObstacles(Position[] obstacles){
        Set<AstarableNode> setOfObstacles = new HashSet<AstarableNode>();
        for(Position obstacle : obstacles){
            setOfObstacles.add(obstacle);
        }
        return setOfObstacles;
    }


    /**
     * @param args contains the URL of the site specified by the user
     * @return the equivalent Java objects of the JSON
     */
    public static Grid JsonGetter(String[] args) {
        String json = "";
        URL site;
        try {
            if (args.length != 0){
                site = new URL(args[0]);
            }else{
                return null;
            }

            InputStream InStream = site.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(InStream, Charset.forName("UTF-8"));
            JsonReader jsonReader = new JsonReader(inputStreamReader);
            Gson gson = new Gson();
            return gson.fromJson(jsonReader, Grid.class);
        } catch (Exception e){
            System.out.print(e);
        }
        Gson gson = new Gson();
        return gson.fromJson(json, Grid.class);
    }
}
