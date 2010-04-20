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
     
   
   public Mealy(String name1, ArrayList<String> s1, Alphabet a1, HashMap<Pair<String, Character>, Pair<String, String> > t1, String s2){
	states = s1;
	name = name1;
	inputAlpha = a1;
	trans = t1;
	startState = s2;

	currentState = startState;
        output = new StringBuffer();
	}

   
   public boolean step(char i){
      if(inputAlpha.valid(i)) {
        System.out.println("INSIDE IF STEP");
      Character getChar = new Character(i);

        System.out.println("INSIDE IF STEP");
      Pair<String, Character> np = new Pair<String, Character>(currentState, getChar);
        System.out.println(np);
      Pair<String, String> out = trans.get(np);
        System.out.println(np);
      currentState = out.getFirst();
        System.out.println("INSIDE IF STEP");
      output.append(out.getSecond() );
        System.out.println("INSIDE IF STEP");
      return true;
     }
      else{
        System.out.println("INSIDE ELSE STEP");
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
	   System.out.println( input.charAt(i));
	}
       return getOutput();
    }

   public String toString(){

     StringBuffer result = new StringBuffer();
     result.append("Alphabet = ");
     result.append(inputAlpha.toString());
     result.append("\n");
     result.append("States = ");
     result.append(states.toString());
     result.append("\n");
     result.append("Transitions = ");
     result.append(trans.toString());
     result.append("\n");
     result.append("start state = ");
     result.append(startState.toString());
     result.append("\n");
     result.append("current state(s) = ");
     result.append(currentState.toString());
     result.append("\n");
     /*
     result.append("output so far = ");
     result.append(currentState.toString());
     result.append("\n");*/


     return result.toString();
   }
  }//end Mealy

