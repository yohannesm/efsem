import java.io.*;
import java.util.*;

//Marcell is driving
public class Moore extends Machine{

     private ArrayList<String> states;
     private Alphabet inputAlpha;
     private HashMap<Pair<String, Character>, String> trans;
     private String startState;
     private ArrayList<String> acceptingStates;
     private String currentState;
     private ArrayList<String> finalStates;
   
   public Moore(String name1, ArrayList<String> s1, Alphabet a1, HashMap<Pair<String, Character>, String> t1, String s2, 
   		 ArrayList<String> s3){
        name = name1;
	states = s1;
	inputAlpha = a1;
	trans = t1;
	startState = s2;
	acceptingStates = s3;

	currentState = startState;
	}

   public boolean IsAccepting(){
       return acceptingStates.contains(currentState);
   }

   public boolean IsFinal() {
       return finalStates.contains(currentState);
   }
   
   public boolean step(char i){
      if(inputAlpha.valid(i)) {
 
      if (IsFinal() ) return true;

      Character getChar = new Character(i);

      Pair<String, Character> np = new Pair<String, Character>(currentState, getChar);
      currentState = trans.get(np);
      return true;
     }
     else{
      return false;
     }
   }

   public void reset() {
	currentState = startState;
   }

   public String getOutput() {
      if ( IsAccepting() ) {
	return "ACCEPTING";
      } else {
	return "REJECTING";
      }
   }


   public String run(String input) {
       for (int i = 0; i < input.length(); i++) {
	   step(input.charAt(i) );
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
     result.append("accepting state(s) = ");
     result.append(acceptingStates.toString());
     result.append("\n");
     result.append("current state(s) = ");
     result.append(currentState.toString());
     result.append("\n");
     result.append("final state(s) = ");
     result.append(finalStates.toString());
     result.append("\n");


     return result.toString();
   }

}//end Moore Class
