package com.flytxt.parser.lookup;

import java.io.File;

import org.springframework.stereotype.Component;

import com.flytxt.parser.lookup.node.Node;
import com.flytxt.parser.marker.MarkerFactory;

@Component
public class MatchKey<T> extends Lookup<T> {

    private final Node<T> node = new Node<>();

    public MatchKey(final MarkerFactory mf) {
        this.mf = mf;
    }

    public MatchKey(final String file) {
        this.fileName = file;
        loadFromFile();
    }

    public MatchKey(final File file, MarkerFactory mf) {
        this.fileName = file.getAbsolutePath();
        this.mf = mf;
        loadFromFile();
    }

    @Override
    public void load(final byte[] key, final T val) {
        node.add(key, val);
    }

    @Override
    public void bake() {
    }

    @Override
    public T get(final byte[] search) {
        return node.traverse(search);
    }
}
