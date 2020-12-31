package dev.assemblyline.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe utilitária para executar cálculos com valores temporais.
 */
public class TimeCalculator {

    /** Um minuto em milissegundos. */
    private static final int MINUTE = 1000 * 60;

    /**
     * Calcula a durção entre o tempo a e b.
     *
     * @param a          Horário inicial.
     * @param b          Horário final.
     * @param dateFormat Formato de data.
     * @return Valor inteiro da duração.
     */
    public static int getDurationBetween(String a, String b, SimpleDateFormat dateFormat) {
        long aTime = 0;
        long bTime = 0;
        try {
            aTime = dateFormat.parse(a).getTime();
            bTime = dateFormat.parse(b).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) Math.abs(bTime - aTime) / MINUTE;
    }

    /**
     * Recupera o próximo horário dado o último horário e a duração.
     *
     * @param lastTime   Último horário.
     * @param duration   Tempo de duração em minutos para avançar o horário antigo.
     * @param dateFormat Formato de data.
     * @return Horário atual.
     */
    public static String getNextTime(String lastTime, int duration, SimpleDateFormat dateFormat) {
        long nextTime = 0;
        try {
            nextTime = dateFormat.parse(lastTime).getTime() + (duration * MINUTE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(new Date(nextTime));
    }

    /**
     * Compara dois textos que representam hora.
     *
     * @param o1         Hora 1.
     * @param o2         Hora 2.
     * @param dateFormat Formato de data.
     * @return 0 se o1 == o2; maior que 0 se o1 > o2; menor que 0 se o1 < o2.
     */
    public static int compare(String o1, String o2, SimpleDateFormat dateFormat) {
        try {
            long timeO1 = dateFormat.parse(o1).getTime();
            long timeO2 = dateFormat.parse(o2).getTime();
            return (int) (timeO1 - timeO2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
