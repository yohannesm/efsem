import java.util.*;
import java.io.*;

public abstract class Machine{
     public static boolean verbose = false;
     public static boolean warning = false;
     public static boolean utt = false;
     protected String name = null;

     public abstract String run(String input);
     
     public abstract void reset();
     

     public String name(){
           return name;
     }
}


