/* Pair.java */

import java.util.*;
import java.io.*;

//Marcell is driving
public class Pair<T, S>{

   private T first;
   private S second;

   public Pair(T a, S b){
     first = a;
     second = b;
   }

   public T getFirst(){
    return first;
   }

   public S getSecond(){
    return second;
   }

}// end Pair Class
