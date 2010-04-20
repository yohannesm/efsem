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
   
   public String toString(){
    StringBuffer result = new StringBuffer();
    result.append("( ");
    result.append(first.toString() );
    result.append(", ");
    result.append(second.toString() );
    result.append(")");

    return result.toString();
   
   }

   public boolean equals(Pair<T, S> other){
       return ((first.equals(other.getFirst()))  && (second.equals(other.getSecond())));
   }
}// end Pair Class
