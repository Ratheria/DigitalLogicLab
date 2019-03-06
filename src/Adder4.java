/*
 * Ariana Fairbanks
 */

import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.gates.*; 

public class Adder4 {

    public final InputMultiLine A = new InputMultiLine(4);
    public final InputMultiLine B = new InputMultiLine(4);
    public final InputLine Cin = new InputLine();
    
    public final MultiLine SUM;
    public final Line Cout;
    
    public Adder4() 
    {

    	FullAdder a0 = new FullAdder();
    	a0.A.connect(A.getLine(0));
    	a0.B.connect(B.getLine(0));
    	a0.Cin.connect(Cin);
    	
    	FullAdder a1 = new FullAdder();
    	a1.A.connect(A.getLine(1));
    	a1.B.connect(B.getLine(1));
    	a1.Cin.connect(a0.Cout);
    	
    	FullAdder a2 = new FullAdder();
    	a2.A.connect(A.getLine(2));
    	a2.B.connect(B.getLine(2));
    	a2.Cin.connect(a1.Cout);
    	
    	FullAdder a3 = new FullAdder();
    	a3.A.connect(A.getLine(3));
    	a3.B.connect(B.getLine(3));
    	a3.Cin.connect(a2.Cout);
    	
    
        SUM = MultiLine.of(a0.SUM, a1.SUM, a2.SUM, a3.SUM);

        Cout = a3.Cout;   // one of the gates you created above
    }
}