package com.flytxt.tp.lookup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.flytxt.tp.lookup.Search;

public class SearchLookupPerfTest {

    // @Test
    public void perfTest() {
        class Datum {

            byte[] key;

            String value;

            public Datum(byte[] key, String value) {
                super();
                this.key = key;
                this.value = value;
            }
        }
        Search<String> search = new Search<>("searchdata.csv");
        ArrayList<Datum> data = new ArrayList<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            File file = new File(classLoader.getResource("searchdata.csv").getFile());
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tt = line.split(",");
                if (tt.length == 2)
                    if (tt[0].trim().length() > 0)
                        data.add(new Datum(tt[0].trim().getBytes(), tt[1]));
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long start = System.nanoTime();
        for (Datum d : data)
            search.load(d.key, d.value);
        long end = System.nanoTime();
        System.out.println("total time nano:" + (end - start) + " count: " + data.size() + " inserts/sec:" + ((end - start) / data.size()));

        start = System.currentTimeMillis();
        @SuppressWarnings("unused")
        String str;
        for (Datum d : data)
            str = search.get(d.key);
        end = System.currentTimeMillis();
        System.out.println("total time:" + (end - start) + " count: " + data.size() + " gets/sec:" + ((end - start) / data.size()));

    }
}
