/* Alphabet.java */

import java.util.*;
import java.io.*;

//david is driving
public class Alphabet{
        private String characters;
        private static String defA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,~!@$#%^&-+{}";

        public Alphabet() {
           characters = new String(defA);
        }

        public Alphabet(String validAlphabet) {
	  characters = new String(validAlphabet);  
	  characters = characters.replace("|a", "|l|u");
	  characters = characters.replace("|u", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	  characters = characters.replace("|l", "abcdefghijklmnopqrstuvwxyz");
	  characters = characters.replace("|n", "123456789");
	  characters = characters.replace("|d", "0123456789");
	  characters = characters.replace("|s", ".,~!@$#%^&-+{}");
	}
	
	public boolean validAlphabet() {
	  if ( characters.length() < 1 ) return false;
	  for (int i = 0; i < characters.length(); i++) {
	    char testChar = characters.charAt(i);
	    if ( !defA.contains(String.valueOf(testChar)) ) return false;
	    
	    for (int j = i + 1; j < characters.length(); j++) {
	      if ( testChar == characters.charAt(j) ) return false;
	    }
	  }
	  return true;
	}

        public boolean valid(char testChar) {
	  return characters.contains(String.valueOf(testChar)) && validAlphabet();
        }
        
        public boolean valid(String testString) {
          for (int i = 0; i < testString.length(); i++) {
           if (!valid(testString.charAt(i))) return false;
          }
          return true;
        }

	public String toString(){
	    return characters;
	}

	public static void main(String[] args) {
	/*  Alphabet test1 = new Alphabet();
          Alphabet test2 = new Alphabet("|d");
          Alphabet test3 = new Alphabet("01");

          System.out.println(test1.valid('c'));
	  System.out.println(test1.valid('|'));
          System.out.println(test1.valid('1'));
          System.out.println(test2.valid('c'));
          System.out.println(test2.valid('1'));
          System.out.println(test2.valid('5'));
	  System.out.println(test3.valid('c'));
	  System.out.println(test3.valid('1'));
	  System.out.println(test3.valid('5')); */
                
	}
	//end main
}
//end Alphabet
