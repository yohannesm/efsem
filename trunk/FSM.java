import java.util.*;
import java.io.*;
import java.util.regex.*;

//Marcell is driving
public class FSM  {
      
      static boolean VERBOSE = false;
      static boolean WARNINGS = false;
      static boolean UTT = false;

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
   Pattern alphaNumeric = Pattern.compile("(\\w)+");
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
   
   Mealy mealyMachine = new Mealy(name, states, input_alphabet, transitionFunction, start_state);
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
        
   	m = alphaNumeric.matcher(testLine);
   	
   	if (m.find() ) {
	        currentState = m.group();
	        states.add(currentState);
	        testLine = m.replaceFirst("");
	        if (testLine.charAt(0) == '$' || testLine.charAt(1) == '$') acceptingStates.add(currentState);
	        if (testLine.charAt(0) == '!' || testLine.charAt(1) == '!') finalStates.add(currentState);
	        if (testLine.charAt(0) == '!' || testLine.charAt(0) == '$') testLine = testLine.substring(1);
	        if (testLine.charAt(0) == '!' || testLine.charAt(0) == '$') testLine = testLine.substring(1);
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
   /*
   for ( Pair<String, Character> i  :  transitionFunction.keySet() ) {
   	System.out.println(i.getFirst() );
   	System.out.println(i.getSecond() );
   	System.out.println( transitionFunction.get(i) );
   	System.out.println("");
   }*/
   
   Moore mooreMachine = new Moore(name, states, input_alphabet, transitionFunction, start_state, acceptingStates, finalStates);
   //System.out.println(mooreMachine);
   return mooreMachine;
   }
}//end parseMachine

public static ArrayList<String> inputParser(String inputString){
      ArrayList<String> result = new ArrayList<String>();
      String[] lines = inputString.split("\n");
      Pattern p = Pattern.compile("(\\S)+(\\s)+:(\\s)+(\\S)+");
      for(int i =0; i < lines.length ;i++){
          //System.out.println(lines[i]);
      		Matcher m = p.matcher(lines[i]);
		if( m.matches() ){
		   String[] line = lines[i].split(":");
		   line[1] = line[1].trim();
		   result.add(line[1]);
		}
      }
      return result;
}


//Marcell is driving
      public static void main(String[] args) {
         try{
	  ArrayList<String> fsmList = new ArrayList<String>();
	  ArrayList<String> inputList = new ArrayList<String>();
	  //getting all the arguments list
	  for(int arg = 0; arg< args.length; arg++){
	     if(args[arg].equals("--fsm")){
	             boolean flag = true;
	             for(int i = arg+1; i<args.length && flag; i++){
		          if(args[i].contains("--")) flag = false;
			  else{
			  fsmList.add(args[i]);			  
			  }
		     }
	     }
	     else if(args[arg].equals("--verbose")){
	           //VERBOSE = true;
	           Machine.verbose = true;
	     }
	     else if(args[arg].equals("--warnings")){
	        //WARNINGS = true;
	        Machine.warning = true;
	     }
	     else if(args[arg].equals("--input")){
	             boolean flag = true;
	             for(int i = arg+1; i<args.length && flag; i++){
		          if(args[i].contains("--")) flag = false;
			  else{
			  inputList.add(args[i]);			  
			  }
		     }
	     }
	     else if(args[arg].equals("--unspecified-transitions-trap")){
	            //UTT = true;
	            Machine.utt = true;
	     }
	  }
	  //convert the fsm into machines
	  ArrayList<Machine> machineList = new ArrayList<Machine>();
	  ArrayList<String> machineInputs = new ArrayList<String>();
	  
	  for(String i : fsmList){
	        String fileReader = read(i);
		Machine mach = parseMachine(fileReader);
		if(mach!=null)
			machineList.add( mach );
	  }
	
         //converts the input files into String array
	  for(String i : inputList){
	       String fileReader = read(i);
	       ArrayList<String> temp1 = inputParser(fileReader);
	       machineInputs.addAll(temp1);
	  }
	  

	  for(Machine i : machineList){
	        //System.out.println(i);
	        for(String j : machineInputs){
		   String output = i.run(j);
		   if(i instanceof Moore){
		      System.out.println( i.name() + " : " + " " +  output + " " + j);
		   }
		   else{
		      System.out.println( i.name() + " : " +  j + " / " +  output);
		   }
		   i.reset();
		}
	  }

	   /*String temp = read(args[0]);
	   //parseMachine(temp);
	   ArrayList<String> inputStr = inputParser(temp);
	   for(String i : inputStr){
	      System.out.println(i);
	   }
          */
	 }//end try

	 catch(Exception e){
	 System.err.println("exception thrown: " + e.getMessage() );
	 }

      }//end main

     }//end FSM class
