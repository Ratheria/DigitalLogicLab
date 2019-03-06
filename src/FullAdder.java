/*
 * Ariana Fairbanks
 */

import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.gates.*;

public class FullAdder {

    // Inputs to the circuit: InputLines
    public final InputLine A = new InputLine();
    public final InputLine B = new InputLine();
    public final InputLine Cin = new InputLine();
    
    // Outputs of the circuit: Lines (initialize in constructor)
    public final Line SUM;
    public final Line Cout;
    
    public FullAdder() 
    {
    	HalfAdder abAdder = new HalfAdder();
    	abAdder.A.connect(A);
    	abAdder.B.connect(B);
    	
    	HalfAdder rcAdder = new HalfAdder();
    	rcAdder.A.connect(abAdder.SUM);
    	rcAdder.B.connect(Cin);
    	
    	OrGate or = new OrGate();
    	or.A.connect(abAdder.C);
    	or.B.connect(rcAdder.C);
    	
    
        SUM = rcAdder.SUM; 
        Cout = or;   
    }
}