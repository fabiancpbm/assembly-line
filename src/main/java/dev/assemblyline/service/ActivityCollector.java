package dev.assemblyline.service;

import dev.assemblyline.model.Activity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por dar suporte à construção e lógica de funcionamento das {@link Activity}.
 */
public class ActivityCollector {

    /** Valor que representa duração inválida de uma atividade. */
    private static final int INVALID_DURATION = -1;

    /** Palavra chave para detectar atividade de manutenção. */
    public static final String MAINTENANCE_ACTIVITY_KEYWORD = "maintenance";

    /**
     * Construtor.
     */
    public ActivityCollector() {
    }

    /**
     * A partir de um arquivo index.txt, converte as linhas deste arquivo para uma lista de atividades.
     *
     * @param file Arquivo index.txt.
     * @return Lista de atividades lidas do arquivo.
     */
    public List<Activity> extractActivitiesFromFile(File file) {
        List<Activity> activities = new ArrayList<>();
        List<String> fileLines = read(file);
        if (fileLinesAreValid(fileLines)) {
            for (String fileLine : fileLines) {
                final Activity activity = convertToActivity(fileLine);
                if (activity != null) {
                    activities.add(activity);
                }
            }
        }
        return activities;
    }

    /**
     * Realiza a leitura do arquivo input.txt e converte para uma lista de linhas lidas.
     *
     * @param file Arquivo input.txt.
     * @return Lista com as linhas do arquivo.
     */
    private List<String> read(File file) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(file.toPath()).collect(Collectors.toList());
        } catch (IOException e) {
            // TODO Adicionar mensagem: Não foi possível ler o arquivo input.txt.
        }
        return lines;
    }

    /**
     * Verifica se a lista com as linhas do arquivo são válidas (não nulas e não vazias).
     *
     * @param fileLines Lista das linhas do arquivo.
     * @return {@code #true}, se a lista é válida; {@code #false}, se não é.
     */
    private boolean fileLinesAreValid(List<String> fileLines) {
        return fileLines != null && !fileLines.isEmpty();
    }

    /**
     * Converte uma linha do arquivo para uma instância de atividade.
     *
     * @param fileLine Linha do arquivo de entrada.
     * @return Atividade convertida. Caso não seja possível converter esta atividade, deve ser retornado nulo.
     */
    private Activity convertToActivity(String fileLine) {
        final Activity activity = new Activity(fileLine, getDurationInMinutes(fileLine));
        if (activity.getDurationInMinute() != INVALID_DURATION) {
            return activity;
        } else {
            return null;
        }
    }

    /**
     * Recupera a duração de tempo de uma atividade em minutos de acordo com o que está indicado na linha do arquivo.
     * Caso haja um número na linha do arquivo, ele deve representar a duração em minutos. Caso contrário e caso haja
     * a palavra MAINTENANCE_WORD, a duração em minutos deve ser igual a 5. Caso nenhum dos caso ocorra, o método
     * retorna um valor INVALID_DURATION.
     *
     * @param fileLine Linha do arquivo.
     * @return Duração em minutos.
     */
    private int getDurationInMinutes(String fileLine) {
        String digits = fileLine.replaceAll("[^0-9]", "");
        // Caso os dígitos existam e estejam distribuídos lado a lado no fileLine, então o dígito pode representar a
        // duração em minutos.
        if (!digits.isEmpty() && fileLine.contains(digits)) {
            return Integer.parseInt(digits);
        } else if (fileLine.contains(MAINTENANCE_ACTIVITY_KEYWORD)) {
            return 5;
        }
        return INVALID_DURATION;
    }
}