package com.uanid.minecraft.config;

public interface ResourceSerializer {
    void parse(byte[] source);

    byte[] serialize();
}
