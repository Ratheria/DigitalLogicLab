/*
 * Ariana Fairbanks
 */

import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.gates.*;

public class Adder16 {

    public final InputMultiLine A = new InputMultiLine(16);
    public final InputMultiLine B = new InputMultiLine(16);
    public final InputLine Cin = new InputLine();
    
    public final MultiLine SUM;
    public final Line Cout;
    
    public Adder16() 
    {

    	Adder4 a0 = new Adder4();
    	a0.A.connectAll(A.lineRange(0, 4));
    	a0.B.connectAll(B.lineRange(0, 4));
    	a0.Cin.connect(Cin);
    	
    	Adder4 a1 = new Adder4();
    	a1.A.connectAll(A.lineRange(4, 8));
    	a1.B.connectAll(B.lineRange(4, 8));
    	a1.Cin.connect(a0.Cout);
    	
    	Adder4 a2 = new Adder4();
    	a2.A.connectAll(A.lineRange(8, 12));
    	a2.B.connectAll(B.lineRange(8, 12));
    	a2.Cin.connect(a1.Cout);
    	
    	Adder4 a3 = new Adder4();
    	a3.A.connectAll(A.lineRange(12, 16));
    	a3.B.connectAll(B.lineRange(12, 16));
    	a3.Cin.connect(a2.Cout);
    	
    
        SUM = MultiLine.join(a0.SUM, a1.SUM, a2.SUM, a3.SUM);

        Cout = a3.Cout;   // one of the gates you created above
    }
}