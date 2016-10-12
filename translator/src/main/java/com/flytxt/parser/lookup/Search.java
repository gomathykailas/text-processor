package com.flytxt.parser.lookup;

import java.io.File;

import org.springframework.stereotype.Component;

import com.flytxt.parser.lookup.node.CharPath;
import com.flytxt.parser.lookup.node.CharacterDic;
import com.flytxt.parser.marker.MarkerFactory;

@Component
public class Search<T> extends Lookup<T> {

    private final CharacterDic<T> dictionary = new CharacterDic<>();

    public Search(final String file) {
        this.fileName = file;
        loadFromFile();
    }

    public Search(final File file, MarkerFactory mf) {
        this.fileName = file.getAbsolutePath();
        this.mf = mf;
        loadFromFile();
    }

    public Search(MarkerFactory mf) {
        this.mf = mf;
    }

    @Override
    public void load(final byte[] key, final T val) {
        for (int i = 0; i < key.length; i++) {
            final CharPath<T> path = new CharPath<>(key, i, val);
            dictionary.getCharNode(key[i]).addPath(path);
        }
    }

    @Override
    public void bake() {
        // fMap = new UnmodifiableTrie<byte[], Marker>(new PatriciaTrie(map));
    }

    @Override
    public T get(final byte[] key) {
        return dictionary.getCharNode(key[0]).getValue(key);
    }
}
