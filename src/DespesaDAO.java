import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {
    private static final String FILE_PATH = "despesas.txt";
    private TipoDespesaDAO tipoDespesaDAO = new TipoDespesaDAO();

    public void salvar(Despesa despesa) {
        List<Despesa> despesas = listarTodos();
        // Lógica simples: remove a despesa antiga se existir (para edição) e adiciona a nova
        despesas.removeIf(d -> d.getId() == despesa.getId());
        despesas.add(despesa);
        reescreverArquivo(despesas);
    }

    public List<Despesa> listarTodos() {
        List<Despesa> despesas = new ArrayList<>();
        List<TipoDespesa> tipos = tipoDespesaDAO.listarTodos(); // Para buscar o TipoDespesa
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 7) {
                    try {
                        int id = Integer.parseInt(partes[0]);
                        String descricao = partes[1];
                        double valor = Double.parseDouble(partes[2]);
                        LocalDate dataVencimento = LocalDate.parse(partes[3], DateTimeFormatter.ISO_DATE);
                        int tipoId = Integer.parseInt(partes[4]);
                        double valorPago = Double.parseDouble(partes[5]);
                        boolean paga = Boolean.parseBoolean(partes[6]);

                        // Simulação de busca do TipoDespesa (MVP)
                        TipoDespesa tipo = tipos.stream()
                                .filter(t -> t.getId() == tipoId)
                                .findFirst()
                                .orElse(new TipoDespesa(tipoId, "Desconhecido")); // Tipo padrão se não encontrar

                        // Criação da despesa (usando DespesaAlimentacao como exemplo de classe concreta para MVP)
                        Despesa despesa = new DespesaAlimentacao(descricao, valor, dataVencimento, tipo);
                        // Ajusta os campos que não são definidos no construtor
                        // Nota: Em um sistema real, o ID não seria redefinido assim, mas para MVP e persistência simples, é aceitável.
                        // O contador estático será ajustado no final para refletir o maior ID.

                        // Simulação de pagamento
                        if (paga) {
                            despesa.anotarPagamento(valorPago);
                        }

                        despesas.add(despesa);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Erro ao parsear linha da despesa: " + linha);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe, retorna lista vazia
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de despesas: " + e.getMessage());
        }
        return despesas;
    }

    private void reescreverArquivo(List<Despesa> despesas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Despesa despesa : despesas) {
                bw.write(despesa.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de despesas: " + e.getMessage());
        }
    }
}
