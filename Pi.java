import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Pi {
	public static void main(String args []){
		if (args.length != 2)
		{
			System.out.println("Incorrect arguments. Use 'java Pi <long nThreads> <long nIterations>");
			System.exit(1);
		}

		final long nThreads = Long.parseLong(args[0]);
		final long nIterations = Long.parseLong(args[1]);

		final long [] numHits = new long[(int) nThreads];
		Thread[] ts = new Thread[(int) nThreads];

		for (int i = 0; i < nThreads; i++){
			final int fi = i;

			ts[i] = new Thread(() ->
			{
				numHits[fi] = calculatePi(nIterations/nThreads);
			});
		}

		try{
			for (Thread t : ts) t.start();

			for (Thread t : ts) t.join();
		} catch (InterruptedException iex){}

		long totalHits = 0;
		for (int i = 0; i < nThreads; i++)
			totalHits += numHits[i];

		System.out.println("Total: " + nIterations);
		System.out.println("Inside: " + totalHits);
		System.out.println("Ratio: " + (float)totalHits/nIterations);
		System.out.println("Pi: " + ((float)totalHits/nIterations) * 4);
	}

	public static long calculatePi(long nIterations){
		
		long nHits = 0;
		long upper = 10000;
		for (int i = 0; i < nIterations; i++){
			long x = ThreadLocalRandom.current().nextLong(0,upper);
			long y = ThreadLocalRandom.current().nextLong(0,upper);
			float result = ((float)x/upper)*((float)x/upper) + ((float)y/upper)*((float)y/upper);

			//System.out.println(result);
			
			//System.out.println(y);

			if (result < (float)1)
				nHits++;
		}

		return nHits;

	}
}