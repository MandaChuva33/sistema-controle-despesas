public class TipoDespesa {
    private int id;
    private String nome;

    public TipoDespesa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

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
