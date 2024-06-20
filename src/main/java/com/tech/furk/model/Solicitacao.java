package com.tech.furk.model;

public class Solicitacao {

    public int solicitacaoId;
    public String codigoTipoCompanhia;
    public String numBilhete;
    public String unidadeOperacional;

    public int getSolicitacaoId() {
        return this.solicitacaoId;
    }

    public void setSolicitacaoId(int solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public String getCodigoTipoCompanhia() {
        return this.codigoTipoCompanhia;
    }

    public void setCodigoTipoCompanhia(String codigoTipoCompanhia) {
        this.codigoTipoCompanhia = codigoTipoCompanhia;
    }

    public String getNumBilhete() {
        return this.numBilhete;
    }

    public void setNumBilhete(String numBilhete) {
        this.numBilhete = numBilhete;
    }

    public String getUnidadeOperacional() {
        return this.unidadeOperacional;
    }

    public void setUnidadeOperacional(String unidadeOperacional) {
        this.unidadeOperacional = unidadeOperacional.substring(0, unidadeOperacional.length() - 1);
    }
}
