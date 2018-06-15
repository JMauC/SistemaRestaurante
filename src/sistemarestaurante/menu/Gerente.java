package sistemarestaurante.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import sistemarestaurante.administrativo.CustoEstoque;
import sistemarestaurante.administrativo.CustoRH;
import sistemarestaurante.administrativo.Fornecedor;
import sistemarestaurante.estoque.Ingrediente;
import sistemarestaurante.estoque.Produto;
import sistemarestaurante.ferramentas.ConnectionFactory;
import sistemarestaurante.individuos.Funcionario;
import sistemarestaurante.servico.Pagamento;

public class Gerente {
    public static void menuPrincipal(String cpfUsuario) throws SQLException {
        Scanner input = new Scanner(System.in);
        int opcao = -1;

        while(opcao != 0){
            System.out.println();
            System.out.println();
            System.out.println("[1] Listar funcionarios.");
            System.out.println("[2] Listar cardapio.");
            System.out.println("[3] Listar estoque.");
            System.out.println("[4] Listar fornecedores.");
            System.out.println("[5] Contratar funcionario.");
            System.out.println("[6] Pagar funcionario.");
            System.out.println("[7] Consultar produtividade garcom.");
            System.out.println("[8] Demitir funcionario.");
            System.out.println("[9] Cadastrar novo produto no cardapio.");
            System.out.println("[10] Remover produto do cardapio.");
            System.out.println("[11] Repor estoque.");
            System.out.println("[12] Consultar receitas.");
            System.out.println("[13] Consultar despesas estoque.");
            System.out.println("[14] Consultar despesas funcionarios.");
            System.out.println("[15] Consultar balanco.");
            System.out.println("[16] Listar preferencias de clientes.");
            System.out.println("[0] Sair.");
            System.out.println();
            
            System.out.print("Digite a opção desejada: ");
            opcao = Integer.parseInt(input.nextLine());
            
            switch(opcao){
                case 1:
                    listaFuncionarios();
                    break;

                case 2:
                    listaCardapio();
                    break;
                
                case 3:
                    listaEstoque();
                    break;
                    
                case 4:
                    listaFornecedores();
                    break;
                    
                case 5:
                    contrataFuncionario();
                    break;

                case 6:
                    pagaFuncionario();
                    break;

                case 7:
                    consultaProdutividade();
                    break;
                
                case 8:
                    demiteFuncionario();
                    break;
                    
                case 9:
                    cadastraProduto();
                    break;
                    
                case 10:
                    removeProduto();
                    break;

                case 11:
                    compraEstoque();
                    break;

                case 12:
                    consultaReceitas();
                    break;
                
                case 13:
                    consultaDespesaEstq();
                    break;
                    
                case 14:
                    consultaDespesaRh();
                    break;
                    
                case 15:
                    consultaBalanco();
                    break;

                case 16:
                    listarPreferencias();
                    break;

                case 0:
                    return;
                
                default:
                    System.out.println("Opcao invalida!");
            }
        
        }
        //input.close();
    }


    public static void listaFuncionarios() throws SQLException {
        Funcionario.listaFuncionarios();
    }

    
    public static void listaCardapio() throws SQLException {
        Produto.imprimeProdutos();
    }

    
    public static void listaEstoque() throws SQLException {
        Ingrediente.imprimeEstoque();
    }

    
    public static void listaFornecedores() throws SQLException {
        Fornecedor.listaFornecedores();
    }

    //#########################################################################
    public static void contrataFuncionario() {
        Funcionario este=new Funcionario();
        Scanner input = new Scanner(System.in);
        String novo;
        int outro;
        double num;
        Date data;
        System.out.println("Digite o cpf do novo funcionario");
        novo=input.nextLine();
        este.setCpf(novo); 
        System.out.println("Digite o nome do novo funcionario");
        novo=input.nextLine();
        este.setNome(novo);
        System.out.println("Digite o rg do novo funcionario");
        novo=input.nextLine();
        este.setRg(novo);
        System.out.println("Digite a dada de nacimentodo novo funcionario no formato dia/mes/ano como o exemplo:20/02/1996");
        novo=input.nextLine();
         try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            data = (java.util.Date)formatter.parse(novo);
        } catch (ParseException e) {            
            throw e;
        }
        este.setDataNascimento(data);
        System.out.println("Digite o nome do pai do novo funcionario");
        novo=input.nextLine();
        este.setFiliacaoPai(novo);
        System.out.println("Digite o nome da mãe do novo funcionario");
        novo=input.nextLine();
        este.setFiliacaoMae(novo);
        System.out.println("Digite o pais do novo funcionario");
        novo=input.nextLine();
        este.setNaturalidade(novo);
        System.out.println("Digite o estado civl do novo funcionario");
        novo=input.nextLine();
        este.setEstadoCivil(novo);
        System.out.println("Digite o 1 caso o novo funcionario seja homem e 2 caso seja mulher");
        outro=input.nextInt();
        if(outro==1)
        {
            este.setSexoMasculino(true)
        }
        if(outro==2)
        {
            este.setSexoMasculino(false)
        }
        System.out.println("Digite o telefone do novo funcionario");
        novo=input.nextLine();
        este.setTelefone(novo)
        System.out.println("Digite o email do novo funcionario");
        novo=input.nextLine();
        este.setEmail(novo);
        System.out.println("Digite o endereco do novo funcionario");
        novo=input.nextLine();
        este.setEndereco(novo);
        System.out.println("Digite o grau de escolaridade do novo funcionario");
        novo=input.nextLine();
        este.setEscolaridade(novo);
        System.out.println("Digite a carteirade trabalho do novo funcionario");
        novo=input.nextLine();
        este.setCtps(novo);
        System.out.println("Digite o salario do novo funcionario");
        num = input.nextDouble();
        este.setSalario(num);
        System.out.println("Digite 1 caso o turno do novo funcionario seja diurno e 2 caso seja noturno");
        outro=input.nextInt();
        if(outro==1)
        {
            este.setTurnoDiurno(true);
        }
        if(outro==1)
        {
            este.setTurnoDiurno(false);
        }
        System.out.println("Digite a senha inicial do novo funcionario");
        novo=input.nextLine();
        este.setSenha(novo) ;
        System.out.println("Digite o cargo do novo funcionario");
        outro=input.nextInt();
        este.setCodigoCargo(outro);
        este.insereBanco();
    }

    
    public static void pagaFuncionario() throws SQLException {
        Scanner input = new Scanner(System.in);
        String cpfFuncionario;

        System.out.printf("Insira o CPF do funcionario: ");
        cpfFuncionario = input.nextLine();

        CustoRH.insereBanco(cpfFuncionario);

        System.out.printf("\nO salario do funcionario %s, CPF %s, foi pago!\n",
                            Funcionario.buscaNome(cpfFuncionario), cpfFuncionario);

        //input.close();
    }


    public static void consultaProdutividade() throws SQLException {
        Funcionario.consultaProdutividade();
    }

    
    public static void demiteFuncionario() throws SQLException {
        Scanner input = new Scanner(System.in);
        String cpfFuncionario;
        String nomeFuncionario;

        System.out.printf("Insira o CPF do funcionario: ");
        cpfFuncionario = input.nextLine();
        nomeFuncionario = Funcionario.buscaNome(cpfFuncionario);

        Funcionario.removeBanco(cpfFuncionario);;

        System.out.printf("\nO funcionario %s, CPF %s, foi demitido!\n",
                            nomeFuncionario, cpfFuncionario);

        //input.close();
    }


    public static void cadastraProduto() throws SQLException {
        Scanner input = new Scanner(System.in);
        Produto produto = new Produto();
        int codigoIngrediente;
        int qtdIngrediente;
        boolean cadastroConcluido = false;
        
        Ingrediente.imprimeEstoque();

        System.out.print("Insira o nome do produto: ");
        produto.setNome(input.nextLine());
        System.out.print("Insira o preco do produto: ");
        produto.setPreco(Double.parseDouble(input.nextLine()));
        
        while(!cadastroConcluido) {
            System.out.print("Digite o codigo do ingrediente ou \"0\" para sair: ");
            codigoIngrediente = Integer.parseInt(input.nextLine());

            if(codigoIngrediente > 0){
                System.out.printf("Digite a quantidade do ingrediente %s: ", 
                                    Ingrediente.buscaNome(codigoIngrediente));
                qtdIngrediente = Integer.parseInt(input.nextLine());

                produto.addInfoIngredientes(codigoIngrediente, qtdIngrediente);
            }
            else{
                cadastroConcluido = true;
            }
        }

        produto.insereBanco();
        //input.close();
    }


    public static void removeProduto() throws SQLException {
        Scanner input = new Scanner(System.in);
        int codProduto;
        String nomeProduto;

        System.out.printf("Insira o codigo do produto: ");
        codProduto = Integer.parseInt(input.nextLine());
        nomeProduto = Produto.buscaNome(codProduto);

        Produto.removeBanco(codProduto);;

        System.out.printf("\nO produto %s, codigo %d, foi removido do cardapio!\n",
                            nomeProduto, codProduto);

        //input.close();
    }

    
    public static void compraEstoque() throws SQLException {
        Scanner input = new Scanner(System.in);
        CustoEstoque custo = new CustoEstoque();

        System.out.printf("Insira o CNPJ do fornecedor: ");
        custo.setCnpjFornedor(input.nextLine());

        System.out.printf("Insira o codigo do ingrediente: ");
        custo.setCodigoIngrediente(Integer.parseInt(input.nextLine()));

        System.out.printf("Insira a quantidade dos ingredientes: ");
        custo.setQtdIngredientes(Integer.parseInt(input.nextLine()));

        System.out.printf("Insira o preco unitario: ");
        custo.setPrecoUnitario(Double.parseDouble(input.nextLine()));
        
        custo.insereBanco();

        //input.close();
    }

    
    public static void consultaReceitas() throws SQLException {
        Pagamento.listaPagamentos();;
    }

    
    public static void consultaDespesaEstq() throws SQLException {
        CustoEstoque.listaCustos();
    }

    
    public static void consultaDespesaRh() throws SQLException {
        CustoRH.listaCustos();
    }


    public static void consultaBalanco() throws SQLException {
        double custosEstoque = CustoEstoque.buscaCustoTotal();
        double custosRH = CustoRH.buscaCustoTotal();
        double receitaTotal = Pagamento.buscaReceitaTotal();
        double balanco = receitaTotal - custosEstoque - custosRH;

        System.out.printf("\n\n|Custos de Estoque - Custos de RH - Total de Receitas - Balanço|\n");
        System.out.printf("|R$ %.2f\t- R$ %.2f\t- R$ %.2f\t- R$ %.2f|\n",
                            custosEstoque, custosRH, receitaTotal, balanco);
    }
    

    public static void listarPreferencias() throws SQLException {
        Connection con = new ConnectionFactory().getConexao();
		String sql = "SELECT pe_c.cpf, pe_c.nome, pp_pr.nome AS produto, SUM(pp_pr.qtd_produto) " +
                        "FROM (SELECT c.cpf, c.nome, pe.codigo AS cod_pedido " +
                            "FROM pedidos AS pe " +
                            "INNER JOIN clientes AS c " +
                            "ON pe.cpf_cliente = c.cpf " +
                            "WHERE pe.pedido_pago = true) AS pe_c " +
                        "INNER JOIN (SELECT pp.cod_pedido,pr.nome, pp.cod_produto, pp.qtd_produto " +
                                "FROM pedido_produto AS pp " +
                                "INNER JOIN produtos AS pr " +
                                "ON pp.cod_produto = pr.codigo) AS pp_pr " +
                        "ON pe_c.cod_pedido = pp_pr.cod_pedido " +
                        "GROUP BY pe_c.cpf, pe_c.nome, pp_pr.nome " +
                        "ORDER BY pe_c.nome ASC, SUM(pp_pr.qtd_produto) DESC;";
		PreparedStatement stmt = con.prepareStatement(sql);

        try {
            ResultSet rs = stmt.executeQuery();

			System.out.printf("\n\n|CPF\t\t- Nome\t\t\t- Produto\t- Quantidade|\n");

            while(rs.next()){
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String produto = rs.getString("produto");
                int qtd = rs.getInt("sum");

                System.out.printf("|%s\t- %s\t- %s\t- %d|\n", cpf, nome, produto, qtd);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            stmt.close();
            con.close();
        }
    }
}
