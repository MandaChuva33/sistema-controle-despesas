import java.time.LocalDate;

public class DespesaAlimentacao extends Despesa {

    // Sobrescrita do construtor
    public DespesaAlimentacao(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
        super(descricao, valor, dataVencimento, tipo);
    }

    // Sobrescrita do construtor sobrecarregado
    public DespesaAlimentacao(String descricao, double valor, String dataVencimentoStr, TipoDespesa tipo) {
        super(descricao, valor, dataVencimentoStr, tipo);
    }

    // Implementação do método abstrato
    @Override
    public String getDetalheTipo() {
        return "Despesa de Alimentação: Essencial para o dia a dia.";
    }
}
