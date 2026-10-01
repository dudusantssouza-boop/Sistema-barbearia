import java.util.Scanner;

public class ContaBarbearia {
    private final String nome;
    private final String telefone;
    private double saldo;

    private static double precoPorServico = 35.00;
    private static int totalServicosRealizados = 0;

    public ContaBarbearia(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
        this.saldo = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public double getSaldo() {
        return saldo;
    }

    public static double getPrecoPorServico() {
        return precoPorServico;
    }

    public static boolean alterarPrecoPorServico(double novoPreco) {
        if (novoPreco <= 0) {
            return false;
        }

        precoPorServico = novoPreco;
        return true;
    }

    public static int getTotalServicosRealizados() {
        return totalServicosRealizados;
    }

    public boolean adicionarCreditos(double valor) {
        if (valor <= 0) {
            return false;
        }

        saldo += valor;
        return true;
    }

    // Atendimento para uma pessoa: reaproveita o método com várias pessoas
    public int agendarServico(int quantidadeServicos) {
        return agendarServico(quantidadeServicos, 1);
    }

    public int agendarServico(int quantidadeServicos, int quantidadePessoas) {
        if (quantidadeServicos <= 0 || quantidadePessoas <= 0) {
            return -2;
        }

        int servicosSolicitados = quantidadeServicos * quantidadePessoas;
        double custo = servicosSolicitados * precoPorServico;

        if (saldo < custo) {
            return -1;
        }

        saldo -= custo;
        totalServicosRealizados += servicosSolicitados;
        return servicosSolicitados;
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        ContaBarbearia carlos = new ContaBarbearia("Carlos Silva", "71999990001");
        ContaBarbearia diego = new ContaBarbearia("Diego Lopes", "71999990002");

        int acesso;
        int opcao;

        do {
            System.out.println("=== ESCOLHA O ACESSO ===");
            System.out.println("1 - Barbearia");
            System.out.println("2 - Carlos");
            System.out.println("3 - Diego");
            System.out.println("0 - Encerrar programa");
            System.out.print("Escolha: ");
            acesso = teclado.nextInt();

            if (acesso >= 1 && acesso <= 3) {
                ContaBarbearia conta = null;

                if (acesso == 2) {
                    conta = carlos;
                } else if (acesso == 3) {
                    conta = diego;
                }

                do {
                    System.out.println("\n=== MENU DE OPERAÇÕES ===");

                    if (acesso == 1) {
                        System.out.println("4 - Alterar o preço por serviço");
                    } else {
                        System.out.println("1 - Adicionar créditos");
                        System.out.println("2 - Solicitar serviço");
                        System.out.println("3 - Consultar a conta");
                    }

                    System.out.println("5 - Consultar o preço por serviço");
                    System.out.println("6 - Consultar o total de serviços realizados");
                    System.out.println("0 - Voltar à escolha do acesso");
                    System.out.print("Escolha: ");
                    opcao = teclado.nextInt();

                    switch (opcao) {
                        case 1:
                            if (acesso == 1) {
                                System.out.println("Opção indisponível para a barbearia");
                                break;
                            }

                            System.out.print("Valor a adicionar: R$ ");
                            double valor = teclado.nextDouble();

                            if (conta.adicionarCreditos(valor)) {
                                System.out.println("Créditos adicionados com sucesso");
                            } else {
                                System.out.println("O valor deve ser maior que zero");
                            }
                            break;

                        case 2:
                            if (acesso == 1) {
                                System.out.println("Opção indisponível para a barbearia");
                                break;
                            }

                            System.out.println("1 - Serviço para uma pessoa");
                            System.out.println("2 - Serviço para várias pessoas");
                            System.out.print("Escolha: ");
                            int tipoServico = teclado.nextInt();

                            if (tipoServico == 1 || tipoServico == 2) {
                                System.out.print("Quantidade de serviços (corte, barba...): ");
                                int servicos = teclado.nextInt();
                                int resultado;

                                if (tipoServico == 1) {
                                    resultado = conta.agendarServico(servicos);
                                } else {
                                    System.out.print("Quantidade de pessoas: ");
                                    int pessoas = teclado.nextInt();
                                    resultado = conta.agendarServico(servicos, pessoas);
                                }

                                if (resultado == -2) {
                                    System.out.println("Serviço não realizado: quantidade inválida");
                                } else if (resultado == -1) {
                                    System.out.println("Serviço não realizado: saldo insuficiente");
                                } else {
                                    System.out.println("Serviço realizado: " + resultado + " atendimento(s)");
                                    System.out.printf("Saldo atual: R$ %.2f%n", conta.getSaldo());
                                }
                            } else {
                                System.out.println("Tipo de serviço inválido");
                            }
                            break;

                        case 3:
                            if (acesso == 1) {
                                System.out.println("Opção indisponível para a barbearia");
                                break;
                            }

                            System.out.println("Nome: " + conta.getNome());
                            System.out.println("Telefone: " + conta.getTelefone());
                            System.out.printf("Saldo: R$ %.2f%n", conta.getSaldo());
                            break;

                        case 4:
                            if (acesso != 1) {
                                System.out.println("Opção indisponível para clientes");
                                break;
                            }

                            System.out.print("Novo preço por serviço: R$ ");
                            double novoPreco = teclado.nextDouble();

                            if (ContaBarbearia.alterarPrecoPorServico(novoPreco)) {
                                System.out.println("Preço alterado com sucesso");
                            } else {
                                System.out.println("O preço deve ser maior que zero");
                            }
                            break;

                        case 5:
                            System.out.printf("Preço por serviço: R$ %.2f%n",
                                    ContaBarbearia.getPrecoPorServico());
                            break;

                        case 6:
                            System.out.println("Total de serviços realizados: "
                                    + ContaBarbearia.getTotalServicosRealizados());
                            break;

                        case 0:
                            System.out.println("Retornando à escolha do acesso");
                            break;

                        default:
                            System.out.println("Opção inválida");
                            break;
                    }
                } while (opcao != 0);

            } else if (acesso != 0) {
                System.out.println("Acesso inválido");
            }

            System.out.println();
        } while (acesso != 0);

        System.out.println("Programa encerrado");
        teclado.close();
    }
}