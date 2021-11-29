package bruzsal.betfair.navigation;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class Child {

    protected final String id;

    protected final String name;

    protected int depth;

    protected Child parent;

    public Child(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Child getParent() {
        return parent;
    }

    public abstract String toString();

    abstract List<List<? extends Child>> getLists();

    protected void getAllData(StringBuilder inputOutputStringBuilder, int depth) {
//        inputOutputStringBuilder.append(NavigationData.SPACES[this.depth]).append(this).append("\n");
//        if (deep >= this.depth && getLists() != null)
//            getLists().
//                    forEach(list -> list.
//                            forEach(navigationData ->
//                                    navigationData.getAllData(inputOutputStringBuilder, deep)));

        inputOutputStringBuilder.append(NavigationData.SPACES[this.depth]).append(this).append("\n");
        if (depth >= this.depth && getLists() != null) {
            for (Field f : this.getClass().getFields()) {
                f.setAccessible(true);
                if (f.getType().isInstance(new TypeToken<List<List<? extends Child>>>() {
                }.getType())) {
                    try {
                        ((List<List<? extends Child>>) f.get(this))
                                .forEach(list -> list.
                                        forEach(navigationData ->
                                                navigationData.getAllData(inputOutputStringBuilder, depth)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void printToConsole(int depth) {
        System.out.printf("%s%s%n", NavigationData.SPACES[this.depth], this);
        if (depth >= this.depth && getLists() != null) {
            System.out.println(Arrays.toString(getClass().getClasses()));
            for (Field f : getClass().getFields()) {
                f.setAccessible(true);
                if (f.getType().isAssignableFrom(new TypeToken<List<List<? extends Child>>>() {
                }.getType().getClass())) {
                    System.out.println("mindig hamis");
                    try {
                        ((List<List<? extends Child>>) f.get(this))
                                .forEach(list -> list.
                                        forEach(navigationData ->
                                                navigationData.printToConsole(depth)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("mindig hamis");
                }
            }
        }
//        System.out.printf("%s%s%n", NavigationData.SPACES[this.depth], this);
//        if (depth >= this.depth && getLists() != null)
//            getLists().
//                    forEach(list -> list.
//                            forEach(navigationData ->
//                                    navigationData.printToConsole(depth)));
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

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
