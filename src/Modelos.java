import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Interface para Polimorfismo
interface Pagavel {
    boolean isPago();
    void anotarPagamento(double valorPago);
}

// Classe Abstrata para Herança
abstract class Despesa implements Pagavel {
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

// Classe de Modelo
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

// Classe de Modelo
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

// Data Access Object (DAO)
class TipoDespesaDAO {
    private static final String FILE_PATH = "tipos_despesa.txt";

    public void salvar(TipoDespesa tipo) {
        List<TipoDespesa> tipos = listarTodos();
        // Lógica simples de salvar (sem edição/exclusão no POC)
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

// Data Access Object (DAO)
class UsuarioDAO {
    private static final String FILE_PATH = "usuarios.txt";

    public void salvar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        // Lógica simples de salvar (sem edição/exclusão no POC)
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
