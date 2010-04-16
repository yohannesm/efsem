import java.io.*;
import java.util.*;

//David is driving
public class Mealy extends Machine{

     private ArrayList<String> states;
     private Alphabet inputAlpha;
     private HashMap<Pair<String, Character>, Pair<String,String> > trans;
     private String startState;
     private String currentState;

     private StringBuffer output;
     
   
   public Mealy(ArrayList<String> s1, Alphabet a1, HashMap<Pair<String, Character>, Pair<String, String> > t1, String s2){
	states = s1;
	inputAlpha = a1;
	trans = t1;
	startState = s2;

	currentState = startState;
        output = new StringBuffer();
	}

   
   public boolean step(char i){
      if(inputAlpha.valid(i)) {
      Character getChar = new Character(i);

      Pair<String, Character> np = new Pair<String, Character>(currentState, getChar);
      Pair<String, String> out = trans.get(np);
      currentState = out.getFirst();
      output.append(out.getSecond() );
      return true;
     }
     else{
      output = null;
      return false;
     }
   }

   public void reset() {
	currentState = startState;
        output = new StringBuffer();
   }

   public String getOutput() {
      return output.toString();
   }

   public String run(String input) {
       for (int i = 0; i < input.length(); i++) {
	   step(input.charAt(i) );
	}
       return getOutput();
    }
  }//end Mealy

