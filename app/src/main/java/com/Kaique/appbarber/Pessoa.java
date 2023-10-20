package com.Kaique.appbarber;

public class Pessoa {
    private String nome;
    private String telefone;
    private String avaliacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return  nome + "                         " + telefone + "                         " + avaliacao+"/5";
    }
}
