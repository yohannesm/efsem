import org.junit.* ;
import static org.junit.Assert.*;

public class AlphabetTest {
    @Test
    public void testValid1() {
        Alphabet a = new Alphabet("|d");
        assertEquals(a.valid('1'), true);
    }
    @Test
    public void testValid2() {
        Alphabet a = new Alphabet("|d|d");
        assertEquals(a.valid('1'), false);
    }
    @Test
    public void testValidAlphabet1() {
        Alphabet a = new Alphabet("|d");
        assertEquals(a.valid('a'), false);
    }
    @Test
    public void testValidAlphabet2() {
        Alphabet a = new Alphabet("|s");
        assertEquals(a.valid("!&%"), true);
    }
    @Test
    public void testGeneral(){
	  Alphabet test1 = new Alphabet();
          Alphabet test2 = new Alphabet("|d");
          Alphabet test3 = new Alphabet("01");

          assertEquals(test1.valid('c'), true);
          assertEquals(test1.valid('|'), false);
          assertEquals(test1.valid('l'), true);
          assertEquals(test2.valid('c'), false);
          assertEquals(test2.valid('1'), true);
          assertEquals(test2.valid('5'), true);
          assertEquals(test3.valid('c'), false);
          assertEquals(test3.valid('1'), true);
    }
    
}
