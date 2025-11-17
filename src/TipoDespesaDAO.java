import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDespesaDAO {
    private static final String FILE_PATH = "tipos_despesa.txt";

    public void salvar(TipoDespesa tipo) {
        List<TipoDespesa> tipos = listarTodos();
        // Simplesmente adiciona, a lógica de ID e edição será aprimorada no MVP
        tipos.add(tipo);
        reescreverArquivo(tipos);
    }

    public List<TipoDespesa> listarTodos() {
        List<TipoDespesa> tipos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                TipoDespesa tipo = TipoDespesa.fromString(linha);
                if (tipo != null) {
                    tipos.add(tipo);
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe, retorna lista vazia
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de tipos de despesa: " + e.getMessage());
        }
        return tipos;
    }

    private void reescreverArquivo(List<TipoDespesa> tipos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (TipoDespesa tipo : tipos) {
                bw.write(tipo.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de tipos de despesa: " + e.getMessage());
        }
    }
}
