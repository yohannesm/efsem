/* alphabet.java */

import java.util.*;
import java.io.*;

//david is driving
public class alphabet{
        private String characters;

        public alphabet() {
           characters = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,~!@$#%^&-+{}.");
        }

        public alphabet(String validAlphabet) {
	  if (validAlphabet == "|u") { characters = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ"); }
          else if (validAlphabet == "|l") { characters = new String("abcdefghijklmnopqrstuvwxyz"); }
          else if (validAlphabet == "|a") { characters = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"); }
	  else if (validAlphabet == "|d") { characters = new String("0123456789"); }
          else if (validAlphabet == "|n") { characters = new String("123456789"); }
          else if (validAlphabet == "|s") { characters = new String(".,~!@$#%^&-+{}."); }
          else { characters = new String(validAlphabet);}
	}

        public boolean valid(char testChar) {
	  return characters.contains(String.valueOf(testChar));
        }

	public static void main(String[] args) {
	  alphabet test1 = new alphabet();
          alphabet test2 = new alphabet("|d");
          alphabet test3 = new alphabet("01");

          System.out.println(test1.valid('c'));
	  System.out.println(test1.valid('|'));
          System.out.println(test1.valid('1'));
          System.out.println(test2.valid('c'));
          System.out.println(test2.valid('1'));
          System.out.println(test2.valid('5'));
	  System.out.println(test3.valid('c'));
	  System.out.println(test3.valid('1'));
	  System.out.println(test3.valid('5'));

	}//end main
}//end alphabet
