/* Moore.java */

import java.util.*;
import java.io.*;

//Marcell is driving
public class Moore{

     private ArrayList<String> states;
     private alphabet inputAlpha;
     private HashMap<Pair<String, char>, String> trans;
     private String startState;
     private ArrayList<String> acceptingStates;
     private String currentState;
   
   public Moore(ArrayList<String> s1, alphabet a1, HashMap<Pair, String> t1, String s2, 
   		 ArrayList<String> s3){
	states = s1;
	inputAlpha = a1;
	trans = t1;
	startState = s2;
	acceptingStates = s3;

	currentState = startState;
	}

   public void IsAccepting(){
       return acceptingStates.contains(currentState);
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


   public static void main(String[] args){
   
   }// end Main

}//end Moore Class
