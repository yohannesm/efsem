import java.util.*;
import java.io.*;

public abstract class Machine{
     public static boolean verbose = false;
     public static boolean warning = false;
     public static boolean utt = false;
     protected String name = null;

     public abstract String run(String input);
     
     public abstract void reset();
     
     public abstract String getMachineType();
     public abstract String getOutput();
     public abstract Alphabet getInputAlphabet();
     public abstract String getStartState();
     

     public String name(){
           return name;
     }
}


