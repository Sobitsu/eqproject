package org.hc.wm.actives;

/**
 * @author Dale
 */

public enum ActiveType {
    STOCK("Stock"),
    BONDS("Bonds"),
    METALS("Metals");
    private final String value;

    ActiveType(String v) {
        value = v;
    }
    public String value() {
        return value;
    }
    public static ActiveType fromValue(String v) {
        for (ActiveType c: ActiveType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
