import java.util.Scanner;

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
                    System.out.println("Funcionalidade: Entrar Despesa");
                    break;
                case "2":
                    System.out.println("Funcionalidade: Anotar Pagamento");
                    break;
                case "3":
                    System.out.println("Funcionalidade: Listar Despesas em Aberto no período");
                    break;
                case "4":
                    System.out.println("Funcionalidade: Listar Despesas Pagas no período");
                    break;
                case "5":
                    gerenciarTiposDespesa(scanner);
                    break;
                case "6":
                    System.out.println("Funcionalidade: Gerenciar Usuários");
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

    private static void gerenciarTiposDespesa(Scanner scanner) {
        TipoDespesaDAO dao = new TipoDespesaDAO();
        System.out.println("\n--- Gerenciar Tipos de Despesa (POC) ---");
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
                System.out.println("Tipo de despesa '" + nome + "' adicionado com sucesso (POC).");
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
