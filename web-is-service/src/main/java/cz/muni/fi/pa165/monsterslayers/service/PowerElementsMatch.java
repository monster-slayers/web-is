package cz.muni.fi.pa165.monsterslayers.service;

/**
 * Class representing match between hero's power elements and monster type's weaknesses
 * 
 * @author Tomáš Richter
 */
public class PowerElementsMatch {
    private final int usefulElementsCount;
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
        if (usefulElementsCount > other.getUsefulElementsCount()) {
            return true;
        }
        if (usefulElementsCount == other.getUsefulElementsCount()) {
            return uselessElementsCount < other.getUselessElementsCount();
        }
        return false;
    }
}
