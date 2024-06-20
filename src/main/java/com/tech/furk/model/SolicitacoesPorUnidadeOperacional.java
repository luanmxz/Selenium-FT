package com.tech.furk.model;

import java.util.List;
import java.util.ArrayList;

public class SolicitacoesPorUnidadeOperacional {

    public String unidadeOperacional;
    public List<Solicitacao> solicitacoesPorUnidadeOperacional = new ArrayList<Solicitacao>();

    public String getUnidadeOperacional() {
        return this.unidadeOperacional;
    }

    public void setUnidadeOperacional(String unidadeOperacional) {
        this.unidadeOperacional = unidadeOperacional.substring(0, unidadeOperacional.length() - 1);
    }

    public List<Solicitacao> getSolicitacoesPorUnidadeOperacional() {
        return this.solicitacoesPorUnidadeOperacional;
    }

    public void setSolicitacoesPorUnidadeOperacional(List<Solicitacao> solicitoesPorUnidadeOperacional) {
        this.solicitacoesPorUnidadeOperacional = solicitoesPorUnidadeOperacional;
    }

    public void addSolicitacao(Solicitacao solicitacao) {
        this.solicitacoesPorUnidadeOperacional.add(solicitacao);
    }

}
