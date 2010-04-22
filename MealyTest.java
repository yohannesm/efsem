import org.junit.* ;
import static org.junit.Assert.*;
import java.util.*;

//Marcell is driving
public class MealyTest {
    @Test
    public void test1() {
        String n1 = "fsm1";
	ArrayList<String> s1 = new ArrayList<String>();
	s1.add("A"); s1.add("B");
	Pair<String, Character> tr1 = new Pair<String, Character>("A", '0');
	Pair<String, Character> tr2 = new Pair<String, Character>("A", '1');
	Pair<String, Character> tr3 = new Pair<String, Character>("B", '0');
	Pair<String, Character> tr4 = new Pair<String, Character>("B", new Character('1'));
	Pair<String, String> trs1 = new Pair<String, String>("B", "0");
	Pair<String, String> trs2 = new Pair<String, String>("A", "1");
	Pair<String, String> trs3 = new Pair<String, String>("B", "1");
	Pair<String, String> trs4 = new Pair<String, String>("B", "0");
        HashMap<Pair<String, Character>, Pair<String, String>> trans = 
		new HashMap<Pair<String, Character>, Pair<String, String>>();
        trans.put(tr1, trs1);
        trans.put(tr2, trs2);
        trans.put(tr3, trs3);
        trans.put(tr4, trs4);
        
	Alphabet al = new Alphabet("01");
	String start = "A";

	Mealy mm1 = new Mealy(n1, s1, al, trans, start);

	mm1.step('0');
	assertEquals(mm1.getOutput(), "0");
	mm1.reset();
	mm1.run("0011100");
	assertEquals(mm1.getOutput(), "0100011");
	mm1.reset();
	assertEquals(mm1.getOutput(), "");
	mm1.run("1100101");
	assertEquals(mm1.getOutput(), "1101010");

    }
}
