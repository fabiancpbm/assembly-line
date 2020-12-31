package dev.assemblyline.service;

import dev.assemblyline.model.Activity;
import dev.assemblyline.model.AssemblyLine;
import dev.assemblyline.model.Production;

import java.io.File;
import java.util.List;

import static dev.assemblyline.utils.Constants.FILE_NAME;

public class ProductionGenerator {

    private final ActivityCollector activityCollector;

    private final AssemblyLineGenerator assemblyLineGenerator;

    private Production production;

    public ProductionGenerator(ActivityCollector activityCollector, AssemblyLineGenerator assemblyLineGenerator) {
        this.activityCollector = activityCollector;
        this.assemblyLineGenerator = assemblyLineGenerator;
    }

    public void generate(String[] args) {
        File file = this.getInputFile(args);
        if (file == null) {
            System.err.println("Arquivo não encontrado. Verifique se ele existe e se o seu caminho está sendo passado"
                    + " no formato 'filePath'");
        }
        List<Activity> activities = this.activityCollector.extractActivitiesFromFile(file);
        this.production = this.extractProductionFromActivities(activities);
    }

    private File getInputFile(String[] args) {
        if (isTheExpectedArgument(args)) {
            File file = new File(args[0]);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    private boolean isTheExpectedArgument(String[] args) {
        return (args != null) && (args.length == 1) && (!args[0].isEmpty()) && (args[0].endsWith(FILE_NAME));
    }

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
