package cz.muni.fi.pa165.monsterslayers.exception;

public class MonsterSlayersException extends RuntimeException {
    public MonsterSlayersException() {
        super();
    }

    public MonsterSlayersException(String message) {
        super(message);
    }

    public MonsterSlayersException(String message, Throwable cause) {
        super(message, cause);
    }

    public MonsterSlayersException(Throwable cause) {
        super(cause);
    }

    protected MonsterSlayersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
