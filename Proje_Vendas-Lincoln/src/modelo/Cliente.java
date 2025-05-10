/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Fatec
 */
public class Cliente {
    int codigo;
    String nome;
    String cpf;
    String email;
    String tel;
    String endereco;
    String data_nasc;


    
    
    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
 

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        // Remove caracteres não numéricos
        tel = tel.replaceAll("[^0-9]", "");

        // Validação básica do número de telefone
        if (tel.length() < 10 || tel.length() > 11) {
            this.tel = null;
            System.out.println("Erro: Número de telefone inválido.");
            return;
        }
        else{
            this.tel = tel;
        }

       
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
            this.data_nasc = data_nasc;
        }

   
}

    
