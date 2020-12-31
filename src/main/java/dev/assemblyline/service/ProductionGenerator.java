package dev.assemblyline.service;

import dev.assemblyline.model.Activity;
import dev.assemblyline.model.AssemblyLine;
import dev.assemblyline.model.Production;
import dev.assemblyline.utils.Constants;

import java.io.File;
import java.util.List;

import static dev.assemblyline.utils.Constants.FILE_NAME;

/**
 * Gerador de produção é o serviço que utiliza o arquivo de entrada para estruturar uma produção com uma ou mais
 * linhas de produção.
 */
public class ProductionGenerator {

    /** Serviço coletor de atividades do arquivo de entrada. */
    private final ActivityCollector activityCollector;

    /** Serviço gerador de linhas de produção. */
    private final AssemblyLineGenerator assemblyLineGenerator;

    /** Modelo de produção. */
    private Production production;

    /**
     * Construtor.
     *
     * @param activityCollector     {@link #activityCollector}
     * @param assemblyLineGenerator {@link #assemblyLineGenerator}]
     */
    public ProductionGenerator(ActivityCollector activityCollector, AssemblyLineGenerator assemblyLineGenerator) {
        this.activityCollector = activityCollector;
        this.assemblyLineGenerator = assemblyLineGenerator;
    }

    /**
     * Instancia o {@link #production} a partir do argumento da aplicação.
     *
     * @param args Argumentos da aplicação.
     */
    public void generate(String[] args) {
        File file = this.getInputFile(args);
        if (file == null) {
            System.err.println(Constants.INPUT_FILE_NOT_FOUND_MESSAGE);
            System.exit(Constants.INPUT_FILE_NOT_FOUND_CODE);
        }
        List<Activity> activities = this.activityCollector.extractActivitiesFromFile(file);
        this.production = this.extractProductionFromActivities(activities);
    }

    /**
     * Captura o arquivo de entrada. Caso este arquivo não exista, será retornado nulo
     *
     * @param args Argumento com o caminho do arquivo.
     * @return Arquivo de entrada encontrado, ou nulo, caso ele não seja encontrado.
     */
    private File getInputFile(String[] args) {
        if (isTheExpectedArgument(args)) {
            File file = new File(args[0]);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    /**
     * Verifica se o argumento passado está conforme o esperado.
     *
     * @param args Argumentos de entrada do programa.
     * @return {@code #true}, se o argumento estiver conforme esperado.
     */
    private boolean isTheExpectedArgument(String[] args) {
        return (args != null) && (args.length == 1) && (!args[0].isEmpty()) && (args[0].endsWith(FILE_NAME));
    }

    /**
     * Extrai uma produção a partir da lista de atividades coletada do arquivo de entrada.
     *
     * @param activities Lista de atividades.
     * @return Produção extraída.
     */
    private Production extractProductionFromActivities(List<Activity> activities) {
        Production production = new Production();
        List<AssemblyLine> assemblyLines = this.assemblyLineGenerator.extractAssemblyLinesByActivities(activities);
        production.getAssemblyLines().addAll(assemblyLines);
        return production;
    }

    /**
     * @return {@link #production}
     */
    public Production getProduction() {
        return this.production;
    }
}
