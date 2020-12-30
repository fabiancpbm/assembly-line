package dev.assemblyline.model;

import java.util.Objects;

/**
 * Uma etapa é uma atividade que já está numa linha de produção e, portanto, possui data de inicio dentro da jornada
 * de trabalho.
 */
public class Stage {

    /** Hora inicial. */
    private String startTime;

    /** Atividade associada. */
    private Activity activity;

    /**
     * Construtor.
     *
     * @param startTime {@link #startTime}
     * @param activity  {@link #activity}
     */
    public Stage(String startTime, Activity activity) {
        this.startTime = startTime;
        this.activity = activity;
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
     * @return {@link #activity}
     */
    public Activity getActivity() {
        return this.activity;
    }

    /**
     * @param activity {@link #activity}
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stage that = (Stage) o;
        return Objects.equals(this.startTime, that.startTime) && Objects.equals(this.activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.startTime, this.activity);
    }

    @Override
    public String toString() {
        return "Ajustar para o formato 'start(HH:mm) activity.title'";
    }
}
