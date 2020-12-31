package dev.assemblyline;

import dev.assemblyline.service.ActivityCollector;
import dev.assemblyline.service.AssemblyLineGenerator;
import dev.assemblyline.service.ProductionGenerator;

public class MainApplication {

    public static void main(String[] args) {
        // Inicializando os serviços necessários para gerar a produção.
        final ActivityCollector activityCollector = new ActivityCollector();
        final AssemblyLineGenerator assemblyLineGenerator = new AssemblyLineGenerator();

        // Gerando a produção.
        ProductionGenerator productionGenerator = new ProductionGenerator(activityCollector, assemblyLineGenerator);
        productionGenerator.generate(args);

        // Exibindo a produção gerada no console.
        System.out.println(productionGenerator.getProduction());
    }
}
