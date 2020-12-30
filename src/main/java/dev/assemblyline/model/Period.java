package dev.assemblyline.model;

import java.util.*;

/**
 * Um período representa um intervalo de tempo que pode ser composto de etapas. Um período possui hora de inicio e
 * hora de fim, podendo ser a hora de fim representada com dois horários que definem a tolerância do final do período
 * . Por exemplo, um período pode iniciar as 13h e terminar entre 16h e 17h.
 */
public class Period {

    /**
     * Título do período. Normalmente, um período possui etapas e, portanto, a representação de um período é feita
     * listando estas etapas. Porém, na ausência de etapas, este período será representado pelo seu título.
     */
    private String title;

    /** Lista de etapas que compõem um período. Um período pode não conter nenhuma etapa. */
    private List<Stage> stageList;

    /** Horário de inicio do período. */
    private String startTime;

    /**
     * Intervalo de horário de fim do período. Existem períodos que podem possuir tolerância no seu término, ou
     * seja, podem terminar entre um horário A ou B. Se A for igual a B ou B for indefinido, então não existe
     * tolerância: existe um horário de término apenas. Além disso, se este intervalo inteiro não estiver definido e
     * {@link #stageList} estiver vazio, então nenhum {@link Period} poderá existir depois deste, configurando um
     * critério de parada na produção de períodos da linha de produção.
     */
    private String[] endTimeRange;

    /**
     * Construtor.
     *
     * @param title        {@link #title}
     * @param stageList    {@link #stageList}
     * @param startTime    {@link #startTime}
     * @param firstEndTime Primeiro horário da tolerância de término do período.
     * @param lastEndTime  Último horário da tolerância de término do período.
     */
    public Period(String title, List<Stage> stageList, String startTime, String firstEndTime, String lastEndTime) {
        this.title = title;
        this.setStageList(stageList);
        this.startTime = startTime;
        this.setEndTimeRange(firstEndTime, lastEndTime);
    }

    /**
     * @return {@link #title}
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title {@link #title}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return {@link #stageList}
     */
    public List<Stage> getStageList() {
        return this.stageList;
    }

    /**
     * Define uma lista de etapas e garante que esta lista jamais será nula.
     * @param stageList {@link #stageList}
     */
    public void setStageList(List<Stage> stageList) {
        if (stageList != null) {
            this.stageList = stageList;
        } else {
            this.stageList = new ArrayList<Stage>();
        }
    }

    /**
     * @return {@link #startTime}
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * @param startTime {@link #startTime}
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return {@link #endTimeRange}
     */
    public String[] getEndTimeRange() {
        return this.endTimeRange;
    }

    /**
     * Define os limites inicial e final da tolerância de horário de fim.
     *
     * @param firstEndTime Primeiro horário da tolerância de término do período.
     * @param lastEndTime  Último horário da tolerância de término do período.
     */
    public void setEndTimeRange(String firstEndTime, String lastEndTime) {
        this.endTimeRange = new String[]{firstEndTime, lastEndTime};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Period period = (Period) o;
        return Objects.equals(this.title, period.title) && Objects.equals(this.stageList, period.stageList) && Objects
                .equals(this.startTime, period.startTime) && Arrays.equals(this.endTimeRange, period.endTimeRange);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.title, this.stageList, this.startTime);
        result = 31 * result + Arrays.hashCode(endTimeRange);
        return result;
    }

    @Override
    public String toString() {
        // Caso o stageList seja vazio, o título deste período será usado para representá-lo.
        if (this.stageList.isEmpty()) {
            return this.startTime + " " + this.title;
        } else {
            String stageLines = "";
            for (Stage stage : this.stageList) {
                stageLines += stage.toString() + "\n";
            }
            return stageLines.substring(0, stageLines.length() - 1);
        }
    }
}
