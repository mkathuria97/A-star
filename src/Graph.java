/**
 * Created by mayankkathuria on 2/27/17.
 */
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class Graph implements Astarable{

    private JsonCity [] nodes;
    private JsonEdge [] edges;

    public JsonCity[] getNodes() {
        return nodes;
    }

    public JsonEdge[] getEdges() {
        return edges;
    }

    public Set<AstarableNode> getAllNodes(){
        Set<AstarableNode> allNodesInGraph = new HashSet<AstarableNode>();
        JsonCity[] citiesInfo = getNodes();
        for(JsonCity cityInfo: citiesInfo){
             allNodesInGraph.add(new JsonCity(cityInfo.getName(), cityInfo.getLatitude(), cityInfo.getLongitude()));
        }
        return allNodesInGraph;
    }

    public double getHeuristicCostEstimate(AstarableNode node, AstarableNode end){
        double dx = Math.pow((((JsonCity)node).getLatitude() - ((JsonCity)end).getLatitude()), 2);
        double dy = Math.pow((((JsonCity)node).getLongitude() - ((JsonCity)end).getLongitude()), 2);
        return Math.sqrt(dx + dy);
    }


    public int getDistCurrentNeighbor(AstarableNode current, AstarableNode neighbor) {
        Map<ArrayList<String>, Integer> cityEdgesAndInfo = getCityEdgesAndInfo();
        ArrayList<String> option1 = new ArrayList<String>();
        option1.add(((JsonCity)current).getName());
        option1.add(((JsonCity)neighbor).getName());
        ArrayList<String> option2 = new ArrayList<String>();
        option2.add(((JsonCity)neighbor).getName());
        option2.add(((JsonCity)current).getName());
        if(cityEdgesAndInfo.containsKey(option1)){
            return cityEdgesAndInfo.get(option1);
        } else if(cityEdgesAndInfo.containsKey(option2)){
            return cityEdgesAndInfo.get(option2);
        }else{
            return 0;
        }
    }

    private Map<ArrayList<String>, Integer> getCityEdgesAndInfo(){
        Map<ArrayList<String>, Integer> cityEdgesAndInfo = new HashMap<ArrayList<String>, Integer>();
        JsonEdge[] cityEdgesInfo = getEdges();
        for(JsonEdge cityEdgeInfo: cityEdgesInfo){
            String node1 = cityEdgeInfo.getNode1();
            String node2 = cityEdgeInfo.getNode2();
            ArrayList<String> neighbors = new ArrayList<String>();
            neighbors.add(node1);
            neighbors.add(node2);
            int weight = cityEdgeInfo.getWeight();
            cityEdgesAndInfo.put(neighbors, weight);
        }
        return cityEdgesAndInfo;

    }

    public Set<AstarableNode> getCurrentNeighbors(AstarableNode current){
        Set<AstarableNode> neighbors = new HashSet<AstarableNode>();
        Map<ArrayList<String>, Integer> cityEdgesAndInfo = getCityEdgesAndInfo();
        Set<AstarableNode> allNodes = getAllNodes();
        for(ArrayList<String> cityEdgeAndInfo: cityEdgesAndInfo.keySet()){
            if(cityEdgeAndInfo.get(0).equalsIgnoreCase(((JsonCity)current).getName())){
                AstarableNode neighbor =  createJsonCity(cityEdgeAndInfo.get(1));
                neighbors.add(neighbor);
            } else if(cityEdgeAndInfo.get(1).equalsIgnoreCase(((JsonCity)current).getName())){
                AstarableNode neighbor =  createJsonCity(cityEdgeAndInfo.get(0));
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    public JsonCity createJsonCity(String cityName){
        Set<AstarableNode> allNodes = getAllNodes();
        for(AstarableNode node: allNodes){
            if(((JsonCity) node).getName().equalsIgnoreCase(cityName)){
                return (JsonCity)node;
            }
        }
        return null;
    }

    /**
     * @param args contains the URL of the site specified by the user
     * @return the equivalent Java objects of the JSON
     */
    public static Graph JsonGetter(String[] args) {
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
            return gson.fromJson(jsonReader, Graph.class);
        } catch (Exception e){
            //System.out.print(e);
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(json, Graph.class);
    }


}
