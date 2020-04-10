/*Editor: Johnson Gao 
 * Date This File Created: 2020-2-2 15:15:06
 * Description Of This Class:
 */
package towerofhanoicalculator;

import java.util.ArrayList;

/**
 *
 * @author app
 */
public class GFG
{

    private int counter;

    private ArrayList<String[]> results;

    public GFG()
    {
        results = new ArrayList<>();
        counter = 0;
    }

    public void towerOfHanoi(int n, String from_rod, String to_rod, String aux_rod)
    {
        //String result;
        //counter plus one
        this.counter++;
        String[] step = new String[3];
        //if the disk is the biggest one, move it from the initi
        if (n == 1)
        {
            step[0] = "1";
            step[1] = from_rod;
            step[2] = to_rod;
            results.add(step);
            //results.add("Move disk 1 from" + from_rod + "to" + to_rod);
            return;
        }
        //if not one, move it from
        towerOfHanoi(n - 1, from_rod, aux_rod, to_rod);
        //results.add("Move disk " + n + " from" + from_rod + "to" + to_rod);
        step[0] = n + "";
        step[1] = from_rod;
        step[2] = to_rod;
        results.add(step);
        towerOfHanoi(n - 1, aux_rod, to_rod, from_rod);
    }

    public ArrayList<String[]> getResults()
    {
        return results;
    }

    public int getCounter()
    {
        return counter;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        GFG gfg = new GFG();

    }

}
