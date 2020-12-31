package dev.assemblyline.service;

import dev.assemblyline.model.Activity;
import dev.assemblyline.model.AssemblyLine;
import dev.assemblyline.model.Stage;
import dev.assemblyline.utils.TimeCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de teste para a classe {@link AssemblyLineGenerator} de geração de linha de montagem.
 */
public class AssemblyLineGeneratorTest {

    /** Serviço gerador de linha de montagem. */
    private AssemblyLineGenerator assemblyLineGenerator;

    /**
     * Inicializa a instância de assemblyLineGenerator.
     */
    @Before
    public void initInstance() {
        this.assemblyLineGenerator = new AssemblyLineGenerator();
    }

    /**
     * Verifica se o período da manhã está terminando antes das 12h.
     */
    @Test
    public void should_FinishMorningPeriodUntil12h() {
        List<Activity> activityList = new ArrayList<>();
        String alphabet = "ABCDEFG";
        for (char c : alphabet.toCharArray()) {
            activityList.add(new Activity("Activity " + c + " 60Min", 60));
        }
        final List<AssemblyLine> actual = this.assemblyLineGenerator.extractAssemblyLinesByActivities(activityList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final Stage stage = actual.get(0).getMorning().getStageList().stream().max(
                (o1, o2) -> TimeCalculator.compare(o1.getStartTime(), o2.getStartTime(), dateFormat)).get();
        final String time = TimeCalculator.getNextTime(stage.getStartTime(), stage.getActivity().getDurationInMinute(),
                dateFormat);
        Assert.assertTrue(TimeCalculator.compare(time, "12:00", dateFormat) <= 0);
    }

    /**
     * Verifica se o período da tarde está começando às 13h.
     */
    @Test
    public void should_StartAfternoonPeriod13h() {
        List<Activity> activityList = new ArrayList<>();
        String alphabet = "ABCDEFG";
        for (char c : alphabet.toCharArray()) {
            activityList.add(new Activity("Activity " + c + " 60Min", 60));
        }
        final List<AssemblyLine> actual = this.assemblyLineGenerator.extractAssemblyLinesByActivities(activityList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final Stage stage = actual.get(0).getAfternoon().getStageList().stream().min(
                (o1, o2) -> TimeCalculator.compare(o1.getStartTime(), o2.getStartTime(), dateFormat)).get();
        Assert.assertEquals(0, TimeCalculator.compare(stage.getStartTime(), "13:00", dateFormat));
    }

    /**
     * Verifica se o período da tarde está terminando entre 16h e 16:59h.
     */
    @Test
    public void should_FinishAfternoonBetween16hAnd16h59() {
        List<Activity> activityList = new ArrayList<>();
        String alphabet = "ABCDEFG";
        for (char c : alphabet.toCharArray()) {
            activityList.add(new Activity("Activity " + c + " 60Min", 60));
        }
        activityList.add(new Activity("Activity H 40Min", 40));

        final List<AssemblyLine> actual = this.assemblyLineGenerator.extractAssemblyLinesByActivities(activityList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final Stage stage = actual.get(0).getAfternoon().getStageList().stream().max(
                (o1, o2) -> TimeCalculator.compare(o1.getStartTime(), o2.getStartTime(), dateFormat)).get();
        final String time = TimeCalculator.getNextTime(stage.getStartTime(), stage.getActivity().getDurationInMinute(),
                dateFormat);
        Assert.assertTrue(TimeCalculator.compare(time, "16:00", dateFormat) >= 0
                && TimeCalculator.compare(time, "17" + ":00", dateFormat) <= 0);
    }

    /**
     * Verifica se as etapas não possuem intervalo.
     */
    @Test
    public void should_TimeWithoutIntervals() {
        List<Activity> activityList = new ArrayList<>();
        String alphabet = "ABCDEFG";
        for (char c : alphabet.toCharArray()) {
            activityList.add(new Activity("Activity " + c + " 60Min", 60));
        }
        activityList.add(new Activity("Activity H 40Min", 40));

        final List<AssemblyLine> actual = this.assemblyLineGenerator.extractAssemblyLinesByActivities(activityList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final AssemblyLine assemblyLine = actual.get(0);

        int sumOfDifferences = 0;
        final List<Stage> stageList = assemblyLine.getMorning().getStageList();
        for (int i = 1; i < stageList.size(); i++) {
            final Stage previousStage = stageList.get(i - 1);
            final String previousTime = TimeCalculator.getNextTime(previousStage.getStartTime(),
                    previousStage.getActivity().getDurationInMinute(), dateFormat);
            sumOfDifferences += TimeCalculator.getDurationBetween(previousTime, stageList.get(i).getStartTime(),
                    dateFormat);
        }
        Assert.assertEquals( 0, sumOfDifferences);
    }
}
