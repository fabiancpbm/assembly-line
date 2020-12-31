package dev.assemblyline.service;

import dev.assemblyline.model.Activity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de teste da classe {@link ActivityCollector} para recuperação de atividades vindas do arquivo txt.
 */
public class ActivityCollectorTest {

    /** Pasta de recursos de teste. */
    protected static final String SRC_TEST_RESOURCES = "src/test/resources/";

    /** Coletor de atividades. */
    private ActivityCollector activityCollector;

    @Before
    public void initInstance() {
        this.activityCollector = new ActivityCollector();
    }

    @Test
    public void should_ExtractTenActivities() {
        List<Activity> expected = new ArrayList<>();
        String alphabet = "ABCDEFGHIJ";
        for (char c : alphabet.toCharArray()) {
            expected.add(new Activity("Activity " + c + " 10Min", 10));
        }
        final String filePath = Paths.get(
                SRC_TEST_RESOURCES + "ActivityCollectorTest/should_ExtractTenActivities/input.txt").toAbsolutePath()
                .toString();
        final List<Activity> actual = this.activityCollector.extractActivitiesFromFile(new File(filePath));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void should_ExcludeLine_When_MoreThanOneNumberIsFound() {
        List<Activity> expected = new ArrayList<>();
        String alphabet = "ABCDEFGHIJ";
        for (char c : alphabet.toCharArray()) {
            expected.add(new Activity("Activity " + c + " 10Min", 10));
        }
        expected.remove(0);
        final String filePath = Paths.get(
                SRC_TEST_RESOURCES + "ActivityCollectorTest/should_ExcludeLine_When_MoreThanOneNumberIsFound/input.txt")
                .toAbsolutePath().toString();
        final List<Activity> actual = this.activityCollector.extractActivitiesFromFile(new File(filePath));
        Assert.assertEquals(expected, actual);
    }
}