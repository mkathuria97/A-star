import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.google.gson.Gson;

public class PositionTest {
    private static final String coordinatesExample = "{\"x\": 1, \"y\": 0}";

    Gson gson = new Gson();
    Position position;

    /**
     * @throws Exception if the JSON string which is to be converted into equivalent Java objects is inaccurate
     */
    @Before
    public void setUp() throws Exception {
        //deserialization
        position = gson.fromJson(coordinatesExample, Position.class);
    }

    /**
     * @throws Exception if the test fails
     * tests for the x coordinate of the node
     */
    @Test
    public void getXTest() throws Exception {
        assertEquals(1, position.getX());
    }

    /**
     * @throws Exception if the test fails
     * tests for the y coordinate of the node
     */
    @Test
    public void getYTest() throws Exception {
        assertEquals(0, position.getY());
    }

    /**
     * @throws Exception if the test fails
     * tests for the format of the position when printed to the console
     */
    @Test
    public void toStringTest() throws Exception {
        assertEquals("x=1, y=0", position.toString());
    }
}