import java.util.Scanner;

/**
 * Sistema de Controle Financeiro Pessoal
 * Classe principal com menu de navegação e integração com a lógica de negócio
 * Versão: 1.0.0
 */
public class Main {
    private static GerenciadorDespesas gerenciador = new GerenciadorDespesas();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = 0;

        do {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            
            // Tratamento de exceção para entrada não numérica
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
            } else {
                System.out.println("\n>>> Entrada inválida! Por favor, digite um número.\n");
                scanner.nextLine(); // Limpar a linha inteira
                opcao = 0; // Força a repetição do loop
                continue;
            }

            switch (opcao) {
                case 1:
                    entrarDespesa();
                    break;
                case 2:
                    anotarPagamento();
                    break;
                case 3:
                    listarDespesasPendentes();
                    break;
                case 4:
                    listarDespesasPagas();
                    break;
                case 5:
                    gerarRelatorioCategoria();
                    break;
                case 6:
                    exibirTotalGeral();
                    break;
                case 7:
                    System.out.println("\n>>> Encerrando o sistema. Obrigado por usar o Controle Financeiro Pessoal!");
                    break;
                default:
                    System.out.println("\n>>> Opção inválida! Tente novamente.\n");
            }
        } while (opcao != 7);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("   CONTROLE FINANCEIRO PESSOAL (CFP)");
        System.out.println("========================================");
        System.out.println("1. Cadastrar Nova Despesa");
        System.out.println("2. Registrar Pagamento");
        System.out.println("3. Visualizar Despesas Pendentes");
        System.out.println("4. Visualizar Despesas Pagas");
        System.out.println("5. Gerar Relatório por Categoria");
        System.out.println("6. Exibir Total Geral de Despesas");
        System.out.println("7. Sair");
        System.out.println("========================================");
    }

    private static void entrarDespesa() {
        System.out.println("\n>>> CADASTRAR NOVA DESPESA");
        
        System.out.print("Descrição (Ex: Aluguel, Supermercado): ");
        String descricao = scanner.nextLine();
        
        double valor = 0;
        boolean valorValido = false;
        while (!valorValido) {
            System.out.print("Valor (R$): ");
            if (scanner.hasNextDouble()) {
                valor = scanner.nextDouble();
                scanner.nextLine(); // Limpar buffer
                valorValido = true;
            } else {
                System.out.println("Valor inválido. Por favor, digite um número.");
                scanner.nextLine(); // Limpar a linha inteira
            }
        }
        
        System.out.print("Categoria (Ex: Moradia, Alimentação, Transporte): ");
        String categoria = scanner.nextLine();
        
        System.out.print("Data de Vencimento (DD/MM/AAAA): ");
        String dataVencimento = scanner.nextLine();
        
        gerenciador.cadastrarDespesa(descricao, valor, categoria, dataVencimento);
    }

    private static void anotarPagamento() {
        System.out.println("\n>>> REGISTRAR PAGAMENTO");
        
        gerenciador.listarDespesasPendentes();
        
        if (gerenciador.isDespesasEmpty()) {
            return; // Sai se não houver despesas
        }

        int id = -1;
        boolean idValido = false;
        while (!idValido) {
            System.out.print("\nInforme o ID da despesa a ser paga: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
                idValido = true;
            } else {
                System.out.println("ID inválido. Por favor, digite um número inteiro.");
                scanner.nextLine(); // Limpar a linha inteira
            }
        }
        
        gerenciador.registrarPagamento(id);
    }

    private static void listarDespesasPendentes() {
        System.out.println("\n>>> VISUALIZAR DESPESAS PENDENTES");
        gerenciador.listarDespesasPendentes();
        
        if (gerenciador.isDespesasEmpty()) {
            return;
        }

        System.out.println("\nDeseja realizar alguma ação?");
        System.out.println("1. Excluir despesa");
        System.out.println("2. Voltar ao menu principal");
        System.out.print("Opção: ");
        
        if (scanner.hasNextInt()) {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            if (opcao == 1) {
                int id = -1;
                boolean idValido = false;
                while (!idValido) {
                    System.out.print("Informe o ID da despesa a excluir: ");
                    if (scanner.hasNextInt()) {
                        id = scanner.nextInt();
                        scanner.nextLine();
                        idValido = true;
                    } else {
                        System.out.println("ID inválido. Por favor, digite um número inteiro.");
                        scanner.nextLine();
                    }
                }
                gerenciador.excluirDespesa(id);
            }
        } else {
            scanner.nextLine(); // Limpar buffer se a entrada não for um número
        }
    }

    private static void listarDespesasPagas() {
        System.out.println("\n>>> VISUALIZAR DESPESAS PAGAS");
        gerenciador.listarDespesasPagas();
        
        if (gerenciador.isDespesasEmpty()) {
            return;
        }

        System.out.println("\nDeseja realizar alguma ação?");
        System.out.println("1. Excluir despesa");
        System.out.println("2. Voltar ao menu principal");
        System.out.print("Opção: ");
        
        if (scanner.hasNextInt()) {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            if (opcao == 1) {
                int id = -1;
                boolean idValido = false;
                while (!idValido) {
                    System.out.print("Informe o ID da despesa a excluir: ");
                    if (scanner.hasNextInt()) {
                        id = scanner.nextInt();
                        scanner.nextLine();
                        idValido = true;
                    } else {
                        System.out.println("ID inválido. Por favor, digite um número inteiro.");
                        scanner.nextLine();
                    }
                }
                gerenciador.excluirDespesa(id);
            }
        } else {
            scanner.nextLine(); // Limpar buffer se a entrada não for um número
        }
    }

    private static void gerarRelatorioCategoria() {
        System.out.println("\n>>> GERAR RELATÓRIO POR CATEGORIA");
        System.out.print("Informe a categoria para o relatório: ");
        String categoria = scanner.nextLine();
        gerenciador.relatorioPorCategoria(categoria);
    }

    private static void exibirTotalGeral() {
        System.out.println("\n>>> TOTAL GERAL DE DESPESAS");
        System.out.printf("O valor total de todas as despesas cadastradas é: R$ %.2f\n", gerenciador.getTotalGeral());
    }
}
