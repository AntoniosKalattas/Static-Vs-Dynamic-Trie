import java.util.Random;
public class ShiftedPoissonGenerator {  //object meant to simulate our distribution
    private final double lambda; // Mean of the distribution
    private final Random rand;

    public ShiftedPoissonGenerator(double lambda) {
        this.lambda = lambda;
        this.rand = new Random();
    }

    // Generate a single shifted Poisson value
    public int nextShiftedPoisson() {
        double L = Math.exp(-lambda); // e^(-lambda)
        int k = 0;
        double p = 1.0;

        do {
            k++;
            p *= rand.nextDouble(); // Multiply by a random number [0,1)
        } while (p > L);

        return k; // Shifted Poisson: starts at 1
    }

    public static void main(String[] args) {
        ShiftedPoissonGenerator generator = new ShiftedPoissonGenerator(6.94); // Mean word length
        
        // Generate 20 word lengths
        for (int i = 0; i < 2000; i++) {
            System.out.println(generator.nextShiftedPoisson());
        }
    }
}

