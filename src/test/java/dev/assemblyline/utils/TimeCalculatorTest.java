package dev.assemblyline.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

/**
 * Classe de teste da classe {@link TimeCalculator} para cálculo temporal.
 */
public class TimeCalculatorTest {

    /**
     * Teste que avalia o caminho feliz do método getDurationBetween.
     */
    @Test
    public void should_ReturnDurationBetweenTwoTimes() {
        String a = "00:00";
        String b = "01:43";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int expected = 103;
        final int actual = TimeCalculator.getDurationBetween(a, b, simpleDateFormat);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Teste que avalia o caminho feliz do método getNextTime.
     */
    @Test
    public void should_ReturnTheSumOfStringTimeWithIntDuration() {
        String lastTime = "00:00";
        int duration = 181;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String expected = "03:01";
        final String actual = TimeCalculator.getNextTime(lastTime, duration, simpleDateFormat);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Teste que avalia o caminho feliz do método compare quando os valores são iguais.
     */
    @Test
    public void should_ReturnZero_When_TimesAreEquals() {
        String o1 = "10:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int expected = 0;
        int actual = TimeCalculator.compare(o1, o1, simpleDateFormat);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Teste que avalia o caminho feliz do método compare quando o1 é menor do que o2.
     */
    @Test
    public void should_ReturnNegative_When_o1IsSmallerThanO2() {
        String o1 = "9:00";
        String o2 = "10:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int actual = TimeCalculator.compare(o1, o2, simpleDateFormat);
        Assert.assertTrue(actual < 0);
    }

    /**
     * Teste que avalia o caminho feliz do método compare quando o1 é maior do que o2.
     */
    @Test
    public void should_ReturnPositive_When_o1IsSmallerThanO2() {
        String o1 = "10:00";
        String o2 = "9:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int actual = TimeCalculator.compare(o1, o2, simpleDateFormat);
        Assert.assertTrue(actual > 0);
    }
}
