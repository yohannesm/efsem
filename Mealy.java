/* Mealy.java */

import java.util.*;
import java.io.*;

//David is driving
public class Mealy{

     private ArrayList<String> states;
     private alphabet inputAlpha;
     private HashMap<Pair<String, char>, Pair<String,String> > trans;
     private String startState;
     private String currentState;
   
   public Mealy(ArrayList<String> s1, alphabet a1, HashMap<Pair<String, char>, Pair<String, String> > t1, String s2){
	states = s1;
	inputAlpha = a1;
	trans = t1;
	startState = s2;

	currentState = startState;
	}

   
   public boolean step(char i){
      if(inputAlpha.valid(i)) {
      
      Pair<String, char> np = new Pair<String, char>(currentState, i);
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
