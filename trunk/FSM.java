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

   System.out.println(machineType);
   System.out.println(start_state);
   System.out.println(lines[7]);

return null;
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
