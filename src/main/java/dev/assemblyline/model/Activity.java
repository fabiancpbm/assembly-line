package dev.assemblyline.model;

import java.util.Objects;

/**
 * Uma atividade é a unidade indicada na entrada que representa uma ocupação e possui um tempo de duração em minutos.
 * Por exemplo: Montagem do subsistema de navegação, almoço, ginástica laboral, etc. Uma atividade ainda não é uma
 * etapa de uma linha de montagem, mas pode ser usada para compor uma etapa desta linha.
 */
public class Activity {

    /**
     * Título da atividade. Todos os números nos títulos das etapas de produção representam um tempo da etapa em
     * minutos. Na ausência de números, ou a palavra "maintenance" irá aparecer (indicando pausa técnica de 15 min)
     * ou este título será composto apenas pela palavra "lunch" (que possui duração de 60 min).
     */
    private String title;

    /** Duração em minutos. */
    private int durationInMinute;

    /**
     * Construtor.
     *
     * @param title            {@link #title}
     * @param durationInMinute {@link #durationInMinute}
     */
    public Activity(String title, int durationInMinute) {
        this.title = title;
        this.durationInMinute = durationInMinute;
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
     * @return {@link #durationInMinute}
     */
    public int getDurationInMinute() {
        return this.durationInMinute;
    }

    /**
     * @param durationInMinute {@link #durationInMinute}
     */
    public void setDurationInMinute(int durationInMinute) {
        this.durationInMinute = durationInMinute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity that = (Activity) o;
        return this.durationInMinute == that.durationInMinute && Objects.equals(this.title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.durationInMinute);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
