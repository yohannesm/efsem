import org.junit.* ;
import static org.junit.Assert.*;

public class FSMTest {
    @Test
    public void testMachineParse1() {
        String testParse = new String(
         "\n" +
         "\n" +
         "testMachine\n" +
         "\n" +
         "MACHINE_TYPE     :     MOORE\n" +
         "\n" +
         "STARTING_STATE:start\n" +
         "INPUT_ALPHABET:  |d,\n" +
         "\n" +
         "\n" +
         "start       :  sawdigit   : {|d}    sawcomma :  {,}\n" +
         "sawdigit!$  :    sawcomma:{}\n" +
         "sawcomma!   :  ");
         
        Machine testMachine = FSM.parseMachine(testParse);
        assertNotNull(testMachine);
        assertEquals(testMachine.getMachineType(), "MOORE");
        assertEquals(testMachine.getInputAlphabet().toString(), "0123456789,");
        assertEquals(testMachine.getStartState(), "start");
        
        testMachine.run("6345");
        
        assertEquals("ACCEPTS", testMachine.getOutput());
        testMachine.reset();
        testMachine.run(",52312");
        assertEquals("REJECTS", testMachine.getOutput()); 
    }
    @Test
    public void testMachineParse2() {
        String testParse = new String(
         "\n" +
         "\n" +
         "testMachine\n" +
         "\n" +
         "MACHINE_TYPE     :     MEALY\n" +
         "\n" +
         "STARTING_STATE:start\n" +
         "INPUT_ALPHABET:  |d,\n" +
         "\n" +
         "\n" +
         "start       :  sawdigit   : {|d}    sawcomma :  {,}\n" +
         "sawdigit!$  :    sawcomma:{}\n" +
         "sawcomma!   :  ");
         
        Machine testMachine = FSM.parseMachine(testParse);
        assertNull(testMachine);
    }
    @Test
    public void testMachineParse3() {
        String testParse = new String(
         "\n" +
         "\n" +
         "testMachine\n" +
         "\n" +
         "MACHINE_TYPE     :     MEALY\n" +
         "\n" +
         "STARTING_STATE:start\n" +
         "INPUT_ALPHABET:  |d,\n" +
         "\n" +
         "OUTPUT_ALPHABET: |d,\n" +
         "\n" +
         "start       :  sawdigit   : {|d/|d}    sawcomma :  {,/,}\n" +
         "sawdigit :    sawcomma:{}\n" +
         "sawcomma   :  ");
         
        Machine testMachine = FSM.parseMachine(testParse);
        System.out.println(testMachine);
        assertNotNull(testMachine);
        assertEquals("MEALY", testMachine.getMachineType());
        assertEquals("0123456789,", testMachine.getInputAlphabet().toString());
        assertEquals("start", testMachine.getStartState());
        
        
        
        testMachine.run("6345");
        
        assertEquals("0123456789,", testMachine.getOutput());
        testMachine.reset();
        testMachine.run(",52312");
        assertEquals(",", testMachine.getOutput());
        
    }
    @Test
    public void testMachineParse4() {
        String testParse = new String(
         "\n" +
         "\n" +
         "testMachine\n" +
         "\n" +
         "MACHINE_TYPE     :     MEALY\n" +
         "\n" +
         "STARTING_STATE:start\n" +
         "INPUT_ALPHABET:  |d,\n" +
         "\n" +
         "OUTPUT_ALPHABET: |d\n" +
         "\n" +
         "start       :  sawdigit   : {|d/|d}    sawcomma :  {,/,}\n" +
         "sawdigit :    sawcomma:{}\n" +
         "sawcomma   :  ");
         
        Machine testMachine = FSM.parseMachine(testParse);
        assertNull(testMachine);
    }
    @Test
    public void testMachineParse5() {
        String testParse = new String(
         "\n" +
         "\n" +
         "testMachine\n" +
         "\n" +
         "MACHINE_TYPE     :     MOORE\n" +
         "\n" +
         "STARTING_STATE:start\n" +
         "INPUT_ALPHABET:  |d,\n" +
         "\n" +
         "OUTPUT_ALPHABET: |d\n" +
         "\n" +
         "start       :  sawdigit   : {5}    sawcomma :  {,}\n" +
         "sawdigit :    sawcomma:{}\n" +
         "sawcomma   :  ");
         
        Machine testMachine = FSM.parseMachine(testParse);
        assertNull(testMachine);
    }
    @Test
    public void testMachineParse6() {
        String testParse = new String(
         "\n" +
         "\n" +
         "testMachine\n" +
         "\n" +
         "MACHINE_TYPE     :     MOORE\n" +
         "\n" +
         "STARTING_STATE:start\n" +
         "INPUT_ALPHABET:  |d,\n" +
         "\n" +
         "\n" +
         "start         sawdigit   : {5}    sawcomma :  {,}\n" +
         "sawdigit :    sawcomma:{}\n" +
         "sawcomma   :  ");
         
        Machine testMachine = FSM.parseMachine(testParse);
        assertNull(testMachine);
    }
}
