package cz.muni.fi.pa165.monsterslayers.service.utils;

import java.util.Collection;

/**
 * Class representing match between hero's power elements and monster type's weaknesses
 *
 * @author Tomáš Richter
 */
public class PowerElementsMatch {
    //number of elements that are both in hero's skills and monster weaknesses
    //plays primary role
    private final int usefulElementsCount;

    //number of elements that ca be used by hero, but monster type is not weak to them
    //plays secondary role when primary useful elements are same
    private final int uselessElementsCount;

    public PowerElementsMatch(int usefulElementsCount, int uselessElementsCount) {
        this.usefulElementsCount = usefulElementsCount;
        this.uselessElementsCount = uselessElementsCount;
    }

    public int getUsefulElementsCount() {
        return usefulElementsCount;
    }

    public int getUselessElementsCount() {
        return uselessElementsCount;
    }

    public boolean isMoreSuitable(PowerElementsMatch other) {
        if (other == null || usefulElementsCount > other.getUsefulElementsCount()) {
            return true;
        }
        return usefulElementsCount == other.getUsefulElementsCount()
                && uselessElementsCount < other.getUselessElementsCount();
    }

    public PowerElementsMatch multiplyByMonsterCount(int count) {
        return new PowerElementsMatch(usefulElementsCount * count, uselessElementsCount * count);
    }

    public static PowerElementsMatch sumMatches(Collection<PowerElementsMatch> matches) {
        int sumUseful = 0;
        int sumUseless = 0;
        for (PowerElementsMatch match : matches) {
            sumUseful += match.getUsefulElementsCount();
            sumUseless += match.getUselessElementsCount();
        }
        return new PowerElementsMatch(sumUseful,sumUseless);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PowerElementsMatch)) {
            return false;
        }
        final PowerElementsMatch other = (PowerElementsMatch) obj;
        return usefulElementsCount == other.usefulElementsCount && uselessElementsCount == other.uselessElementsCount;
    }
}
