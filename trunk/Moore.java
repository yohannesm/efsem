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
     private int stepNumber;
     boolean invalidInput;
     
     public ArrayList<String> getStates() { return states; }
     public Alphabet getInputAlphabet() { return inputAlpha; }
     public HashMap<Pair<String, Character>, String> getTransitionFunction() { return trans; }
     public String getStartState() { return startState; }
     public ArrayList<String> getAcceptingStates() { return acceptingStates; }
     public String getCurrentState() { return currentState; }
     public ArrayList<String> getFinalStates() { return finalStates; }
     public int getStepNumber() { return stepNumber; }
     public boolean isValidInput() { return !invalidInput; }
   
   public Moore(String name1, ArrayList<String> s1, Alphabet a1, HashMap<Pair<String, Character>, String> t1, String s2, 
   		 ArrayList<String> s3, ArrayList<String> finals){
        name = name1;
	states = s1;
	inputAlpha = a1;
	trans = t1;
	startState = s2;
	acceptingStates = s3;
	finalStates = finals;

	currentState = startState;
	invalidInput = false;
	stepNumber = 0;
	if (warning) {
		if (acceptingStates.isEmpty() ) {
		System.out.println("FSM WARNING: " + name + " : MISSING ACCEPTING STATE");
		}
		
		String checkAlphabet = inputAlpha.toString();
		
		for( String state : states ) {
		 if (!finalStates.contains(state) ) {
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
		}
		
		if ( !connectedComponents() ) {
		System.out.println("FSM WARNING: " + name + " : UNCONNECTED COMPONENTS");
		}
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
 	          q.offer(trans.get(key));
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
   public boolean IsAccepting(){
       return acceptingStates.contains(currentState);
   }

   public boolean IsFinal() {
       return finalStates.contains(currentState);
   }
   
   public boolean step(char i){
      stepNumber++;      
      Character getChar = new Character(i);
      if(inputAlpha.valid(i) && !invalidInput) {
      String nextState = null;
      if ( IsFinal() || currentState == "unlabeled final state") { nextState = currentState; }
      else {
      Pair<String, Character> np = new Pair<String, Character>(currentState, getChar);
      for( Pair<String, Character> key : trans.keySet() ) {
      	if ( key.equals(np) ) {
      		nextState = trans.get(key);
      	}
      }
      }
      if ( nextState == null ) {
       if (utt) {
        nextState = new String("unlabeled final state");
       }
       else {
        nextState = currentState;
       }
      }
      if ( verbose ) {
      	System.out.println(stepNumber + " : " + currentState + " -- " + getChar.toString() + " --> "
      				+ nextState);
      }
      
      currentState = nextState;
      
      return true;
     }
     else{
      invalidInput = true;
      if ( verbose ) {
      	System.out.println(stepNumber + " : " + currentState + " -- " + getChar.toString() + 
      			" -- INVALID INPUT");
      }
      return false;
     }
   }

   public void reset() {
	currentState = startState;
	stepNumber = 0;
	invalidInput = false;
   }

   public String getOutput() {
      if ( IsAccepting() && !invalidInput ) {
	return "ACCEPTS";
      } else {
	return "REJECTS";
      }
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
