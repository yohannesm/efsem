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
     private int stepNumber;
     boolean invalidInput;
     
     public ArrayList<String> getStates() { return states; }
     public Alphabet getInputAlphabet() { return inputAlpha; }
     public HashMap<Pair<String, Character>, Pair<String, String> > getTransitionFunction() { return trans; }
     public String getStartState() { return startState; }
     public String getCurrentState() { return currentState; }
     public int getStepNumber() { return stepNumber; }
     public boolean isValidInput() { return !invalidInput; }
   
   public Mealy(String name1, ArrayList<String> s1, Alphabet a1, HashMap<Pair<String, Character>, Pair<String, String> > t1, String s2){
	states = s1;
	name = name1;
	inputAlpha = a1;
	trans = t1;
	startState = s2;
        invalidInput = false;
	stepNumber = 0;
	currentState = startState;
        output = new StringBuffer();
        if (warning) {		
		String checkAlphabet = inputAlpha.toString();
		
		for( String state : states ) {
		  boolean checkAl = true;
		  for( int i = 0; i < checkAlphabet.length() && checkAl; i++) {
		   Character checkChar = new Character(checkAlphabet.charAt(i));
		   Pair<String, Character> checkPair = new Pair<String, Character>(state, checkChar);
		   boolean checkAl2 = false;
		   for( Pair<String, Character> key : trans.keySet() ) {
		    if (key.equals(checkPair)) {
		     checkAl2 = true;
		    }
		   }
		   if (!checkAl2) checkAl = false;
		  }
		  if (!checkAl) {
		   System.out.println("FSM WARNING: " + name + " : INCOMPLETE TRANSITION TABLE FOR STATE " + state);
		  }
		 
		}
		
		if ( !connectedComponents() ) {
		System.out.println("FSM WARNING: " + name + " : UNCONNECTED COMPONENTS");
		}
	}
	}

   
   public boolean step(char i){      
      stepNumber++;
      Character getChar = new Character(i);
      if(inputAlpha.valid(i) && !invalidInput) {
      String nextState = null;
      String outputString = null;
      Pair<String, Character> np = new Pair<String, Character>(currentState, getChar);
      Pair<String, String> out = null;
      if ( currentState != "unlabeled final state" ) {
      for ( Pair<String, Character> key : trans.keySet() ){
      	if ( key.equals(np) ) {
      	  out = trans.get(key);
      	}
      }
      }
      
      if (out == null) {
        if (utt) {
          nextState = new String("unlabeled final state");
        }
        else {
          nextState = currentState;
        }
        outputString = new String("");
      }
      else {
      	nextState = out.getFirst();
      	outputString = out.getSecond();
      }
      if (verbose) {
        System.out.println(stepNumber + " : " + currentState + " -- " + getChar.toString() + " --> "
      				+ nextState + " " + outputString);
      }
      currentState = nextState;
      output.append(outputString );
      return true;
     }
      else{
      output = new StringBuffer("");
      invalidInput = true;
      if ( verbose ) {
      	System.out.println(stepNumber + " : " + currentState + " -- " + getChar.toString() + 
      			" -- INVALID INPUT");
      }
      return false;
     }
   }
    private boolean connectedComponents() {
   	TreeSet<String> searched = new TreeSet<String> ();
 	Queue<String> q = new LinkedList<String> ();
 	q.offer(startState);
 	String alpha = inputAlpha.toString();
 	while ( !q.isEmpty() ) {
 	  String searchString = q.remove();
 	  if (!searched.contains(searchString) ) {
 	    for (int i = 0; i < alpha.length(); i++) {
 	      Character testChar = new Character(alpha.charAt(i));
 	      Pair<String, Character> testPair = new Pair<String, Character> (searchString, testChar);
 	      for (Pair<String, Character> key : trans.keySet() ) {
 	        if ( key.equals(testPair) ) {
 	          q.offer( (trans.get(key)).getFirst() );
 	        }
 	      }
 	    }
 	  }
 	  searched.add(searchString);
 	}
   	for( String state : states ) {
   	  if ( !searched.contains(state) ) return false;
   	}
   	
   	return true;
   }
   public void reset() {
	currentState = startState;
        output = new StringBuffer();
        invalidInput = false;
        stepNumber = 0;
   }

   public String getOutput() {
      return output.toString();
   }

   public String run(String input) {
       if (!inputAlpha.valid(input) && warning) {
       	System.out.println("FSM WARNING: " + name + " : UNSUPPORTED ALPHABET FOR " + input);
       }
       for (int i = 0; i < input.length(); i++) {
	   if( !step(input.charAt(i)) ) return getOutput();
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

