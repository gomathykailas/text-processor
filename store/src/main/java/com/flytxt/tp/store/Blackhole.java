package com.flytxt.tp.store;


import java.io.IOException;

import com.flytxt.tp.marker.Marker;


public class Blackhole implements Store {

    public Blackhole(String file, String... headers) {
    }

    @Override
    public void save(final byte[] data, String fileName, final Marker... markers) throws IOException {
    }

    @Override
    public String done() throws IOException {
        return null;
    }

    @Override
    public void set(String fileName) {
    }
}
