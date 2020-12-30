package dev.assemblyline.model;

import java.util.Objects;

/**
 * Linha de produção.
 */
public class AssemblyLine {

    /** Período da manhã. */
    private Period morning;

    /** Período do almoço. Este período não possui etapas dentro dele. */
    private Period lunch;

    /** Período da tarde. */
    private Period afternoon;

    /**
     * Período da ginástica laboral. Este período não possuirá etapas e nem definição de hora final. Este caso
     * indicará que a linha de produção pode ser encerrada já que não é possícel adicionar nenhum período depois deste.
     */
    private Period laborGymnastics;

    /**
     * Construtor.
     *
     * @param morning         {@link #morning}
     * @param lunch           {@link #lunch}
     * @param afternoon       {@link #afternoon}
     * @param laborGymnastics {@link #laborGymnastics}
     */
    public AssemblyLine(Period morning, Period lunch, Period afternoon, Period laborGymnastics) {
        this.morning = morning;
        this.lunch = lunch;
        this.afternoon = afternoon;
        this.laborGymnastics = laborGymnastics;
    }

    /**
     * @return {@link #morning}
     */
    public Period getMorning() {
        return this.morning;
    }

    /**
     * @param morning {@link #morning}
     */
    public void setMorning(Period morning) {
        this.morning = morning;
    }

    /**
     * @return {@link #lunch}
     */
    public Period getLunch() {
        return this.lunch;
    }

    /**
     * @param lunch {@link #lunch}
     */
    public void setLunch(Period lunch) {
        this.lunch = lunch;
    }

    /**
     * @return {@link #afternoon}
     */
    public Period getAfternoon() {
        return this.afternoon;
    }

    /**
     * @param afternoon {@link #afternoon}
     */
    public void setAfternoon(Period afternoon) {
        this.afternoon = afternoon;
    }

    /**
     * @return {@link #laborGymnastics}
     */
    public Period getLaborGymnastics() {
        return this.laborGymnastics;
    }

    /**
     * @param laborGymnastics {@link #laborGymnastics}
     */
    public void setLaborGymnastics(Period laborGymnastics) {
        this.laborGymnastics = laborGymnastics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssemblyLine that = (AssemblyLine) o;
        return Objects.equals(this.morning, that.morning) && Objects.equals(this.lunch, that.lunch) && Objects.equals(
                this.afternoon, that.afternoon) && Objects.equals(this.laborGymnastics, that.laborGymnastics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.morning, this.lunch, this.afternoon, this.laborGymnastics);
    }

    @Override
    public String toString() {
        return this.morning.toString() + "\n" + this.lunch.toString() + "\n" + this.afternoon.toString() + "\n"
                + this.laborGymnastics.toString();
    }
}
