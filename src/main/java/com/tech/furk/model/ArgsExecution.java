package com.tech.furk.model;

import java.util.List;

public class ArgsExecution {

    List<String> agendas;
    TiposProcessoEnum tipoProcesso;
    boolean isHeadless = false;
    String pathGeckodriver = null;

    public List<String> getAgendas() {
        return this.agendas;
    }

    public void setAgendas(List<String> agendas) {
        this.agendas = agendas;
    }

    public TiposProcessoEnum getTipoProcesso() {
        return this.tipoProcesso;
    }

    public void setTipoProcesso(TiposProcessoEnum tipoProcess) {
        this.tipoProcesso = tipoProcess;
    }

    public boolean isHeadless() {
        return this.isHeadless;
    }

    public void setIsHeadless(boolean isHeadless) {
        this.isHeadless = isHeadless;
    }

    public String getPathGeckodriver() {
        return this.pathGeckodriver;
    }

    public void setIsHeadless(String pathGeckodriver) {
        this.pathGeckodriver = pathGeckodriver;
    }
}
