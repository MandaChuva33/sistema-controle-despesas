import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Data Access Object (DAO) para TipoDespesa
class TipoDespesaDAO {
    private static final String FILE_PATH = "tipos_despesa.txt";

    public void salvar(TipoDespesa tipo) {
        List<TipoDespesa> tipos = listarTodos();
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

// Data Access Object (DAO) para Usuário
class UsuarioDAO {
    private static final String FILE_PATH = "usuarios.txt";

    public void salvar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        usuarios.add(usuario);
        reescreverArquivo(usuarios);
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Usuario usuario = Usuario.fromString(linha);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo não existe, retorna lista vazia
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de usuários: " + e.getMessage());
        }
        return usuarios;
    }

    private void reescreverArquivo(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de usuários: " + e.getMessage());
        }
    }

    // Método estático para criptografar a senha
    public static String criptografarSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }
}

// Data Access Object (DAO) para Despesa
class DespesaDAO {
    private static final String FILE_PATH = "despesas.txt";
    private final TipoDespesaDAO tipoDespesaDAO;

    public DespesaDAO(TipoDespesaDAO tipoDespesaDAO) {
        this.tipoDespesaDAO = tipoDespesaDAO;
    }

    public void salvar(Despesa despesa) {
        List<Despesa> despesas = listarTodos();
        despesas.removeIf(d -> d.getId() == despesa.getId());
        despesas.add(despesa);
        reescreverArquivo(despesas);
    }

    public boolean excluir(int id) {
        List<Despesa> despesas = listarTodos();
        boolean removed = despesas.removeIf(d -> d.getId() == id);
        if (removed) {
            reescreverArquivo(despesas);
        }
        return removed;
    }

    public List<Despesa> listarTodos() {
        List<Despesa> despesas = new ArrayList<>();
        List<TipoDespesa> tipos = tipoDespesaDAO.listarTodos();

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

                        TipoDespesa tipo = tipos.stream()
                                .filter(t -> t.getId() == tipoId)
                                .findFirst()
                                .orElse(new TipoDespesa(tipoId, "Desconhecido"));

                        // Criação da despesa (usando DespesaAlimentacao como classe concreta para o MVP)
                        Despesa despesa = new DespesaAlimentacao(id, descricao, valor, dataVencimento, tipo, valorPago, paga);
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

// Classe de Gerenciamento (GerenciadorDespesas)
public class GerenciadorDespesas {
    private final TipoDespesaDAO tipoDAO = new TipoDespesaDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DespesaDAO despesaDAO = new DespesaDAO(tipoDAO);

    public TipoDespesaDAO getTipoDAO() {
        return tipoDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public DespesaDAO getDespesaDAO() {
        return despesaDAO;
    }
}
