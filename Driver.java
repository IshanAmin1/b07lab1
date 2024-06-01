import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Driver {
	public static void main(String [] args) throws IOException {
		
		double[] coefficients1 = {-1,-1};
        int[] exponents1 = {1,0};
        Polynomial poly1 = new Polynomial(coefficients1, exponents1);
        
        double[] coefficients2 = {1,1};
        int[] exponents2 = {0,1};
        Polynomial poly2 = new Polynomial(coefficients2, exponents2);
        
        Polynomial result = poly1.add(poly2);
        
        System.out.println("Resulting coefficients: " + Arrays.toString(result.coefficients));
        System.out.println("Resulting exponents: " + Arrays.toString(result.exponents));
        
       System.out.println("Result: " + result.evaluate(-2));
       System.out.println("Has Root: " + result.hasRoot(-1));
        
        Polynomial result2 = poly1.multiply(poly2);

        System.out.println("Resulting coefficients: " + Arrays.toString(result2.coefficients));
        System.out.println("Resulting exponents: " + Arrays.toString(result2.exponents));
        
        File Obj = new File("C:\\Users\\ishan\\Downloads\\Data.txt");
        Polynomial new_poly = new Polynomial(Obj);
        System.out.println("Resulting coefficients: " + Arrays.toString(new_poly.coefficients));
        System.out.println("Resulting exponents: " + Arrays.toString(new_poly.exponents));
        
        result2.saveToFile("C:\\Users\\ishan\\Downloads\\DataWrite.txt");
	}
}