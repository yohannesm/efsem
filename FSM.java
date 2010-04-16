import java.util.*;
import java.io.*;
import java.util.regex.*;

//Marcell is driving
public class FSM  {


public static String read(String inFile) throws Exception {

	FileInputStream in = new FileInputStream(inFile);
	StringBuffer input = new StringBuffer();
	//DataInputStream dataIn = new DataInputStream(in);
        
	while(in.available() > 0){
	 input.append( (char)in.read());
	 }
    in.close();
    return input.toString();
}//end read

//David is driving
public static Machine parseMachine(String input){
   String[] lines = input.split("\n");
   String name = lines[0].trim();
   Alphabet input_alphabet = null;
   Alphabet output_alphabet = null;
   String machineType = null;
   String start_state = null;
   int line = 1;
   boolean input_alphaFound = false;
   boolean output_alphaFound = false;
   boolean machine_typeFound = false;
   boolean starting_stateFound = false;
   Pattern inA = Pattern.compile("INPUT_ALPHABET(\\s)+:(\\s)+");
   Pattern outA = Pattern.compile("OUTPUT_ALPHABET(\\s)+:(\\s)+");
   Pattern mType = Pattern.compile("MACHINE_TYPE(\\s)+:(\\s)+");
   Pattern sState = Pattern.compile("STARTING_STATE(\\s)+:(\\s)+");
   Pattern a = Pattern.compile("(\\S)+");
   Pattern mooreOrMealy = Pattern.compile("(MOORE|MEALY)");
   Pattern MealyTransition = Pattern.compile("(\\S)+:\\{(\\D|(\\,\\s))*\\}");
   Pattern MooreTransition = Pattern.compile("(\\S)+:\\{(\\S,\\s)+\\S\\}");
   Pattern colonMatch = Pattern.compile("(\\s)*:(\\s)*");
   Matcher m = null;

   for(; line<lines.length && !machine_typeFound && !starting_stateFound; line++){
     String testLine = lines[line];
     m = inA.matcher(testLine);
     if (m.find() && !input_alphaFound) {
         testLine = m.replaceFirst("");
         m = a.matcher(testLine);
         if (m.find() ) {
           input_alphabet = new Alphabet( m.group() );
         }
         input_alphaFound = true;         
     }
     m = outA.matcher(testLine);
     if (m.find() && !output_alphaFound) {
         testLine = m.replaceFirst("");
         m = a.matcher(testLine);
         if (m.find() ) {
           output_alphabet = new Alphabet( m.group() );
         }
         output_alphaFound = true;         
     }
     m = mType.matcher(testLine);
     if (m.find() && !machine_typeFound) {
         testLine = m.replaceFirst("");
         m = mooreOrMealy.matcher(testLine);
         if (m.find() ) {
           machineType = m.group();
         }
         output_alphaFound = true;         
     }
     m = sState.matcher(testLine);
     if (m.find() ) {
       testLine = m.replaceFirst("");
       m = a.matcher(testLine);
       if (m.find() ) {
         start_state = m.group();
       }
       starting_stateFound = true;
     }
   }
   if (machineType.equals("MEALY") ) {
   ArrayList<String> states = new ArrayList<String>();
   String currentState = null;
   String nextState = null;
   Character inputChar = null;
   String outputString = null;
   String transitionParse = null;
   HashMap <Pair<String, Character>, Pair<String,String> > transitionFunction = new 		        HashMap<Pair<String, Character>, Pair<String, String> > ();
      
   
   for(; line < lines.length; line++) {
    
        String testLine = lines[line];
        
   	m = a.matcher(testLine);
   	
   	if (m.find() ) {
	        currentState = m.group();
	        states.add(currentState);
	        testLine = m.replaceFirst("");
	        m = colonMatch.matcher(testLine);
	        testLine = m.replaceFirst("");
	        m = MealyTransition.matcher(testLine);
	        String [] transitions = testLine.split("\\}(\\s)+(\\n)?");
	        int num = transitions.length - 1;
	        String lastTransition = transitions[num];
	        int stringLen = lastTransition.length();
	        
	        if (lastTransition.charAt(stringLen - 1) == '}' ) {
	        	transitions[num] = lastTransition.substring(0, stringLen - 1);
	        }
	        for (int i = 0; i < transitions.length; i++) {
	        	String transition[] = transitions[i].split(":\\{");
	        	nextState = transition[0];
	        	String [] pairs = transition[1].split("\\,\\s");
	        	for (int j = 0; j < pairs.length; j++) {
	        		inputChar = new Character(pairs[j].charAt(0));
	        		outputString = pairs[j].substring(2);
	        		Pair<String, Character> strChr = new Pair<String, Character>(currentState, inputChar);
	        		Pair<String, String> strStr = new Pair<String, String>(nextState, outputString);
	        		transitionFunction.put(strChr, strStr);	        		
	        	}
	        }
	        
	}
	


   }
   
   Mealy mealyMachine = new Mealy(states, input_alphabet, transitionFunction, start_state);
   return mealyMachine;
   } else {
   ArrayList<String> states = new ArrayList<String>();
   ArrayList<String> acceptingStates = new ArrayList<String>();
   ArrayList<String> finalStates = new ArrayList<String>();
   String currentState = null;
   String nextState = null;
   Character inputChar = null;
   String transitionParse = null;
   HashMap <Pair<String, Character>, String > transitionFunction = new 		        HashMap<Pair<String, Character>, String > ();
      
   
   for(; line < lines.length; line++) {
    
        String testLine = lines[line];
        
   	m = a.matcher(testLine);
   	
   	if (m.find() ) {
	        currentState = m.group();
	        states.add(currentState);
	        testLine = m.replaceFirst("");
	        m = colonMatch.matcher(testLine);
	        testLine = m.replaceFirst("");
	        m = MealyTransition.matcher(testLine);
	        String [] transitions = testLine.split("\\}(\\s)+(\\n)?");
	        int num = transitions.length - 1;
	        String lastTransition = transitions[num];
	        int stringLen = lastTransition.length();
	        if (lastTransition.charAt(stringLen - 1) == '}' ) {
	        	transitions[num] = lastTransition.substring(0, stringLen - 1);
	        }
	        for (int i = 0; i < transitions.length; i++) {
	        	String transition[] = transitions[i].split(":\\{");
	        	nextState = transition[0];
	        	String [] pairs = transition[1].split("\\,\\s");
	        	for (int j = 0; j < pairs.length; j++) {
	        		inputChar = new Character(pairs[j].charAt(0));
	        		Pair<String, Character> strChr = new Pair<String, Character>(currentState, inputChar);
	        		transitionFunction.put(strChr, nextState);	        		
	        	}
	        }
	        
	}
	


   }
   
   for ( Pair<String, Character> i  :  transitionFunction.keySet() ) {
   	System.out.println(i.getFirst() );
   	System.out.println(i.getSecond() );
   	System.out.println( transitionFunction.get(i) );
   	System.out.println("");
   }
   Moore mooreMachine = new Moore(states, input_alphabet, transitionFunction, start_state, acceptingStates);
   return mooreMachine;
   }
}//end parseMachine

//Marcell is driving
      public static void main(String[] args) {
         try{
	   String temp = read(args[0]);
	   parseMachine(temp);

	 }//end try

	 catch(Exception e){
	 System.err.println("exception thrown: " + e.getMessage() );
	 }

      }//end main

     }//end FSM class
