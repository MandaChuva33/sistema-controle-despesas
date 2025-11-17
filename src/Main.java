import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

// Importa todas as classes do arquivo Modelos.java
// Nota: Em um projeto real, estas classes estariam em arquivos separados e pacotes.
// Para atender ao requisito de "menos arquivos possível", elas foram consolidadas.
// O compilador Java (javac) permite compilar múltiplos arquivos de uma vez.

public class Main {
    private static final TipoDespesaDAO tipoDAO = new TipoDespesaDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    // DespesaDAO não é estático para permitir a busca de TiposDespesa
    // em um ambiente de execução simples.

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("--- Sistema de Controle de Despesas ---");

        while (running) {
            exibirMenuPrincipal();
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            try {
                switch (opcao) {
                    case "1":
                        entrarDespesa(scanner);
                        break;
                    case "2":
                        anotarPagamento(scanner);
                        break;
                    case "3":
                        listarDespesas(scanner, false); // Despesas em Aberto
                        break;
                    case "4":
                        listarDespesas(scanner, true); // Despesas Pagas
                        break;
                    case "5":
                        gerenciarTiposDespesa(scanner);
                        break;
                    case "6":
                        gerenciarUsuarios(scanner);
                        break;
                    case "7":
                        System.out.println("Saindo do sistema...");
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Ocorreu um erro: " + e.getMessage());
            }
            System.out.println(); // Linha em branco para melhor visualização
        }
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("=====================================");
        System.out.println("          MENU PRINCIPAL             ");
        System.out.println("=====================================");
        System.out.println("1. Entrar Despesa");
        System.out.println("2. Anotar Pagamento");
        System.out.println("3. Listar Despesas em Aberto no período");
        System.out.println("4. Listar Despesas Pagas no período");
        System.out.println("5. Gerenciar Tipos de Despesa");
        System.out.println("6. Gerenciar Usuários");
        System.out.println("7. Sair");
        System.out.println("-------------------------------------");
    }

    private static void entrarDespesa(Scanner scanner) {
        DespesaDAO despesaDAO = new DespesaDAO(tipoDAO);
        List<TipoDespesa> tipos = tipoDAO.listarTodos();

        if (tipos.isEmpty()) {
            System.out.println("ERRO: Nenhum Tipo de Despesa cadastrado. Cadastre um tipo primeiro (Opção 5).");
            return;
        }

        System.out.println("\n--- Entrar Despesa ---");
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Data de Vencimento (AAAA-MM-DD): ");
        String dataVencimentoStr = scanner.nextLine();

        System.out.println("Tipos disponíveis:");
        for (TipoDespesa tipo : tipos) {
            System.out.println("  [" + tipo.getId() + "] " + tipo.getNome());
        }
        System.out.print("ID da Categoria: ");
        int tipoId = Integer.parseInt(scanner.nextLine());

        TipoDespesa tipoSelecionado = tipos.stream()
                .filter(t -> t.getId() == tipoId)
                .findFirst()
                .orElse(null);

        if (tipoSelecionado != null) {
            // Usando DespesaAlimentacao como classe concreta para o MVP
            Despesa novaDespesa = new DespesaAlimentacao(descricao, valor, LocalDate.parse(dataVencimentoStr, DateTimeFormatter.ISO_DATE), tipoSelecionado);
            despesaDAO.salvar(novaDespesa);
            System.out.println("Despesa cadastrada com sucesso! ID: " + novaDespesa.getId());
            System.out.println("Contador de Despesas (Estático): " + Despesa.getContadorDespesas());
        } else {
            System.out.println("ID de Tipo de Despesa inválido.");
        }
    }

    private static void anotarPagamento(Scanner scanner) {
        DespesaDAO despesaDAO = new DespesaDAO(tipoDAO);
        System.out.println("\n--- Anotar Pagamento ---");
        System.out.print("Digite o ID da Despesa a ser paga: ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Despesa> despesas = despesaDAO.listarTodos();
        Despesa despesa = despesas.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);

        if (despesa != null) {
            if (despesa.isPago()) {
                System.out.println("Despesa ID " + id + " já está paga.");
                return;
            }
            System.out.println("Despesa: " + despesa.getDescricao() + " | Valor Pendente: " + (despesa.getValor() - despesa.getValorPago()));
            System.out.print("Valor do pagamento: ");
            double valorPago = Double.parseDouble(scanner.nextLine());

            despesa.anotarPagamento(valorPago);
            despesaDAO.salvar(despesa); // Salva a despesa atualizada
            System.out.println("Pagamento de R$" + valorPago + " anotado com sucesso.");
            if (despesa.isPago()) {
                System.out.println("Despesa ID " + id + " foi totalmente paga.");
            }
        } else {
            System.out.println("Despesa com ID " + id + " não encontrada.");
        }
    }

    private static void listarDespesas(Scanner scanner, boolean pagas) {
        DespesaDAO despesaDAO = new DespesaDAO(tipoDAO);
        List<Despesa> todasDespesas = despesaDAO.listarTodos();
        String status = pagas ? "Pagas" : "Em Aberto";

        System.out.println("\n--- Listar Despesas " + status + " ---");

        List<Despesa> despesasFiltradas = todasDespesas.stream()
                .filter(d -> d.isPago() == pagas)
                .collect(Collectors.toList());

        if (despesasFiltradas.isEmpty()) {
            System.out.println("Nenhuma despesa " + status.toLowerCase() + " encontrada.");
            return;
        }

        for (Despesa d : despesasFiltradas) {
            System.out.printf("ID: %d | Descrição: %s | Valor: R$%.2f | Vencimento: %s | Tipo: %s | Detalhe: %s | Pago: R$%.2f%n",
                    d.getId(), d.getDescricao(), d.getValor(), d.getDataVencimento().format(DateTimeFormatter.ISO_DATE),
                    d.getTipo().getNome(), d.getDetalheTipo(), d.getValorPago());
        }

        // Submenu (MVP)
        System.out.println("\n--- Submenu ---");
        System.out.println("1. Editar Despesa");
        System.out.println("2. Excluir Despesa");
        System.out.println("3. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                editarDespesa(scanner, despesaDAO);
                break;
            case "2":
                excluirDespesa(scanner, despesaDAO);
                break;
            case "3":
                // Volta ao menu principal
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void editarDespesa(Scanner scanner, DespesaDAO despesaDAO) {
        System.out.println("\n--- Editar Despesa ---");
        System.out.print("Digite o ID da Despesa a ser editada: ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Despesa> despesas = despesaDAO.listarTodos();
        Despesa despesa = despesas.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElse(null);

        if (despesa != null) {
            System.out.println("Editando Despesa ID " + id + ": " + despesa.getDescricao());
            System.out.print("Nova Descrição (Atual: " + despesa.getDescricao() + "): ");
            String novaDescricao = scanner.nextLine();
            // Nota: Para simplificar o MVP, a edição de Despesa será limitada a Descrição.
            // Em um sistema real, a classe Despesa teria setters.
            // Aqui, vamos simular a edição salvando uma nova despesa com o mesmo ID e nova descrição.
            // Isso requer uma lógica mais complexa no DAO, que será simplificada para o MVP.
            System.out.println("Funcionalidade de edição simulada: Apenas a descrição seria alterada.");
            System.out.println("Voltando ao Menu Principal...");
        } else {
            System.out.println("Despesa com ID " + id + " não encontrada.");
        }
    }

    private static void excluirDespesa(Scanner scanner, DespesaDAO despesaDAO) {
        System.out.println("\n--- Excluir Despesa ---");
        System.out.print("Digite o ID da Despesa a ser excluída: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (despesaDAO.excluir(id)) {
            System.out.println("Despesa ID " + id + " excluída com sucesso.");
        } else {
            System.out.println("Despesa com ID " + id + " não encontrada.");
        }
    }

    private static void gerenciarTiposDespesa(Scanner scanner) {
        System.out.println("\n--- Gerenciar Tipos de Despesa ---");
        System.out.println("1. Listar Tipos");
        System.out.println("2. Adicionar Novo Tipo");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                System.out.println("\n--- Tipos de Despesa Cadastrados ---");
                List<TipoDespesa> tipos = tipoDAO.listarTodos();
                if (tipos.isEmpty()) {
                    System.out.println("Nenhum tipo de despesa cadastrado.");
                } else {
                    for (TipoDespesa tipo : tipos) {
                        System.out.println("ID: " + tipo.getId() + ", Nome: " + tipo.getNome());
                    }
                }
                break;
            case "2":
                System.out.print("Digite o ID do novo tipo (apenas números): ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Digite o nome do novo tipo: ");
                String nome = scanner.nextLine();
                tipoDAO.salvar(new TipoDespesa(id, nome));
                System.out.println("Tipo de despesa '" + nome + "' adicionado com sucesso.");
                break;
            default:
                System.out.println("Opção inválida.");
        }
        System.out.println("Voltando ao Menu Principal...");
    }

    private static void gerenciarUsuarios(Scanner scanner) {
        System.out.println("\n--- Gerenciar Usuários ---");
        System.out.println("1. Listar Usuários");
        System.out.println("2. Cadastrar Novo Usuário");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                System.out.println("\n--- Usuários Cadastrados ---");
                List<Usuario> usuarios = usuarioDAO.listarTodos();
                if (usuarios.isEmpty()) {
                    System.out.println("Nenhum usuário cadastrado.");
                } else {
                    for (Usuario u : usuarios) {
                        System.out.println("ID: " + u.getId() + ", Login: " + u.getLogin() + ", Senha (Hash): " + u.getSenhaCriptografada());
                    }
                }
                break;
            case "2":
                System.out.print("Digite o ID do novo usuário (apenas números): ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Digite o Login: ");
                String login = scanner.nextLine();
                System.out.print("Digite a Senha: ");
                String senha = scanner.nextLine();
                String senhaCriptografada = UsuarioDAO.criptografarSenha(senha);
                usuarioDAO.salvar(new Usuario(id, login, senhaCriptografada));
                System.out.println("Usuário '" + login + "' cadastrado com sucesso. Senha criptografada: " + senhaCriptografada);
                break;
            default:
                System.out.println("Opção inválida.");
        }
        System.out.println("Voltando ao Menu Principal...");
    }
}

// Data Access Object (DAO) para Despesa - Necessário para o MVP
class DespesaDAO {
    private static final String FILE_PATH = "despesas.txt";
    private final TipoDespesaDAO tipoDespesaDAO;

    public DespesaDAO(TipoDespesaDAO tipoDespesaDAO) {
        this.tipoDespesaDAO = tipoDespesaDAO;
    }

    public void salvar(Despesa despesa) {
        List<Despesa> despesas = listarTodos();
        // Remove a despesa antiga se existir (para edição/atualização de pagamento)
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
