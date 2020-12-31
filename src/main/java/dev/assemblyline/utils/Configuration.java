package dev.assemblyline.utils;

import java.text.SimpleDateFormat;

/**
 * Consfiguração do sistema que deve ser usada para consultar os valores padrão.
 */
public class Configuration {

    /** instância singleton. */
    private static Configuration instance;

    /**
     * Construtor privado.
     */
    private Configuration() {
    }

    /**
     * Recupera a instância única deste Configurator.
     *
     * @return {@link #instance}
     */
    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    /** Formato de hora. */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    /** Hora de inicio do período da manhã linhas de produção. */
    private final String morningBegin = "09:00";

    /** Hora de fim do período da manhã linhas de produção. */
    private final String morningEnd = "12:00";

    /** Hora de inicio do período de lanche das linhas de produção. */
    private final String lunchBegin = "12:00";

    /** Hora de fim do período de lanche das linhas de produção. */
    private final String lunchEnd = "13:00";

    /** Hora de inicio do período da tarde linhas de produção. */
    private final String afternoonBegin = "13:00";

    /** Range da hora de fim do período da tarde linhas de produção. */
    private final String[] afternoonEndRange = {"16:00", "16:59"};

    /**
     * @return {@link #dateFormat}
     */
    public SimpleDateFormat getDateFormat() {
        return this.dateFormat;
    }

    /**
     * @return {@link #morningBegin}
     */
    public String getMorningBegin() {
        return this.morningBegin;
    }

    /**
     * @return {@link #morningEnd}
     */
    public String getMorningEnd() {
        return this.morningEnd;
    }

    /**
     * @return {@link #lunchBegin}
     */
    public String getLunchBegin() {
        return this.lunchBegin;
    }

    /**
     * @return {@link #lunchEnd}
     */
    public String getLunchEnd() {
        return this.lunchEnd;
    }

    /**
     * @return {@link #afternoonBegin}
     */
    public String getAfternoonBegin() {
        return this.afternoonBegin;
    }

    /**
     * @return {@link #afternoonEndRange}
     */
    public String[] getAfternoonEndRange() {
        return this.afternoonEndRange;
    }
}
