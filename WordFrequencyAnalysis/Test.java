import java.util.HashMap;
/**
 * Write a description of class test here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Test
{
    
    private static HashMap<String, Integer> testing;
    private int val;
    
    public Test(int val)
    {
        testing = new HashMap<String, Integer>();
        testing.put("num1", 1);
        testing.put("num2", 2);
        testing.put("num1", 3);
        this.val = val;
    }
    
    public HashMap<String, Integer> returnT(){
        return testing;
    }
}
