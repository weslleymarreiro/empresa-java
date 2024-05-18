import java.time.YearMonth;
import java.util.List;

// Interface Funcionario
interface Funcionario {
    double calcularSalario(int ano, int mes);
    String getNome();
}

// Enum para representar os cargos
enum Cargo {
    SECRETARIO,
    VENDEDOR,
    GERENTE
}

// Enum para representar os benefícios
enum Beneficio {
    SECRETARIO(1000.00, 0.20),
    VENDEDOR(1800.00, 0.30),
    GERENTE(3000.00, 0.0); // Gerente não tem benefícios

    private final double valorAnual;
    private final double percentualVendas;

    Beneficio(double valorAnual, double percentualVendas) {
        this.valorAnual = valorAnual;
        this.percentualVendas = percentualVendas;
    }

    public double getValorAnual() {
        return valorAnual;
    }

    public double getPercentualVendas() {
        return percentualVendas;
    }
}

// Classe FuncionarioImpl para implementar Funcionario
class FuncionarioImpl implements Funcionario {
    private final String nome;
    private final Cargo cargo;
    private final YearMonth dataContratacao;
    private final double salarioBase;

    public FuncionarioImpl(String nome, Cargo cargo, YearMonth dataContratacao, double salarioBase) {
        this.nome = nome;
        this.cargo = cargo;
        this.dataContratacao = dataContratacao;
        this.salarioBase = salarioBase;
    }

    @SuppressWarnings("unused")
    @Override
    public double calcularSalario(int ano, int mes) {
        double anosDeServico = calcularAnosDeServico(ano, mes);
        double beneficioAnual = cargo == Cargo.GERENTE ? 0 : cargo == Cargo.VENDEDOR ? Beneficio.VENDEDOR.getValorAnual() : Beneficio.SECRETARIO.getValorAnual();
        double beneficio = cargo == Cargo.VENDEDOR ? Beneficio.VENDEDOR.getPercentualVendas() * calcularVendas(mes) : cargo == Cargo.SECRETARIO ? salarioBase * Beneficio.SECRETARIO.getPercentualVendas() : 0;
        return salarioBase + beneficioAnual + beneficio;
    }

    @Override
    public String getNome() {
        return nome;
    }

    private double calcularAnosDeServico(int ano, int mes) {
        YearMonth dataAtual = YearMonth.of(ano, mes);
        return dataAtual.getYear() - dataContratacao.getYear() +
                (dataAtual.getMonthValue() >= dataContratacao.getMonthValue() ? 0 : -1);
    }

    private double calcularVendas(int mes) {
        return 0; // Método a ser implementado na classe Vendedor
    }
}

// Classe Vendedor
class Vendedor extends FuncionarioImpl {
    private final double[] vendasMensais;

    public Vendedor(String nome, YearMonth dataContratacao, double[] vendasMensais) {
        super(nome, Cargo.VENDEDOR, dataContratacao, 12000.00);
        this.vendasMensais = vendasMensais;
    }

   
    public double calcularVendas(int mes) {
        return vendasMensais[mes - 1]; // Índice do mês - 1 (0 baseado)
    }
}

// Classe Empresa
class Empresa {
    public double calcularTotalPago(List<Funcionario> funcionarios, int ano, int mes) {
        return funcionarios.stream().mapToDouble(f -> f.calcularSalario(ano, mes)).sum();
    }

    // Outros métodos aqui...
}

// Classe Main
public class Main {
    public static void main(String[] args) {
        // Criação de funcionários e chamadas aos métodos da Empresa
        // ...
    }
}


//RODA CODIGO "java Main".

//Obrigado pela oportunidade que então me dando, espero ser aceito na empresa.