package jp.daich.diffsellect.common.exception;

/**
 * このアプリから発生した例外だと識別するための例外クラス
 */
public class DiffSellectException extends RuntimeException {
    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 5488758372729199373L;

    public DiffSellectException() {
        super();
    }

    public DiffSellectException(String message) {
        super(message);
    }

    DiffSellectException(String message, Throwable cause) {
        super(message, cause);
    }

    DiffSellectException(Throwable cause) {
        super(cause);
    }
}
