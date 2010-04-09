/* Moore.java */

import java.util.*;
import java.io.*;

//Marcell is driving
public class Moore{

     private ArrayList<String> states;
     private alphabet inputAlpha;
     private HashMap<Pair<String, Character>, String> trans;
     private String startState;
     private ArrayList<String> acceptingStates;
     private String currentState;
     private ArrayList<String> finalStates;
   
   public Moore(ArrayList<String> s1, alphabet a1, HashMap<Pair<String, Character>, String> t1, String s2, 
   		 ArrayList<String> s3){
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

   private boolean IsFinal() {
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

   public static void main(String[] args){
   
   }// end Main

}//end Moore Class
