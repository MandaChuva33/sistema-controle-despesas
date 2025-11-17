import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Interface para Polimorfismo
interface Pagavel {
    boolean isPago();
    void anotarPagamento(double valorPago);
}

// Classe de Modelo para Tipo de Despesa
class TipoDespesa {
    private int id;
    private String nome;

    public TipoDespesa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }

    @Override
    public String toString() {
        return id + ";" + nome;
    }

    public static TipoDespesa fromString(String linha) {
        String[] partes = linha.split(";");
        if (partes.length == 2) {
            try {
                int id = Integer.parseInt(partes[0]);
                return new TipoDespesa(id, partes[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}

// Classe de Modelo para Usuário
class Usuario {
    private int id;
    private String login;
    private String senhaCriptografada;

    public Usuario(int id, String login, String senhaCriptografada) {
        this.id = id;
        this.login = login;
        this.senhaCriptografada = senhaCriptografada;
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getSenhaCriptografada() { return senhaCriptografada; }

    @Override
    public String toString() {
        return id + ";" + login + ";" + senhaCriptografada;
    }

    public static Usuario fromString(String linha) {
        String[] partes = linha.split(";");
        if (partes.length == 3) {
            try {
                int id = Integer.parseInt(partes[0]);
                return new Usuario(id, partes[1], partes[2]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}

// Classe Abstrata para Herança
public abstract class Despesa implements Pagavel {
    private static int contadorDespesas = 0; // Atributo estático
    private int id;
    private String descricao;
    private double valor;
    private LocalDate dataVencimento;
    private TipoDespesa tipo;
    private double valorPago;
    private boolean paga;

    // Construtor sobrecarregado 1
    public Despesa(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
        this.id = ++contadorDespesas;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.valorPago = 0.0;
        this.paga = false;
    }

    // Construtor sobrecarregado 2 (para persistência)
    public Despesa(int id, String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo, double valorPago, boolean paga) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.valorPago = valorPago;
        this.paga = paga;
        if (id > contadorDespesas) {
            contadorDespesas = id; // Ajusta o contador estático
        }
    }

    // Método estático
    public static int getContadorDespesas() {
        return contadorDespesas;
    }

    // Implementação da interface Pagavel
    @Override
    public boolean isPago() {
        return paga;
    }

    @Override
    public void anotarPagamento(double valorPago) {
        this.valorPago += valorPago;
        if (this.valorPago >= this.valor) {
            this.paga = true;
        }
    }

    // Método abstrato para sobrescrita
    public abstract String getDetalheTipo();

    // Getters
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public TipoDespesa getTipo() { return tipo; }
    public double getValorPago() { return valorPago; }

    // Sobrescrita do método toString para persistência
    @Override
    public String toString() {
        return String.format("%d;%s;%.2f;%s;%d;%.2f;%b",
                id, descricao, valor, dataVencimento.format(DateTimeFormatter.ISO_DATE),
                tipo.getId(), valorPago, paga);
    }
}

// Classe Concreta para Herança e Sobrescrita
class DespesaAlimentacao extends Despesa {
    // Sobrescrita do construtor 1
    public DespesaAlimentacao(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
        super(descricao, valor, dataVencimento, tipo);
    }

    // Sobrescrita do construtor 2 (para persistência)
    public DespesaAlimentacao(int id, String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo, double valorPago, boolean paga) {
        super(id, descricao, valor, dataVencimento, tipo, valorPago, paga);
    }

    // Implementação do método abstrato
    @Override
    public String getDetalheTipo() {
        return "Despesa de Alimentação: Essencial.";
    }
}
