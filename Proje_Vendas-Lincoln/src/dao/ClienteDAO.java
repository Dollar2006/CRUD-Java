package dao;

import factory.ConnectionFactory;
import java.sql.*;
import modelo.Cliente;
import gui.VisualizarClienteGUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class ClienteDAO {

    private Connection connection;

    public ClienteDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public boolean VerificaCPF(String cpf) throws SQLException {
    String sql = "SELECT COUNT(*) FROM clientes WHERE cli_cpf = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            if (rs.getInt(1) > 0){
                return true;
            }
        }
      
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        return false;
    }

    public void adiciona(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (cli_nome, cli_cpf, cli_email, cli_tel, cli_end, cli_data) VALUES (?, ?, ?, ?, ?, ?)";
        if(VerificaCPF(cliente.getCpf()) == true){
            JOptionPane.showMessageDialog(null,"CPF já existente no banco de dados");
            return;
        }
        else{
            
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTel());
            stmt.setString(5, cliente.getEndereco());
            stmt.setString(6, cliente.getData_nasc()); 
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Cliente " + cliente.getNome() + " inserido com sucesso!");
            stmt.close();
        }
        catch (SQLException u) {
            throw new RuntimeException(u);
        }
        }
    }
    public void atualiza(Cliente cliente) {
    try {
        StringBuilder sql = new StringBuilder("UPDATE clientes SET ");
        List<Object> valores = new ArrayList<>();
        // Cria uma String sql para o código sql e uma lista para os valores a serem inseridos nessa lista, se o valor estiver vazio ele insere nada
        if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
            sql.append("cli_nome = ?, ");
            valores.add(cliente.getNome());
        }
        if (cliente.getCpf() != null && !cliente.getCpf().isEmpty()) {
            sql.append("cli_cpf = ?, ");
            valores.add(cliente.getCpf());
        }
        if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
            sql.append("cli_email = ?, ");
            valores.add(cliente.getEmail());
        }
        if (cliente.getTel() != null && !cliente.getTel().isEmpty()) {
            sql.append("cli_tel = ?, ");
            valores.add(cliente.getTel());
        }
        if (cliente.getEndereco() != null && !cliente.getEndereco().isEmpty()) {
            sql.append("cli_end = ?, ");
            valores.add(cliente.getEndereco());
        }
        if (cliente.getData_nasc() != null && !cliente.getData_nasc().isEmpty()) {
            sql.append("cli_data = ?, ");
            valores.add(cliente.getData_nasc());
        }

        if (valores.isEmpty()) {
            throw new RuntimeException("Nenhum dado informado para atualização.");
        }

        // Remover última vírgula
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE cli_cpf = ?"); // CPF como identificador único
        valores.add(cliente.getCpf());   // CPF no final para o WHERE

        PreparedStatement stmt = connection.prepareStatement(sql.toString());

        for (int i = 0; i < valores.size(); i++) {
            stmt.setString(i + 1, valores.get(i).toString());
        }

        stmt.executeUpdate();
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
    
    public void deleta(String cpf) {
    String sql = "DELETE FROM clientes WHERE cli_cpf = ?";
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpf);
        stmt.executeUpdate();
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}


    public void ler(VisualizarClienteGUI vc){
       try{
       Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from clientes");

            StringBuilder html = new StringBuilder("<html><<body>");
            html.append("<table style='background-color:black; color:white;' border='1' cellpadding='10' cellspacing='1'>");
            html.append("<tr>")
                .append("<th>Nome</th>")
                .append("<th>CPF</th>")
                .append("<th>Email</th>")
                .append("<th>Telefone</th>")
                .append("<th>Endereço</th>")
                .append("<th>Data</th>")
                .append("</tr>");

            while (rs.next()) {
                html.append("<tr>")
                    .append("<td>").append(rs.getString("cli_nome")).append("</td>")
                    .append("<td>").append(rs.getString("cli_CPF")).append("</td>")
                    .append("<td>").append(rs.getString("cli_email")).append("</td>")
                    .append("<td>").append(rs.getString("cli_tel")).append("</td>")
                    .append("<td>").append(rs.getString("cli_end")).append("</td>")
                    .append("<td>").append(rs.getString("cli_data")).append("</td>")
                    .append("</tr>");
            }

            html.append("</table>");
            html.append("</body></html>");

            vc.resposta.setText(html.toString());

            rs.close();
            stmt.close();

       }catch(SQLException u){
           throw new RuntimeException(u);
       }
    }

}