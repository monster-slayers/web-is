package cz.muni.fi.pa165.monsterslayers.service.utils;

import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class PowerElementsMatchTest {
    @Test
    public void testGetters(){
        int expectedUsefulMatches = 65;
        int expectedUselessMatches = 65;
        PowerElementsMatch powerElementsMatch = new PowerElementsMatch(expectedUsefulMatches, expectedUselessMatches);

        Assert.assertEquals(expectedUsefulMatches, powerElementsMatch.getUsefulElementsCount());
        Assert.assertEquals(expectedUselessMatches, powerElementsMatch.getUselessElementsCount());
    }

    @Test
    public void testIsMoreSuitable(){
        PowerElementsMatch weakerMatch = new PowerElementsMatch(1, 1);
        PowerElementsMatch match = new PowerElementsMatch(2, 1);
        PowerElementsMatch strongerMatch = new PowerElementsMatch(3, 1);
        PowerElementsMatch similarMatch = new PowerElementsMatch(2, 0);

        Assert.assertTrue(match.isMoreSuitable(weakerMatch));
        Assert.assertFalse(weakerMatch.isMoreSuitable(match));

        Assert.assertTrue(strongerMatch.isMoreSuitable(match));
        Assert.assertFalse(match.isMoreSuitable(strongerMatch));

        Assert.assertTrue(strongerMatch.isMoreSuitable(weakerMatch));
        Assert.assertFalse(weakerMatch.isMoreSuitable(strongerMatch));

        Assert.assertTrue(similarMatch.isMoreSuitable(match));
        Assert.assertFalse(match.isMoreSuitable(similarMatch));
    }

    @Test
    public void testMultiplyByMonsterCount(){
        PowerElementsMatch match = new PowerElementsMatch(2, 0);

        Assert.assertEquals(new PowerElementsMatch(6, 0), match.multiplyByMonsterCount(3));
    }

    @Test
    public void testSumMatches(){
        List<PowerElementsMatch> list = new ArrayList<>();
        list.add(new PowerElementsMatch(2, 0));
        list.add(new PowerElementsMatch(1, 3));
        list.add(new PowerElementsMatch(0, 5));
        list.add(new PowerElementsMatch(3, 1));

        Assert.assertEquals(new PowerElementsMatch(6, 9), PowerElementsMatch.sumMatches(list));
    }

    @Test
    public void testEquals(){
        PowerElementsMatch equal1 = new PowerElementsMatch(3, 8);
        PowerElementsMatch equal2 = new PowerElementsMatch(3, 8);
        PowerElementsMatch not_equal1 = new PowerElementsMatch(1, 8);
        PowerElementsMatch not_equal2 = new PowerElementsMatch(3, 2);

        Assert.assertEquals(equal1, equal2);
        Assert.assertNotEquals(equal1, not_equal1);
        Assert.assertNotEquals(equal1, not_equal2);
    }
}
