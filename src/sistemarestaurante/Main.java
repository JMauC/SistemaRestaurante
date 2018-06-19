package sistemarestaurante;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import sistemarestaurante.ferramentas.Login;
import sistemarestaurante.menu.Barman;
import sistemarestaurante.menu.Cozinheiro;
import sistemarestaurante.menu.Garcom;
import sistemarestaurante.menu.Gerente;


public class Main {
    public static void main(String[] args) throws SQLException, ParseException, IOException {
        Login login = new Login();
        int opcao;

        opcao = login.realizaLogin();
        while(opcao!=0){
            switch(opcao){
                case 1:
                    Gerente.menuPrincipal(login.getCpfUsuario());
                    break;
                
                case 2:
                    Garcom.menuPrincipal(login.getCpfUsuario());
                    break;
                
                case 3:
                    Cozinheiro.menuPrincipal(login.getCpfUsuario());
                    break;
                
                case 4:
                    Barman.menuPrincipal(login.getCpfUsuario());
                    break;
                
                default:
                    System.out.println("Falha ao logar usuario!");
            }
        }    
    }
}