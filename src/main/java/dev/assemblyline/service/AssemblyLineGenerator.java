package dev.assemblyline.service;

import dev.assemblyline.model.Activity;
import dev.assemblyline.model.AssemblyLine;
import dev.assemblyline.model.Period;
import dev.assemblyline.model.Stage;
import dev.assemblyline.utils.Configuration;
import dev.assemblyline.utils.Constants;
import dev.assemblyline.utils.TimeCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por dar suporte à construção e lógica de funcionamento das {@link AssemblyLineGenerator}.
 */
public class AssemblyLineGenerator {

    /**
     * Construtor.
     */
    public AssemblyLineGenerator() {
    }

    /**
     * A partir da lista de atividades recuperadas, extrai a lista de linhas de produção que se consegue criar. Isso
     * já considera os períodos de manhã, lanche, tarde e a ginástica laboral.
     *
     * @param activities Lista de atividades.
     * @return Lista de linhas de produção.
     */
    public List<AssemblyLine> extractAssemblyLinesByActivities(List<Activity> activities) {
        final Configuration c = Configuration.getInstance();
        int morningDuration = TimeCalculator.getDurationBetween(c.getMorningBegin(), c.getMorningEnd(),
                c.getDateFormat());
        int afternoonDuration = TimeCalculator.getDurationBetween(c.getAfternoonBegin(), c.getAfternoonEndRange()[0],
                c.getDateFormat());
        int afternoonTolerance = TimeCalculator.getDurationBetween(c.getAfternoonEndRange()[0],
                c.getAfternoonEndRange()[1], c.getDateFormat());

        // Criar agrupamentos de atividades com duração igual à menor duração encontrada
        List<GroupOfActivity> morningGroups = groupTheActivitiesByDurationSum(activities, morningDuration, 0);
        List<Activity> nonGroupedActivities = new ArrayList<>(activities);
        morningGroups.forEach(group -> nonGroupedActivities.removeAll(group.activities));
        List<GroupOfActivity> afternoonGroups = groupTheActivitiesByDurationSum(nonGroupedActivities, afternoonDuration,
                afternoonTolerance);

        // Redistribui as atividades para que
        redistributeActivities(morningGroups, afternoonGroups);

        List<Activity> remainingGroupedActivities = new ArrayList<>(activities);
        morningGroups.forEach(group -> remainingGroupedActivities.removeAll(group.activities));
        afternoonGroups.forEach(group -> remainingGroupedActivities.removeAll(group.activities));
        addNonGroupedActivitiesInGroup(afternoonGroups, afternoonDuration, afternoonTolerance,
                remainingGroupedActivities);

        List<AssemblyLine> assemblyLineList = new ArrayList<>();
        for (int i = 0; i < morningGroups.size(); i++) {
            GroupOfActivity morningGroup = morningGroups.get(i);
            GroupOfActivity afternoonGroup = afternoonGroups.get(i);

            Period morning = createPeriod(Constants.MORNING_PERIOD_TITLE, morningGroup, c.getMorningBegin(),
                    c.getMorningEnd(), null);
            Period lunch = new Period(Constants.LUNCH_PERIOD_TITLE, null, c.getLunchBegin(), c.getLunchEnd(), null);
            Period afternoon = createPeriod(Constants.AFTERNOON_PERIOD_TITLE, afternoonGroup, c.getAfternoonBegin(),
                    c.getAfternoonEndRange()[0], c.getAfternoonEndRange()[1]);
            Period laborGymnastics = createLaborGymnastics(afternoon);
            assemblyLineList.add(new AssemblyLine(morning, lunch, afternoon, laborGymnastics));
        }
        return assemblyLineList;
    }

    /**
     * Agrupa as atividades, onde cada gropo possui uma série de atividades com a soma das durações entre duration e
     * duration+tolerance.
     *
     * @param activities Lista de atividades.
     * @param duration   Soma da duração das atividades.
     * @param tolerance  Tolerância da soma da duração das atividades.
     * @return Lista de grupos de atividades cuja soma das durações está entre duration e duration+tolerance.
     */
    private List<GroupOfActivity> groupTheActivitiesByDurationSum(List<Activity> activities, int duration,
                                                                  int tolerance) {
        List<GroupOfActivity> groupOfActivityList = new ArrayList<>();
        List<Activity> mutableActivityList = new ArrayList<>(activities);
        while (!mutableActivityList.isEmpty()) {
            GroupOfActivity groupOfActivity = new GroupOfActivity();
            List<Activity> activitiesToRemove = new ArrayList<>();
            int sum = 0;
            for (Activity activity : mutableActivityList) {
                sum += activity.getDurationInMinute();
                if (sum < duration + tolerance) {
                    groupOfActivity.activities.add(activity);
                    activitiesToRemove.add(activity);
                } else if (sum > duration + tolerance) {
                    sum -= activity.getDurationInMinute();
                } else if (sum == duration + tolerance) {
                    groupOfActivity.activities.add(activity);
                    activitiesToRemove.add(activity);
                    groupOfActivityList.add(groupOfActivity);
                    groupOfActivity = new GroupOfActivity();
                    sum = 0;
                }
            }
            if (tolerance > 0 && !groupOfActivity.activities.isEmpty() && sum >= duration && sum <= tolerance) {
                groupOfActivityList.add(groupOfActivity);
            }
            mutableActivityList.removeAll(activitiesToRemove);
        }
        return groupOfActivityList;
    }

    /**
     * Adiciona as atividades ainda não agrupadas em um grupo.
     *
     * @param groupOfActivityList  Grupo de atividades.
     * @param duration             Soma das durações das atividades.
     * @param tolerance            Tolerância da duração.
     * @param nonGroupedActivities Lista de atividades não agrupadas.
     */
    private void addNonGroupedActivitiesInGroup(List<GroupOfActivity> groupOfActivityList, int duration, int tolerance,
                                                List<Activity> nonGroupedActivities) {
        for (Activity remainingGroupedActivity : nonGroupedActivities) {
            for (GroupOfActivity groupOfActivity : groupOfActivityList) {
                int sum = 0;
                for (Activity activity : groupOfActivity.activities) {
                    sum += activity.getDurationInMinute();
                }
                if ((sum + remainingGroupedActivity.getDurationInMinute()) < duration + tolerance) {
                    groupOfActivity.activities.add(remainingGroupedActivity);
                }
            }
        }
    }

    /**
     * Redistribui as atividades entre os grupos da manhã e da tarde na tentativa de criar linhas de produções válidas.
     *
     * @param morningGroups Grupos de atividades da manhã.
     * @param afternoonGroups Grupos de atividades da tarde.
     */
    private void redistributeActivities(List<GroupOfActivity> morningGroups, List<GroupOfActivity> afternoonGroups) {
        if (morningGroups.size() > afternoonGroups.size()) {
            for (int i = 0; i < Math.abs(morningGroups.size() - afternoonGroups.size()); i++) {
                afternoonGroups.add(morningGroups.get(i));
                morningGroups.remove(i);
                if (morningGroups.size() == afternoonGroups.size()) {
                    break;
                }
            }
        } else if (morningGroups.size() < afternoonGroups.size()) {
            for (int i = 0; i < Math.abs(morningGroups.size() - afternoonGroups.size()); i++) {
                afternoonGroups.remove(i);
                if (morningGroups.size() == afternoonGroups.size()) {
                    break;
                }
            }
        }
    }

    /**
     * Cria um período a ser adicionado na linha de produção.
     *
     * @param title        Título do período.
     * @param group        Grupo de atividades.
     * @param initialTime  Tempo inicial.
     * @param endTimeStart Limite inferior do tempo final
     * @param endTimeEnd   Limite superior do tempo final.
     * @return Período.
     */
    private Period createPeriod(String title, GroupOfActivity group, String initialTime, String endTimeStart,
                                String endTimeEnd) {
        List<Stage> stageList = new ArrayList<>();
        String currentTime = initialTime;
        for (Activity activity : group.activities) {
            stageList.add(new Stage(currentTime, activity));
            currentTime = TimeCalculator.getNextTime(currentTime, activity.getDurationInMinute(),
                    Configuration.getInstance().getDateFormat());
        }
        return new Period(title, stageList, initialTime, endTimeStart, endTimeEnd);
    }

    /**
     * Cria o período da ginástica laboral.
     *
     * @param afternoon Período da tarde.
     * @return Período da ginástica laboral
     */
    private Period createLaborGymnastics(Period afternoon) {
        final Stage stage = afternoon.getStageList().stream().max((o1, o2) -> TimeCalculator
                .compare(o1.getStartTime(), o2.getStartTime(), Configuration.getInstance().getDateFormat())).get();
        return new Period(Constants.LABOR_GYMNASTIC_TITLE, null, TimeCalculator
                .getNextTime(stage.getStartTime(), stage.getActivity().getDurationInMinute(),
                        Configuration.getInstance().getDateFormat()), null, null);
    }

    /**
     * Grupo de atividades. Este grupo permite unir atividades em comum.
     */
    private static class GroupOfActivity {

        /** Lista de atividades do grupo. */
        private List<Activity> activities;

        /**
         * Construtor.
         */
        private GroupOfActivity() {
            activities = new ArrayList<>();
        }
    }
}
