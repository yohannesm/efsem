NAMES: David T. Kraft  Yohannes M. Himawan
UT EIDS: dtk265 ymh79
SECTIONS: 54195 54185

CODING STATUS: Working perfectly, given our assumptions on the input

All code compiles successfully. When we run FSM on our machine inputs, they all work correctly
and give correct output. Testing by hand reveals that most FSM file errors will be caught by
our parser and reported.

Our code currently makes a few assumptions about the FSM input file:

 * Inside the state transition brackets {}, we assume that that every comma separating a transition
   has one or more white space characters. We also assume these are the only white spaces in
   the brackets.
 * We assume that a closing } bracket for the state transitions will be followed by one or more
   white space characters except at the end of the line.
 * State names consist only of alpha-numeric 

Our pair programming log is in comments at the beginning of FSM.java.
