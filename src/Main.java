import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("--- Sistema de Controle de Despesas ---");

        while (running) {
            exibirMenuPrincipal();
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    entrarDespesa(scanner);
                    break;
                case "2":
                    anotarPagamento(scanner);
                    break;
                case "3":
                    listarDespesas(scanner, false);
                    break;
                case "4":
                    listarDespesas(scanner, true);
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
        DespesaDAO despesaDAO = new DespesaDAO();
        TipoDespesaDAO tipoDAO = new TipoDespesaDAO();
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
            Despesa novaDespesa = new DespesaAlimentacao(descricao, valor, dataVencimentoStr, tipoSelecionado);
            despesaDAO.salvar(novaDespesa);
            System.out.println("Despesa cadastrada com sucesso! ID: " + novaDespesa.getId());
        } else {
            System.out.println("ID de Tipo de Despesa inválido.");
        }
    }

    private static void anotarPagamento(Scanner scanner) {
        DespesaDAO despesaDAO = new DespesaDAO();
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
        DespesaDAO despesaDAO = new DespesaDAO();
        List<Despesa> todasDespesas = despesaDAO.listarTodos();
        String status = pagas ? "Pagas" : "Em Aberto";

        System.out.println("\n--- Listar Despesas " + status + " ---");

        List<Despesa> despesasFiltradas = todasDespesas.stream()
                .filter(d -> d.isPago() == pagas)
                .toList();

        if (despesasFiltradas.isEmpty()) {
            System.out.println("Nenhuma despesa " + status.toLowerCase() + " encontrada.");
            return;
        }

        for (Despesa d : despesasFiltradas) {
            System.out.printf("ID: %d | Descrição: %s | Valor: R$%.2f | Vencimento: %s | Tipo: %s | Pago: R$%.2f%n",
                    d.getId(), d.getDescricao(), d.getValor(), d.getDataVencimento().format(DateTimeFormatter.ISO_DATE),
                    d.getTipo().getNome(), d.getValorPago());
        }

        // Submenu (MVP simplificado)
        System.out.println("\n--- Submenu ---");
        System.out.println("1. Editar Despesa (Funcionalidade a ser implementada)");
        System.out.println("2. Excluir Despesa (Funcionalidade a ser implementada)");
        System.out.println("3. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                System.out.println("Funcionalidade de Edição não implementada no MVP.");
                break;
            case "2":
                System.out.println("Funcionalidade de Exclusão não implementada no MVP.");
                break;
            case "3":
                // Volta ao menu principal
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void gerenciarTiposDespesa(Scanner scanner) {
        TipoDespesaDAO dao = new TipoDespesaDAO();
        System.out.println("\n--- Gerenciar Tipos de Despesa (MVP) ---");
        System.out.println("1. Listar Tipos");
        System.out.println("2. Adicionar Novo Tipo");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                System.out.println("\n--- Tipos de Despesa Cadastrados ---");
                List<TipoDespesa> tipos = dao.listarTodos();
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
                dao.salvar(new TipoDespesa(id, nome));
                System.out.println("Tipo de despesa '" + nome + "' adicionado com sucesso.");
                break;
            default:
                System.out.println("Opção inválida.");
        }
        System.out.println("Voltando ao Menu Principal...");
    }

    private static void gerenciarUsuarios(Scanner scanner) {
        UsuarioDAO dao = new UsuarioDAO();
        System.out.println("\n--- Gerenciar Usuários (MVP) ---");
        System.out.println("1. Listar Usuários");
        System.out.println("2. Cadastrar Novo Usuário");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                System.out.println("\n--- Usuários Cadastrados ---");
                List<Usuario> usuarios = dao.listarTodos();
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
                dao.salvar(new Usuario(id, login, senhaCriptografada));
                System.out.println("Usuário '" + login + "' cadastrado com sucesso. Senha criptografada: " + senhaCriptografada);
                break;
            default:
                System.out.println("Opção inválida.");
        }
        System.out.println("Voltando ao Menu Principal...");
    }
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
}
