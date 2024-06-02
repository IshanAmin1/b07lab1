import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Polynomial {
	
	double[] coefficients;
	int[] exponents;
	
	public Polynomial() {
		coefficients = null;
		exponents = null;
	}

	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = new double[coefficients.length];
		for (int i = 0; i < coefficients.length; i++) {
			this.coefficients[i] = coefficients[i];
		}
		
		this.exponents = new int[exponents.length];
		for (int i = 0; i < exponents.length; i++) {
			this.exponents[i] = exponents[i];
		}
	}
	
	public Polynomial(File file) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(file));
		String data = input.readLine();
		input.close();
		
		String[] arrOfValues = data.split("(?=[-+])");
		this.coefficients = new double[arrOfValues.length];
		this.exponents = new int[arrOfValues.length];
		
		for (int i = 0; i < arrOfValues.length; i++) {
			String[] arr = arrOfValues[i].split("x");
			
			if (arrOfValues[i].contains("x") == false) {
				this.coefficients[i] = Double.parseDouble(arr[0]);
				this.exponents[i] = 0;
				}
			
            else {
                this.coefficients[i] = Double.parseDouble(arr[0]);
                this.exponents[i] = Integer.parseInt(arr[1]);
				}
			}
		
		Polynomial poly = new Polynomial(coefficients, exponents);
		poly = removeZeroCoeff(poly);
		
		this.coefficients = poly.coefficients;
		this.exponents = poly.exponents;
		
	}
	
	private boolean isPresent(int arr[], int num) {	
		for (int i = 0; i < arr.length; i++) {
			if (num == arr[i]){
				return true;
			}
		}
		return false;
	}
	
	private int isPresentIndex(int arr[], int num) {
		for (int i = 0; i < arr.length; i++) {
			if (num == arr[i]){
				return i;
			}
		}
		return -1;
	}
	
	private int[] mergeExponentArray(Polynomial polynomial) {	
		int[] tempArr = new int[polynomial.exponents.length + exponents.length];
		int size = 0;
		
		if (isPresent(exponents, 0) || isPresent(polynomial.exponents, 0)) {
			tempArr[size++] = 0;
		}
		
		for (int i = 0; i < polynomial.exponents.length; i++) {
			if (isPresent(tempArr, polynomial.exponents[i]) == false) {
				tempArr[size++] = polynomial.exponents[i];
			}
		}

		for (int i = 0; i < exponents.length; i++) {
			if (isPresent(tempArr, exponents[i]) == false) {
				tempArr[size++] = exponents[i];
			}
		}
			
		int[] mergedExponents = new int[size];
		for (int i = 0; i < size; i++) {
			mergedExponents[i] = tempArr[i];
		}
		
		return mergedExponents;
	}
	
	private Polynomial removeZeroCoeff(Polynomial polynomial) {
		int numZeroCoeff = numZeros(polynomial);
		int[] new_exp = new int[polynomial.coefficients.length - numZeroCoeff];
		double[] new_coeff = new double[polynomial.coefficients.length - numZeroCoeff];
		
		int k = 0;
		for (int i = 0; i < polynomial.coefficients.length; i++) {
			if (polynomial.coefficients[i] != 0) {
				new_coeff[k] = polynomial.coefficients[i];
				new_exp[k] = polynomial.exponents[i];
				k++;
			}
		}
		if (new_coeff.length == 0) {
			return new Polynomial();
		}
		else {
			Polynomial multi_poly = new Polynomial(new_coeff, new_exp);
			return multi_poly;
		}
	}
	
	public Polynomial add(Polynomial polynomial) {
		if (this.exponents == null && polynomial.exponents == null) {
			return new Polynomial();
		}
		
		if (this.exponents == null) {
			return new Polynomial(polynomial.coefficients, polynomial.exponents);
		}
		
		if (polynomial.exponents == null) {
			return new Polynomial(this.coefficients, this.exponents);
		}
		
		int[] new_exp = mergeExponentArray(polynomial);
		double [] new_coefficients = new double[new_exp.length];
		for (int i = 0; i < new_exp.length; i++) {
            int indexInThis = isPresentIndex(this.exponents, new_exp[i]);
            if (indexInThis != -1) {
                new_coefficients[i] += this.coefficients[indexInThis];
            }
            int indexInPolynomial = isPresentIndex(polynomial.exponents, new_exp[i]);
            if (indexInPolynomial != -1) {
                new_coefficients[i] += polynomial.coefficients[indexInPolynomial];
            }
        }
		
		Polynomial new_poly = new Polynomial(new_coefficients, new_exp);
		new_poly = removeZeroCoeff(new_poly);
		return new_poly;
	}
	
	public double evaluate(double x) {
		double result = 0;
		if (coefficients == null || exponents == null) {
			return result;
		}
		for (int i = 0; i < coefficients.length; i++) { 
			result += coefficients[i]*Math.pow(x,exponents[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		double result = evaluate(x);
		return result == 0;
	}
	
	private int numZeros(Polynomial poly) {
		int counter = 0;
		for (int k = 0; k < poly.coefficients.length; k++) {
			if (poly.coefficients[k] == 0) {
				counter++;
			}
		}
		return counter;
	}
	
	public Polynomial multiply(Polynomial polynomial){
		if (this.exponents == null || polynomial.exponents == null) {
			return new Polynomial();
		}
		
		Polynomial new_poly = new Polynomial();
		int[] temp_exp = new int[Math.max(polynomial.exponents.length, exponents.length)];
		double[] temp_coefficients = new double[Math.max(polynomial.exponents.length, exponents.length)];
		
		for (int i = 0; i < exponents.length; i++){
			for (int j = 0; j < polynomial.exponents.length; j++) {
				temp_exp[j] = exponents[i] + polynomial.exponents[j];
				temp_coefficients[j] = coefficients[i]*polynomial.coefficients[j];
				}
			Polynomial temp_poly = new Polynomial(temp_coefficients, temp_exp);
			new_poly = new_poly.add(temp_poly);
		}
		
		Polynomial poly = removeZeroCoeff(new_poly);
		return poly;
	}
	
	public void saveToFile(String filename) throws FileNotFoundException {
		PrintStream output = new PrintStream(filename);
		if (this.coefficients == null) {
			output.print(0);
		}
		else {
			for (int i = 0; i < this.coefficients.length; i++) {
				if (this.exponents[i] == 0) {
					if (this.coefficients[i] > 0 && i != 0) {
						output.print("+");
						output.print(this.coefficients[i]);
					}
					
					else {
					output.print(this.coefficients[i]);
					}
				}
			
				else {	
					if (this.coefficients[i] > 0 && i != 0) {
					output.print("+");
					output.print(this.coefficients[i]);
					output.print("x");
					output.print(this.exponents[i]);
					
					}
					
					else {
						output.print(this.coefficients[i]);
						output.print("x");
						output.print(this.exponents[i]);
					}
				}
			}
		}
		output.close();
	}
}
	
	
	
