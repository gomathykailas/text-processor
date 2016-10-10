package com.flytxt.parser.lookup;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.collections4.trie.UnmodifiableTrie;
import org.springframework.stereotype.Component;

import com.flytxt.parser.marker.MarkerFactory;

@Component
public class PrefixLookupIgnoreCase<T> extends Lookup<T> {

    private final Map<String, T> map = new HashMap<>();

    private UnmodifiableTrie<String, T> fMap;

    private final byte capsA = 'A';

    private final byte capsZ = 'Z';

    public PrefixLookupIgnoreCase(final String file, MarkerFactory mf) {
        this.fileName = file;
        this.mf = mf;
        loadFromFile();
    }

    private byte[] toLower(final byte[] data1) {
        final byte[] dest = new byte[data1.length];
        int index = 0;
        for (final byte element : data1)
            if (element >= capsA && element <= capsZ)
                dest[index++] = (byte) (element - 'A' + 'a');
            else
                dest[index++] = element;
        return dest;
    }

    @Override
    public void load(final byte[] key, final T val) {
        map.put(new String(toLower(key)), val);
    }

    @Override
    public void bake() {
        fMap = new UnmodifiableTrie<>(new PatriciaTrie<>(map));
    }

    @Override
    public T get(final byte[] key) {
        return fMap.get(new String(toLower(key)));
    }

}
