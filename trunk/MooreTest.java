import org.junit.* ;
import static org.junit.Assert.*;
import java.util.*;

//Marcell is driving
public class MooreTest {
    @Test
    public void test1() {
        String n1 = "fsm1";
	ArrayList<String> s1 = new ArrayList<String>();
	s1.add("A"); s1.add("B");
	Pair<String, Character> tr1 = new Pair<String, Character>("A", '0');
	Pair<String, Character> tr2 = new Pair<String, Character>("A", '1');
	Pair<String, Character> tr3 = new Pair<String, Character>("B", '0');
	Pair<String, Character> tr4 = new Pair<String, Character>("B", new Character('1'));
        HashMap<Pair<String, Character>, String> trans = 
		new HashMap<Pair<String, Character>, String>();
        trans.put(tr1, "A");
        trans.put(tr2, "B");
        trans.put(tr3, "B");
        trans.put(tr4, "B");
        
	ArrayList<String> s2 = new ArrayList<String>();
	s2.add("B");
	ArrayList<String> s3 = new ArrayList<String>();
	s3.add("B");
	Alphabet al = new Alphabet("01");
	String start = "A";

	Moore mm1 = new Moore(n1, s1, al, trans, start,  s2, s3);

	assertEquals(mm1.IsAccepting(), false);
	assertEquals(mm1.IsFinal(), false);
	mm1.step('0');
	assertEquals(mm1.getCurrentState(), "A");
	mm1.step('1');
	assertEquals(mm1.getCurrentState(), "B");
	assertEquals(mm1.IsAccepting(), true);
	mm1.reset();
	assertEquals(mm1.getCurrentState(), "A");
	mm1.run("000010001110");
	assertEquals(mm1.getCurrentState(), "B");
	assertEquals(mm1.IsAccepting(), true);
	assertEquals(mm1.getOutput(), "ACCEPTS");

    }
}
