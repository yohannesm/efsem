/* Mealy.java */

import java.util.*;
import java.io.*;

//David is driving
public class Mealy{

     private ArrayList<String> states;
     private alphabet inputAlpha;
     private HashMap<Pair<String, Character>, Pair<String,String> > trans;
     private String startState;
     private String currentState;

     private StringBuffer output;
     
   
   public Mealy(ArrayList<String> s1, alphabet a1, HashMap<Pair<String, Character>, Pair<String, String> > t1, String s2){
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

   public static void main(String[] args){
   
   }// end Main

}//end Moore Class
