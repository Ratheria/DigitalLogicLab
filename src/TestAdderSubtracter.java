import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.ic.*;
import static edu.westminstercollege.cmpt328.logic.Line.*;

import java.math.BigInteger;
import java.util.Random;

/*
 Rewrite of the vanilla 64bit Adder test class that tests both addition and subtraction for the Adder-Subtracter.
 */


public class TestAdderSubtracter 
{
	private static boolean print = true;
    private static final int TEST_COUNT = 50000;
    private static final AdderSubtracter adder = new AdderSubtracter();
    private static final BigInteger MAX_UNSIGNED_LONG = BigInteger.valueOf(Long.MAX_VALUE).shiftLeft(1).add(BigInteger.ONE);

    public static void main(String... args) 
    {
        NumberFormat format = NumberFormat.twosComplement(64);
        NumberSource sourceA = new NumberSource(format);
        NumberSource sourceB = new NumberSource(format);
        adder.A.connectAll(sourceA);
        adder.B.connectAll(sourceB);

        NumberDisplay displaySUM = new NumberDisplay(format);
        displaySUM.IN.connectAll(adder.SUM);

        Random random = new Random();

        System.out.printf("Running %d test cases selected at random....\n", TEST_COUNT);
        int passes = 0, failures = 0;
        for (int i = 0; i < TEST_COUNT; ++i) 
        {
            long A = format.random(random).longValue(),
                 B = format.random(random).longValue(),
                 Cin = random.nextInt(2);
            sourceA.setValue(A);
            sourceB.setValue(B);
            adder.SUB.connect((Cin == 0) ? GROUND : CURRENT);

            long expectedSum = ((Cin == 0) ? (A + B + Cin) : (A - B));
            String operation = ((Cin == 0) ? ("+") : ("-"));
            Bit expectedCout = Bit.of(expectOverflow(A, B, Cin, format));
            long sum = displaySUM.getValue();
            Bit Cout = adder.Cout.getState();
           
            boolean pass = (sum == expectedSum && Cout == expectedCout);
            if (pass)
            {
            	++passes;
            }
            else
            {
            	++failures;
            }
            
            if(print)
            {
                System.out.printf("Case %5d/%5d: %,28d " + operation + " %,28d = %,28d C %s   " + pass + ": expected %,28d C %s\n",
                        i + 1, TEST_COUNT,
                        A, B, sum, Cout,
                        expectedSum, expectedCout);
            }
            
            if (failures >= 50) 
            {
                System.out.println("\n50 test cases have failed - bailing out.");
                break;
            }
        }

        if (passes + failures == TEST_COUNT)
        {
        	System.out.printf("\n%d of %d cases passed!\n", passes, passes + failures);
        }
        
    }

    private static boolean expectOverflow(long A, long B, long Cin, NumberFormat format) 
    {
        BigInteger bigA = unsigned(A), bigB = unsigned(B);
        BigInteger sum;
        boolean minusWillHaveCarryOut = false;
        if(Cin == 0)
        {
        	sum = bigA.add(bigB).add(BigInteger.valueOf(Cin));
        }
        else
        {
        	sum = bigA.subtract(bigB);
        	boolean aPositive = A >= 0;
        	boolean bPositive = B >= 0;
        	boolean resultPositive = A - B >= 0;
        	minusWillHaveCarryOut = (aPositive && bPositive && resultPositive)
        						 || (!aPositive && bPositive && resultPositive)
        						 || (!aPositive && bPositive && !resultPositive)
        						 || (!aPositive && !bPositive && resultPositive);
        }
        return sum.compareTo(MAX_UNSIGNED_LONG) > 0 || minusWillHaveCarryOut;
    }

    private static BigInteger unsigned(long x) 
    {
        if (x < 0)
            return BigInteger.valueOf(x & Long.MAX_VALUE).setBit(Long.SIZE - 1);
        else
            return BigInteger.valueOf(x);
    }
}
