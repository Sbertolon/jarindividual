import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Looca looca = new Looca();

        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        //JanelaGrupo windows = new JanelaGrupo(new SystemInfo());
        //List<Janela> janelas = windows.getJanelas();

        Memoria memoria = new Memoria();

        Scanner leitor = new Scanner(System.in);

        Scanner email = new Scanner(System.in);
        Scanner senha = new Scanner(System.in);

        System.out.println(String.format("""
                 *************************
                *                        *
                *   Bem Vindo ao NOCT.u  *
                *                        *
                 *************************
                """));

        System.out.println("Digite seu email: ");
        String emailUser = email.next();
        System.out.println("Digite sua senha: ");
        String senhaUser = senha.next();

//        Usuario user01 = new Usuario(emailUser, senhaUser);

        List<Usuario> usuariosBanco = con.query("SELECT email, senha FROM usuario " +
                        "WHERE email = '%s' AND senha = '%s'".formatted(emailUser, senhaUser),
                new BeanPropertyRowMapper<>(Usuario.class));

        if (usuariosBanco.size() < 1) {
            System.out.println("Email e/ou senha inválidos");
        } else {

            Integer memoriaDisponivel = Math.toIntExact(memoria.getDisponivel() / 1042);
            Integer memoriaUso = Math.toIntExact(memoria.getEmUso() / 1042);
            Integer memoriaTotal = Math.toIntExact(memoria.getTotal() / 1042);

            Integer escolha;
            do {

                String painel = """
                        |----------------------------------------------|
                              Monitoramento de Janelas da NOCT.U          
                        |----------------------------------------------|
                        | 1 - Visualizar memoria disponivel            |
                        | 2 - Visualizar memoria em uso                |
                        | 3 - Visualizar memoria total                 |
                        | 0 - Sair                                     |
                        |----------------------------------------------|
                                            
                        """;
                System.out.println(painel);

                escolha = leitor.nextInt();

                switch (escolha) {
                    case 1 -> {
                        System.out.println("Memoria disponivel: " + memoriaDisponivel);
                    }
                    case 2 -> {
                        System.out.println("Memoria em Uso: " + memoriaUso);                    }
                    case 3 -> {
                        System.out.println("Memoria total: " + memoriaTotal);
                    }

                    case 0 -> {
                        System.out.println("Obrigada pela preferência, volte sempre!");
                    }

                    default -> {
                        System.out.println("valor inválido");
                    }
                }


            } while (escolha != 0);


        }
    }

}

