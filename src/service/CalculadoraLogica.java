package service;
import java.lang.Math;

public class CalculadoraLogica {

// basico
    public double somar(double a, double b) {
        return a + b;
    }
    public double subtrair(double a, double b) {
        return a - b;
    }
    public double multiplicar(double a, double b) {
        return a * b;
    }
    public double dividir(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisão por zero não é permitida.");
        }
        return a / b;
    }

// mais avançado== potencia, raiz quadrada , logaritmo, trigonometria
    public double potencia(double base, double expoente) {
        return Math.pow(base, expoente);
    }
    public double raizQuadrada(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("Raiz quadrada de número negativo não é permitida.");
        }
        return Math.sqrt(a);
    }

    public double logaritmo(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException("Logaritmo de número não positivo não é permitido.");
        }
        return Math.log(a);
    }

    public double seno(double a) {
        return Math.sin(Math.toRadians(a));
    }

    public double cosseno(double a) {
        return Math.cos(Math.toRadians(a));
    }

    public double tangente(double a) {
        return Math.tan(Math.toRadians(a));
    }



    






    
}
