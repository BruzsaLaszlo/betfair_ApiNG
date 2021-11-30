package bruzsal.betfair.navigation;

import java.lang.reflect.Field;
import java.util.List;

public abstract class Child {

    protected final String id;

    protected final String name;

    protected int depth;

    protected Child parent;

    protected Child(int depth, String id, String name) {
        this.depth = depth;
        this.id = id;
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    protected void getAllData(StringBuilder inputOutputStringBuilder, int depth) {

        inputOutputStringBuilder.append(NavigationData.SPACES[this.depth]).append(this).append("\n");
        if (depth >= this.depth) {
            for (Field f : this.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getType().isAssignableFrom(List.class)) {
                    try {
                        ((List<? extends Child>) f.get(this))
                                .forEach(navigationData ->
                                        navigationData.getAllData(inputOutputStringBuilder, depth));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public Child getParent() {
        return parent;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDepth() {
        return depth;
    }

}
