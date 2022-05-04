package navigation;

import lombok.Getter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * A ROOT group node has one or many EVENT_TYPE nodes
 * <p>
 * An EVENT_TYPE node has zero, one or many GROUP nodes
 * <p>
 * An EVENT_TYPE node has zero, one or many EVENT nodes
 * <p>
 * A Horse Racing EVENT_TYPE node has zero, one or many RACE nodes
 * <p>
 * A RACE node has one or many MARKET nodes
 * <p>
 * A GROUP node has zero, one or many EVENT nodes
 * <p>
 * A GROUP node has zero, one or many GROUP nodes
 * <p>
 * An EVENT node has zero, one or many MARKET nodes
 * <p>
 * An EVENT node has zero, one or many GROUP nodes
 * <p>
 * An EVENT node has zero, one or many EVENT nodes
 */
@Getter
public abstract class Node {


    protected final String id;

    protected final String name;

    protected int depth;

    protected Node parent;

    protected Node(int depth, String id, String name) {
        this.depth = depth;
        this.id = id;
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    protected void getAllData(StringBuilder inputOutputStringBuilder, int depth) {

        inputOutputStringBuilder.append(SPACES[this.depth]).append(this).append("\n");
        if (depth >= this.depth) {
            for (Field f : this.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getType().isAssignableFrom(List.class)) {
                    try {
                        ((List<? extends Node>) f.get(this))
                                .forEach(navigationData -> navigationData.getAllData(inputOutputStringBuilder, depth));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static final String[] SPACES = new String[10];

    static {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            SPACES[i] = sb.toString();
            sb.append("   > ");
        }
    }

}
