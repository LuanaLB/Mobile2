package com.example.application01;

public class Estudante {
    private String nome, disciplina;
    private int nota;


    public Estudante() {
    }


    public Estudante(String nome, String disciplina, int nota) {
        this.nome = nome;
        this.disciplina = disciplina;
        this.nota = nota;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDisciplina() {
        return disciplina;
    }


    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }


    public int getNota() {
        return nota;
    }


    public void setNota(int nota) {
        this.nota = nota;
    }


    @Override
    public String toString() {
        return "Estudante{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
