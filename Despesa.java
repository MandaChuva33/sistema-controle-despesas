/**
 * Classe modelo para representar uma Transação Financeira (Despesa ou Receita)
 * Versão: 1.0.0
 */
public class Despesa {
    private static int contadorId = 1;
    
    private int id;
    private String descricao;
    private double valor;
    private String categoria;
    private boolean paga;
    private String dataVencimento; // Novo campo para data de vencimento

    // Construtor completo
    public Despesa(String descricao, double valor, String categoria, String dataVencimento) {
        this.id = contadorId++;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.paga = false;
        this.dataVencimento = dataVencimento;
    }

    // Construtor sobrecarregado (sem categoria e data)
    public Despesa(String descricao, double valor) {
        this(descricao, valor, "Geral", "N/A");
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    // Método para exibir informações da despesa
    @Override
    public String toString() {
        String status = paga ? "PAGA" : "PENDENTE";
        return String.format("ID: %d | Descrição: %s | Valor: R$ %.2f | Categoria: %s | Vencimento: %s | Status: %s",
                id, descricao, valor, categoria, dataVencimento, status);
    }

    // Método estático para obter total de despesas criadas
    public static int getTotalDespesas() {
        return contadorId - 1;
    }
}
