/*
 * Ariana Fairbanks
 */

import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.gates.*;

public class Adder64
{

    public final InputMultiLine A = new InputMultiLine(64);
    public final InputMultiLine B = new InputMultiLine(64);
    public final InputLine Cin = new InputLine();
    
    public final MultiLine SUM;
    public final Line Cout;
    
    public Adder64() 
    {

    	Adder16 a0 = new Adder16();
    	a0.A.connectAll(A.lineRange(0, 16));
    	a0.B.connectAll(B.lineRange(0, 16));
    	a0.Cin.connect(Cin);
    	
    	Adder16 a1 = new Adder16();
    	a1.A.connectAll(A.lineRange(16, 32));
    	a1.B.connectAll(B.lineRange(16, 32));
    	a1.Cin.connect(a0.Cout);
    	
    	Adder16 a2 = new Adder16();
    	a2.A.connectAll(A.lineRange(32, 48));
    	a2.B.connectAll(B.lineRange(32, 48));
    	a2.Cin.connect(a1.Cout);
    	
    	Adder16 a3 = new Adder16();
    	a3.A.connectAll(A.lineRange(48, 64));
    	a3.B.connectAll(B.lineRange(48, 64));
    	a3.Cin.connect(a2.Cout);
    	
    
        SUM = MultiLine.join(a0.SUM, a1.SUM, a2.SUM, a3.SUM);

        Cout = a3.Cout;   // one of the gates you created above
    }
}