package dev.assemblyline.service;

import dev.assemblyline.model.Activity;
import dev.assemblyline.model.AssemblyLine;

import java.util.List;

/**
 * Serviço singleton responsável por dar suporte à construção e lógica de funcionamento das {@link AssemblyLineService}.
 */
public class AssemblyLineService {

    /** Instância singleton. */
    private static AssemblyLineService instance;

    /**
     * Construtor privado.
     */
    private AssemblyLineService() {
    }

    /**
     * Recupera a instância singleton deste {@link AssemblyLineService}.
     *
     * @return {@link #instance}
     */
    public static AssemblyLineService getInstance() {
        if (instance == null) {
            instance = new AssemblyLineService();
        }
        return instance;
    }

    /**
     * A partir da lista de atividades recuperadas, extrai a lista de linhas de produção que se consegue criar. Isso
     * já considera os períodos de manhã, lanche, tarde e a ginástica laboral.
     *
     * @param activities Lista de atividades.
     * @return Lista de linhas de produção.
     */
    public List<AssemblyLine> extractAssemblyLinesByActivities(List<Activity> activities) {
        return null;
    }
}
