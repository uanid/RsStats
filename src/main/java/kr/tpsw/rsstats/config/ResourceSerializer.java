package kr.tpsw.rsstats.config;

public interface ResourceSerializer {
    void parse(byte[] source);

    byte[] serialize();
}
