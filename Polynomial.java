public class Polynomial {
	
	double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[]{0};
	}

	public Polynomial(double[] array) {
		coefficients = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			coefficients[i] = array[i];
		}
	}

	public Polynomial add(Polynomial polynomial) {
		double[] arr = new double[Math.max(coefficients.length, polynomial.coefficients.length)];
		for (int i = 0; i < Math.max(coefficients.length, polynomial.coefficients.length); i++) {
			if (i < polynomial.coefficients.length && i < coefficients.length) {
				arr[i] = polynomial.coefficients[i] + coefficients[i];
			}
			else if (i < polynomial.coefficients.length) {
				arr[i] = polynomial.coefficients[i];
			}
			else if (i < coefficients.length) {
				arr[i] = coefficients[i];
			}
		}
		Polynomial new_poly = new Polynomial(arr);
		return new_poly;
	}
		
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) { 
			result += coefficients[i]*Math.pow(x, i);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) { 
			result += coefficients[i]*Math.pow(x, i);
		}
		return result == 0;
	}
}
