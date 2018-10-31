package com.example.aluno.ioasys.entity;

import java.util.List;

public class EmpresasLista {

    private List<Empresas> enterprises;

    public EmpresasLista(List<Empresas> enterprises) {
        this.enterprises = enterprises;
    }

    public List<Empresas> getEnterprises() {
        return enterprises;
    }
}
