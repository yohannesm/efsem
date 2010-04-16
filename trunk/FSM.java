import java.util.*;
import java.io.*;

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

public static Machine parseMachine(String input){
   String[] lines = input.split("\n");

   for(int i = 0; i<lines.length; i++){
         System.out.println(i);
         System.out.println(lines[i]);
   }
 

return null;
}//end parseMachine

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
