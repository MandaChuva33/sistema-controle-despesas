import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    // Construtor sobrecarregado 2
    public Despesa(String descricao, double valor, String dataVencimentoStr, TipoDespesa tipo) {
        this(descricao, valor, LocalDate.parse(dataVencimentoStr, DateTimeFormatter.ISO_DATE), tipo);
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
