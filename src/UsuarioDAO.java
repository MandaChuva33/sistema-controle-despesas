import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsuarioDAO {
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

    // Método para criptografar a senha (MVP: usando SHA-256)
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
