package dev.assemblyline.service;

import dev.assemblyline.model.Activity;

import java.io.File;
import java.util.List;

/**
 * Serviço singleton responsável por dar suporte à construção e lógica de funcionamento das {@link Activity}.
 */
public class ActivityService {

    /** Instância singleton. */
    private static ActivityService instance;

    /**
     * Construtor privado.
     */
    private ActivityService() {
    }

    /**
     * Recupera a instância singleton deste {@link ActivityService}.
     *
     * @return {@link #instance}
     */
    public static ActivityService getInstance() {
        if (instance == null) {
            instance = new ActivityService();
        }
        return instance;
    }

    /**
     * A partir de um arquivo index.txt, converte as linhas deste arquivo para uma lista de atividades.
     *
     * @param file Arquivo index.txt.
     * @return Lista de atividades lidas do arquivo.
     */
    public List<Activity> extractActivitiesFromFile(File file) {
        return null;
    }
}
