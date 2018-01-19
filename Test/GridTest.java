import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.google.gson.Gson;

public class GridTest {
    private static final String gridExample = "{\n" +
            "   \"dimension\": 10,\n" +
            "   \"start\": {\"x\": 0, \"y\": 0},\n" +
            "   \"end\": {\"x\": 3, \"y\": 2},\n" +
            "   \"obstacles\": [\n" +
            "      {\"x\": 1, \"y\": 0},\n" +
            "      {\"x\": 1, \"y\": 1},\n" +
            "      {\"x\": 1, \"y\": 2},\n" +
            "      {\"x\": 1, \"y\": 3},\n" +
            "      {\"x\": 2, \"y\": 3},\n" +
            "      {\"x\": 3, \"y\": 3},\n" +
            "      {\"x\": 4, \"y\": 3},\n" +
            "      {\"x\": 5, \"y\": 3},\n" +
            "      {\"x\": 5, \"y\": 4}\n" +
            "   ]\n" +
            "}      ";

    private static final String startExample = "{\"x\": 0, \"y\": 0}";
    private static final String endExample = "{\"x\": 3, \"y\": 2}";
    private static final String obstacleExample = "{\"x\": 3, \"y\": 3}";

    Gson gson = new Gson();
    Grid grid;
    Position start;
    Position end;
    Position obstacle;

    /**
     * @throws Exception if the JSON string which is to be converted into equivalent Java objects  is inaccurate
     */
    @Before
    public void setUp() throws Exception {
        //deserializtion
        grid = gson.fromJson(gridExample, Grid.class);
        start = gson.fromJson(startExample, Position.class);
        end = gson.fromJson(endExample, Position.class);
        obstacle = gson.fromJson(obstacleExample, Position.class);
    }

    /**
     * @throws Exception if the test fails
     * tests for the dimension of the grid
     */
    @Test
    public void getDimensionTest() throws Exception {
        assertEquals(10, grid.getDimension());
    }

    /**
     * @throws Exception if the test fails
     * tests for the start position
     */
    @Test
    public void getStartTest() throws Exception {
        assertEquals(start, grid.getStart());
    }

    /**
     * @throws Exception if the test fails
     * tests for the end position
     */
    @Test
    public void getEndTest() throws Exception {
        assertEquals(end, grid.getEnd());
    }

    /**
     * @throws Exception if the test fails
     * tests for the obstacles in the grid
     */
    @Test
    public void getObstaclesTest() throws Exception {
        assertEquals(obstacle, grid.getObstacles()[5]);
    }
}