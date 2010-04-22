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
   Pattern ws = Pattern.compile("(\\s)+");   
   Matcher m = null;
   Matcher m1 = null;
   Matcher m2 = null;
   Matcher m3 = null;
   Matcher m4 = null;
   Matcher m5 = null;
   Matcher m6 = null;
   Matcher m7 = null;
   int line = 0;
   String name = null;
   boolean nameFound = false;
   for (nameFound = false; !nameFound && line < lines.length; line++) {
     String testLine = lines[line];
     m = ws.matcher(testLine );
     if (!m.matches() ) {
       testLine = testLine.trim();
       name = testLine;
       nameFound = true;
     }
   }
   if ( !nameFound ) {
   	System.out.println("FSM FILE ERROR: EMPTY FILE");
   	return null;
   }
   Alphabet input_alphabet = null;
   Alphabet output_alphabet = null;
   String machineType = null;
   String start_state = null;
   boolean input_alphaFound = false;
   boolean output_alphaFound = false;
   boolean machine_typeFound = false;
   boolean starting_stateFound = false;
   boolean end_parameters = false;
   Pattern inA = Pattern.compile("(\\s)*INPUT_ALPHABET(\\s)*:(\\s)*");
   Pattern outA = Pattern.compile("(\\s)*OUTPUT_ALPHABET(\\s)*:(\\s)*");
   Pattern mType = Pattern.compile("(\\s)*MACHINE_TYPE(\\s)*:(\\s)*");
   Pattern sState = Pattern.compile("(\\s)*STARTING_STATE(\\s)*:(\\s)*");
   Pattern a = Pattern.compile("(\\S)+");
   Pattern inMatch = Pattern.compile("(\\s)*INPUT_ALPHABET(\\s)*:(\\s)*(\\S)+(\\s)*");
   Pattern outMatch = Pattern.compile("(\\s)*OUTPUT_ALPHABET(\\s)*:(\\s)*(\\S)+(\\s)*");
   Pattern macMatch = Pattern.compile("(\\s)*MACHINE_TYPE(\\s)*:(\\s)*(\\S)+(\\s)*");
   Pattern startMatch = Pattern.compile("(\\s)*STARTING_STATE(\\s)*:(\\s)*(\\S)+(\\s)*");
   Pattern MealyTransition = Pattern.compile("(\\s)*(\\w)+(\\s)*:(\\s)*((\\w)+(\\s)*:(\\s)*\\{((\\S)+/(\\S)*\\,(\\s)+)*((\\S)+/(\\S)*)?\\}(\\s)+)*((\\w)+(\\s)*:(\\s)*\\{((\\S)+/(\\S)*\\,(\\s)+)*((\\S)+/(\\S)*)?\\}(\\s)*)?");
   Pattern MooreTransition = Pattern.compile("(\\s)*(\\w)+(\\s)*:(\\s)*((\\w)+(\\s)*:(\\s)*\\{((\\S)+\\,(\\s)+)*((\\S)+)?\\}(\\s)+)*((\\w)+(\\s)*:(\\s)*\\{((\\S)+\\,(\\s)+)*((\\S)+)?\\}(\\s)*)?");
   Pattern colonMatch = Pattern.compile("(\\s)*:(\\s)*");
   Pattern alphaNumeric = Pattern.compile("(\\w)+");

   for(; line<lines.length && !end_parameters; line++){
     String testLine = lines[line];
     m1 = inMatch.matcher(testLine);
     m2 = outMatch.matcher(testLine);     
     m3 = macMatch.matcher(testLine);
     m4 = startMatch.matcher(testLine);
     m5 = MealyTransition.matcher(testLine);
     m6 = MooreTransition.matcher(testLine);
     m7 = a.matcher(testLine);
     
     if ( !m1.matches() && !m2.matches() && !m3.matches() && !m4.matches() && !m5.matches() && !m6.matches()  && m7.find()) {
     	System.out.println("FSM FILE ERROR: " + name + " : INVA8LID FORMATTING ON LINE " + 
     		String.valueOf(line + 1));
     	return null;
     }
     
     if (m1.matches() ) {
	     if (input_alphaFound) {
     		System.out.println("FSM FILE ERROR: " + name + " : INVaALID FORMATTING ON LINE "
	         		+ String.valueOf(line + 1));
        	 return null;
	     }
     	 m = inA.matcher(testLine);
         testLine = m.replaceFirst(" ");
         testLine = testLine.trim();
         input_alphabet = new Alphabet( testLine );
         m = ws.matcher(testLine);
         if ( m.find() ) {
         	System.out.println("FSM FILE ERROR: " + name + " : INVALI2D FORMATTING ON LINE "
         		+ String.valueOf(line + 1));
         	return null;
         }
         if ( !input_alphabet.validAlphabet() ) {
           	System.out.println("FSM FILE ERROR: " + name + " : INPUT_ALPHABET HAS INVALID VALUE " +
           		input_alphabet.toString() );
           	return null;
         }         
         input_alphaFound = true;         
     }     
    
     if (m2.matches()) {
         if (output_alphaFound) {
     	System.out.println("FSM FILE ERROR: " + name + " : INVALID1 FORMATTING ON LINE "
         		+ String.valueOf(line + 1));
         return null;
 	    }
         m = outA.matcher(testLine);
         testLine = m.replaceFirst("");
         testLine.trim();
         output_alphabet = new Alphabet( testLine );
         m = ws.matcher(testLine);
         if ( m.find() ) {
         	System.out.println("FSM FILE ERROR: " + name + " : INVALID2 FORMATTING ON LINE "
         		+ String.valueOf(line + 1));
         }
         if ( !output_alphabet.validAlphabet() ) {
         	System.out.println("FSM FILE ERROR: " + name + " : OUTPUT_ALPHABET HAS INVALID VALUE " +
         		output_alphabet.toString() );
         	return null;
         }
         output_alphaFound = true;   
     }
     
     
     if (m3.matches()) {
   	  if (machine_typeFound) {
   	  	System.out.println("FSM FILE ERROR: " + name + " : INVALID3 FORMATTING ON LINE "
  	       		+ String.valueOf(line + 1));
 	        return null;
 	    }
     	 m = mType.matcher(testLine);
         testLine = m.replaceFirst("");
         testLine = testLine.trim();
         machineType = testLine;
         m = ws.matcher(testLine);
         if ( m.find() ) {
         	System.out.println("FSM FILE ERROR: " + name + " : INVALID4 FORMATTING ON LINE "
         		+ String.valueOf(line + 1));
         	return null;
         }
         if ( !(machineType.equals("MEALY")) && !(machineType.equals("MOORE")) ) {
         	System.out.println("FSM FILE ERROR: " + name + " : MACHINE_TYPE HAS INVALID VALUE "
         		+ machineType);
         	return null;
         }         
         machine_typeFound = true;         
     }
     
     
     if (m4.matches() ) {  
	     if (starting_stateFound) {
	     	System.out.println("FSM FILE ERROR: " + name + " : INVALI5D FORMATTING ON LINE "
	         		+ String.valueOf(line + 1));
	         return null;
	     }
       m = sState.matcher(testLine);
       testLine = m.replaceFirst("");
       testLine = testLine.trim();
       start_state = testLine;
       m = ws.matcher(testLine);
       if (m.find() ) {
         System.out.println("FSM FILE ERROR: " + name + " : INVALID FORMAT6TING ON LINE "
         		+ String.valueOf(line + 1));
         	return null;
       }
       m = alphaNumeric.matcher(testLine);
       if (!m.matches() ) {
       	System.out.println("FSM FILE ERROR: " + name + " : STARTING_STATE HAS INVALID VALUE " + start_state);
       	return null;
       }
       starting_stateFound = true;
     }
     if (m5.matches() || m6.matches() ) {end_parameters = true;}
     }
    
   
   if ( !(machine_typeFound) ) {
   	System.out.println("FSM FILE ERROR: " + name + " : MACHINE_TYPE IS MISSING");
   	return null;
   }
   if ( !(starting_stateFound) ) {
   	System.out.println("FSM FILE ERROR: " + name + " : STARTING_STATE IS MISSING");
   	return null;
   }
   if (machineType.equals("MOORE") && output_alphaFound ) {
   	System.out.println("FSM FILE ERROR: " + name + " : OUTPUT_ALPHABET IS INVALID PARAMETER FOR MOORE");
   	return null;
   }
   line--;
   if ( !(input_alphaFound) ) input_alphabet = new Alphabet();
   if ( !(output_alphaFound) ) output_alphabet = new Alphabet();
   //* +++++++++++++ MEALY MACHINE +++++++++++++ *//
 
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
	        
	        if (stringLen > 0 && lastTransition.charAt(stringLen - 1) == '}' ) {
	        	transitions[num] = lastTransition.substring(0, stringLen - 1);
	        }
	        if (stringLen > 0) {
	        for (int i = 0; i < transitions.length; i++) {
	        	String transition[] = transitions[i].split("(\\s)*:(\\s)*\\{");
	        	nextState = transition[0];
	        	String [] pairs = transition[1].split("\\,(\\s)+");
	        	for (int j = 0; j < pairs.length; j++) {
	        	  if ( pairs[j].length() > 0 && pairs[j].charAt(0) != '|' ) {
	        	        if ( pairs[j].length() < 1 || (pairs[j].length() >= 2 
	        	        		&& pairs[j].charAt(1) != '/' )) {
	        	          System.out.println("FSM FILE ERROR: " + name + " : IaNVALID FORMATTING ON "
	        	          		+ "LINE " + String.valueOf(line + 1));
	        	          return null;
	        	        }
	        	        if ( pairs[j].length() == 1 ) {
	        	          System.out.println("FSM FILE ERROR: " + name + " INVALID TRANSITION FOR "
	        	          	+ "MEALY ON LINE " + String.valueOf(line + 1));
	        	          return null;
	        	        }
	        	        
	        	        if ( !input_alphabet.valid( pairs[j].charAt(0) )) {
	        	          System.out.println("FSM FILE ERROR: " + name + " : INVALID TRANSITION " +
	        	          	"SYMBOL ON LINE " + String.valueOf(line + 1));
	        	          return null;
	        	        }
	        		inputChar = new Character(pairs[j].charAt(0));
	        		outputString = pairs[j].substring(2);
	        		outputString.replace("|a", "|l|u");
	        		outputString.replace("|l", "abcdefghijklmnopqrstuvwxyz");
	        		outputString.replace("|u", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	        		outputString.replace("|d", "0123456789");
	        		outputString.replace("|n", "123456789");
	        		outputString.replace("|s", ".,~!@$#%^&-+{}");	        		
	        		if ( !output_alphabet.valid( outputString )) {
	        		  System.out.println("FSM FILE ERROR: " + name + " : INVALID OUTPUT SYMBOL " +
	        		  	" ON LINE " + String.valueOf(line + 1));
	        		  return null;
	        		}
	        		Pair<String, Character> strChr = new Pair<String, Character>(currentState, inputChar);
	        		Pair<String, String> strStr = new Pair<String, String>(nextState, outputString);
	        		transitionFunction.put(strChr, strStr);	        		
	        	   }
	        	 if ( pairs[j].length() > 0 && pairs[j].charAt(0) == '/' ) {
	        	        if ( pairs[j].length() < 2 || (pairs[j].length() >= 3 
	        	        	&& pairs[j].charAt(2) != '/') ) {
	        	 	  System.out.println("FSM FILE ERROR: " + name + " : INVALID FaORMATTING ON "
	        	 	  	+ "LINE " + String.valueOf(line + 1));
	        	 	  return null;
	        	 		}
	        	 	if ( pairs[j].length() == 2 ) {
	        	 	System.out.println("FSM FILE ERROR: " + name + " : INVALID TRANSITION FOR "
	        	 		+ "MEALY ON LINE " + String.valueOf(line + 1));
	        	 		return null;
	        	 	}	        	 	
	        	 	        	 	
	        	 	String shortcut = new String("");
	        	 	if ( pairs[j].charAt(1) == 'd' ) shortcut = new String("0123456789");
	        	 	if ( pairs[j].charAt(1) == 'n' ) shortcut = new String("123456789");
	        	 	if ( pairs[j].charAt(1) == 'a' ) 
	        	 	   shortcut = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
	        	 	if ( pairs[j].charAt(1) == 'l' )
	        	 	   shortcut = new String("abcdefghijklmnopqrstuvwxyz");
	        	 	if ( pairs[j].charAt(1) == 'u' )
	        	 	   shortcut = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	        	 	if ( pairs[j].charAt(1) == 's' )
	        	 	   shortcut = new String(".,~!@$#%^&-+{}");
	        	 	
	        	 	if ( !input_alphabet.valid(shortcut) ) {
	        	 	 System.out.println("FSM FILE ERROR: " + name + " : INVALID TRANSITION "
	        	 	 	+ "SYMBOL ON LINE " + String.valueOf(line + 1));
	        	 	 	return null;
	        	 	}
	        	 	for (int x = 0; x < shortcut.length(); x++ ) {
	        	 	 inputChar = new Character(shortcut.charAt(x));
	        		 outputString = pairs[j].substring(3);
	        		 outputString.replace("|a", "|l|u");
	        		 outputString.replace("|l", "abcdefghijklmnopqrstuvwxyz");
	        		 outputString.replace("|u", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	        		 outputString.replace("|d", "0123456789");
	        		 outputString.replace("|n", "123456789");
	        		 outputString.replace("|s", ".,~!@$#%^&-+{}");	        		
	        		 if ( !output_alphabet.valid( outputString )) {
	        		   System.out.println("FSM FILE ERROR: " + name + " : INVALID OUTPUT SYMBOL " +
	        		   	" ON LINE " + String.valueOf(line + 1));
	        		   return null;
	        		 }
	        		 Pair<String, Character> strChr = new Pair<String, Character>(currentState, inputChar);
	         		 Pair<String, String> strStr = new Pair<String, String>(nextState, outputString);
	        		 transitionFunction.put(strChr, strStr);
	        	 	}
	        	 }
	        	}}
	        }
	   ArrayList< Character > deterministicCheck = new ArrayList<Character> ();
	        for (Pair<String, Character> key : transitionFunction.keySet() ){
	          if (key.getFirst().equals(currentState) ) {
	            if ( deterministicCheck.contains( key.getSecond() ) ) {
	              System.out.println("FSM FILE ERROR: " + name + " : NON-DETERMINISTIC TRANSITION " +
	              		"TABLE FOR STATE " + currentState);
	              	return null;
	            }
	            deterministicCheck.add(key.getSecond() );
	          }
	        }     
	}
	


   }
   
   if  ( !states.contains(start_state) ) {
   	System.out.println("FSM FILE ERROR: " + name + " : STARTING_STATE HAS INVALID VALUE " + start_state);
   	return null;
   }
   
   for ( Pair<String, Character> key : transitionFunction.keySet() ) {
     String s1 = key.getFirst();
     String s2 = (transitionFunction.get(key)).getFirst();
     if ( !states.contains( s2 ) ) {
     	System.out.println("FSM FILE ERROR: " + s1 + " TRANSITIONS TO NON-EXISTANT STATE");
     	return null;
     	}
   }
   Mealy mealyMachine = new Mealy(name, states, input_alphabet, transitionFunction, start_state);
   return mealyMachine;
   } else {
 
   //* +++++++++++++ MOORE MACHINE +++++++++++++ *//
 
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
	        if (stringLen > 0 && lastTransition.charAt(stringLen - 1) == '}' ) {
	        	transitions[num] = lastTransition.substring(0, stringLen - 1);
	        }
	        
	        if (stringLen > 0) {
	        for (int i = 0; i < transitions.length; i++) {
	        	String transition[] = transitions[i].split("(\\s)*:(\\s)*\\{");
	        	nextState = transition[0];
	        	String [] pairs = transition[1].split("\\,(\\s)+");
	        	for (int j = 0; j < pairs.length; j++) { 
	        	 if (pairs[j].length() == 1) {
	        	        if ( !input_alphabet.valid(pairs[j].charAt(0)) ) {
	        	        	System.out.println("FSM FILE ERROR: " + name + " : INVALID "
	        	        	+ "TRANSITION SYMBOL ON LINE " + String.valueOf(line + 1));
	        	        	return null;
	        	        }        	        
	        		inputChar = new Character(pairs[j].charAt(0));
	        		Pair<String, Character> strChr = new Pair<String, Character>(currentState, inputChar);
	        		transitionFunction.put(strChr, nextState);  		
	        		}
	        	 
	        	 if (pairs[j].length() >= 3) {
	        	  if (pairs[j].charAt(1) == '/' ) 
	        	  	System.out.println("FSM FILE ERROR: " + name + " : INVALID TRANSITION "
	        	  	+ "FOR MOORE ON LINE " + String.valueOf(line + 1));  
	        	  else
	        	  	System.out.println("FSM FILE ERROR: " + name + " : INVALID FORMATTING ON"
	        	  	+ " LINE " + String.valueOf(line + 1));
	        	  return null;
	        	 }
	        	 
	        	 if (pairs[j].length() == 2) {
	        	   if (pairs[j].charAt(0) != '|') {
	        	   	System.out.println("FSM FILE ERROR: " + name + " : INVALID FORMATTING ON "
	        	   	+ "LINE " + String.valueOf(line + 1));
	        	   	return null;
	        	   }
	        	   String shortcut = new String("");
	        	   if ( pairs[j].charAt(1) == 'd' ) shortcut = new String("0123456789");
	        	   if ( pairs[j].charAt(1) == 'n' ) shortcut = new String("123456789");
	        	   if ( pairs[j].charAt(1) == 'a' ) 
	        	    shortcut = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
	        	   if ( pairs[j].charAt(1) == 'l' )
	        	    shortcut = new String("abcdefghijklmnopqrstuvwxyz");
	        	   if ( pairs[j].charAt(1) == 'u' )
	        	    shortcut = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	        	   if ( pairs[j].charAt(1) == 's' )
	        	    shortcut = new String(".,~!@$#%^&-+{}");
	        	
	        	   if ( !input_alphabet.valid(shortcut) ) {
	        	    System.out.println("FSM FILE ERROR: " + name + " : INVALID TRANSITION "
	        	 	 	+ "SYMBOL ON LINE " + String.valueOf(line + 1));
	        	 	 	return null;
	        	 	}
	        	
	        	    
	        	   for (int x = 0; x < shortcut.length(); x++) {
	        	        
	        	        inputChar = new Character(shortcut.charAt(x));
	        	     
	        		Pair<String, Character> strChr = new Pair<String, Character>(currentState, inputChar);
	        		
	        		transitionFunction.put(strChr, nextState);
	        		
	        	   }
	        	   
	        	 }
	        	 
	        	 }
	        	 
	        }
	        
	        if (!finalStates.contains(currentState)) {
	        ArrayList< Character > deterministicCheck = new ArrayList<Character> ();
	        for (Pair<String, Character> key : transitionFunction.keySet() ){
	          if (key.getFirst().equals(currentState) ) {
	            if ( deterministicCheck.contains( key.getSecond() )) {
	              System.out.println("FSM FILE ERROR: " + name + " : NON-DETERMINISTIC TRANSITION " +
	              		"TABLE FOR STATE " + currentState);
	              	return null;
	            }
	            deterministicCheck.add(key.getSecond() );
	          }
	        }}}
	        
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
      Pattern p = Pattern.compile(":");
      for(int i =0; i < lines.length ;i++){
          //System.out.println(lines[i]);
      		Matcher m = p.matcher(lines[i]);
		if( m.find() ){
		   String[] line = lines[i].split(":");
		   if(line.length<2 || (line.length == 2 && line[1].trim().equals(""))) 
		    System.out.println( line[0] + " : " + 
		   		"MISSING INPUT STRING ON LINE " + String.valueOf(i+1) );
		   else if(line.length>2)
		    System.out.println( line[0] + " : " + 
		   		"MULTIPLE STRINGS ON LINE " + String.valueOf(i+1) );
		   else if( (line[0].trim()).equals("") )
		    System.out.println( line[0] + " : " + 
		   		"MISSING NAME  ON LINE " + String.valueOf(i+1) );
		   else{
		   line[1] = line[1].trim();
		   if( line[1].contains(" ") ) 
		   System.out.println( line[0] + " : " + 
		   		"MULTIPLE STRINGS ON LINE " + String.valueOf(i+1) );
		   else result.add(line[1]);
		   }
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
