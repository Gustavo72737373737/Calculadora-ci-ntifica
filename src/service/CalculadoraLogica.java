package service;
import java.lang.Math;
import java.util.function.DoubleUnaryOperator;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

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



    // Operador de fatorial (n!) para o exp4j
    private final Operator fatorialOp = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
        @Override
        public double apply(double... args) {
            int n = (int) args[0];
            if ((double) n != args[0] || n < 0) {
                throw new IllegalArgumentException("Fatorial exige inteiro >= 0");
            }
            double r = 1;
            for (int i = 2; i <= n; i++) r *= i;
            return r;
        }
    };

    // Funções trigonométricas em graus
    private Function funcGraus(String nome, DoubleUnaryOperator op, boolean inversa) {
        return new Function(nome, 1) {
            @Override
            public double apply(double... args) {
                return inversa ? Math.toDegrees(op.applyAsDouble(args[0]))
                               : op.applyAsDouble(Math.toRadians(args[0]));
            }
        };
    }

    // Calcula a expressão digitada no visor
    public double calcular(String expressaoVisor, boolean modoGraus) {
        String expr = expressaoVisor.replace(",", ".");
        ExpressionBuilder builder;

        if (modoGraus) {
            expr = expr.replaceAll("\\basin\\(", "asind(")
                       .replaceAll("\\bacos\\(", "acosd(")
                       .replaceAll("\\batan\\(", "atand(")
                       .replaceAll("\\bsin\\(", "sind(")
                       .replaceAll("\\bcos\\(", "cosd(")
                       .replaceAll("\\btan\\(", "tand(");
            builder = new ExpressionBuilder(expr)
                    .operator(fatorialOp)
                    .function(funcGraus("sind", Math::sin, false))
                    .function(funcGraus("cosd", Math::cos, false))
                    .function(funcGraus("tand", Math::tan, false))
                    .function(funcGraus("asind", Math::asin, true))
                    .function(funcGraus("acosd", Math::acos, true))
                    .function(funcGraus("atand", Math::atan, true));
        } else {
            builder = new ExpressionBuilder(expr).operator(fatorialOp);
        }

        double r = builder.build().evaluate();
        if (Double.isNaN(r) || Double.isInfinite(r)) {
            throw new ArithmeticException("Resultado inválido");
        }
        return Math.round(r * 1e10) / 1e10; // evita lixo de ponto flutuante
    }

    // Formata o resultado para o visor (vírgula como separador)
    public String formatar(double r) {
        if (r == (long) r) return String.format("%d", (long) r);
        return String.valueOf(r).replace('.', ',');
    }



    






    
}
