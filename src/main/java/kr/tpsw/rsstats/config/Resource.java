package kr.tpsw.rsstats.config;

public abstract class Resource {
    private String name;

    public Resource(String name) {
        this.name = name;
    }

    public abstract <T> T getItem(String key) throws ItemNotFoundException;

    public abstract <T> T getItem(String key, T defaultItem);

    public String getName() {
        return this.name;
    }
}
