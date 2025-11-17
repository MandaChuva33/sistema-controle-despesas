public class Usuario {
    private int id;
    private String login;
    private String senhaCriptografada;

    public Usuario(int id, String login, String senhaCriptografada) {
        this.id = id;
        this.login = login;
        this.senhaCriptografada = senhaCriptografada;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

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
