import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviço para gerenciar despesas e transações financeiras
 * Armazena despesas em memória utilizando ArrayList
 * Versão: 1.0.0
 */
public class GerenciadorDespesas {
    private List<Despesa> despesas;

    public GerenciadorDespesas() {
        this.despesas = new ArrayList<>();
    }

    /**
     * Cadastra uma nova despesa com data de vencimento
     */
    public void cadastrarDespesa(String descricao, double valor, String categoria, String dataVencimento) {
        Despesa novaDespesa = new Despesa(descricao, valor, categoria, dataVencimento);
        despesas.add(novaDespesa);
        System.out.println("\n✓ Despesa cadastrada com sucesso! ID: " + novaDespesa.getId());
    }

    /**
     * Lista todas as despesas cadastradas
     */
    public void listarDespesas() {
        if (despesas.isEmpty()) {
            System.out.println("\nNenhuma despesa cadastrada.");
            return;
        }

        System.out.println("\n========== LISTA DE TRANSAÇÕES FINANCEIRAS ==========");
        for (Despesa despesa : despesas) {
            System.out.println(despesa);
        }
        System.out.println("=====================================================");
        System.out.println("Total de transações: " + despesas.size());
    }

    /**
     * Lista apenas despesas pendentes (não pagas)
     */
    public void listarDespesasPendentes() {
        List<Despesa> pendentes = new ArrayList<>();
        for (Despesa despesa : despesas) {
            if (!despesa.isPaga()) {
                pendentes.add(despesa);
            }
        }

        if (pendentes.isEmpty()) {
            System.out.println("\nNenhuma despesa pendente.");
            return;
        }

        System.out.println("\n========== DESPESAS PENDENTES ==========");
        for (Despesa despesa : pendentes) {
            System.out.println(despesa);
        }
        System.out.println("========================================");
    }

    /**
     * Lista apenas despesas pagas
     */
    public void listarDespesasPagas() {
        List<Despesa> pagas = new ArrayList<>();
        for (Despesa despesa : despesas) {
            if (despesa.isPaga()) {
                pagas.add(despesa);
            }
        }

        if (pagas.isEmpty()) {
            System.out.println("\nNenhuma despesa paga.");
            return;
        }

        System.out.println("\n========== DESPESAS PAGAS ==========");
        for (Despesa despesa : pagas) {
            System.out.println(despesa);
        }
        System.out.println("====================================");
    }

    /**
     * Exclui uma despesa pelo ID
     */
    public void excluirDespesa(int id) {
        for (int i = 0; i < despesas.size(); i++) {
            if (despesas.get(i).getId() == id) {
                despesas.remove(i);
                System.out.println("\n✓ Despesa ID " + id + " excluída com sucesso!");
                return;
            }
        }
        System.out.println("\n✗ Despesa com ID " + id + " não encontrada.");
    }

    /**
     * Registra o pagamento de uma despesa
     */
    public void registrarPagamento(int id) {
        for (Despesa despesa : despesas) {
            if (despesa.getId() == id) {
                if (despesa.isPaga()) {
                    System.out.println("\n✗ Esta despesa já foi paga anteriormente.");
                    return;
                }
                despesa.setPaga(true);
                System.out.println("\n✓ Pagamento registrado com sucesso para a despesa ID " + id);
                return;
            }
        }
        System.out.println("\n✗ Despesa com ID " + id + " não encontrada.");
    }

    /**
     * Calcula o total de despesas por categoria
     */
    public void relatorioPorCategoria(String categoria) {
        double total = 0;
        int quantidade = 0;

        for (Despesa despesa : despesas) {
            if (despesa.getCategoria().equalsIgnoreCase(categoria)) {
                total += despesa.getValor();
                quantidade++;
            }
        }

        System.out.println("\n========== RELATÓRIO POR CATEGORIA ==========");
        System.out.println("Categoria: " + categoria);
        System.out.println("Quantidade de despesas: " + quantidade);
        System.out.printf("Valor total: R$ %.2f\n", total);
        System.out.println("=============================================");
    }

    /**
     * Obtém o total geral de todas as despesas
     */
    public boolean isDespesasEmpty() {
        return despesas.isEmpty();
    }

    /**
     * Obtém o total geral de todas as despesas
     */
    public double getTotalGeral() {
        double total = 0;
        for (Despesa despesa : despesas) {
            total += despesa.getValor();
        }
        return total;
    }
}
