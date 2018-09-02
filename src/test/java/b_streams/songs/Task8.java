package b_streams.songs;

import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class Task8 {
    public static void main(String[] args) {

        Object you = null;
        Map<Integer, String> everything = new HashMap<>();
        if (new Random().nextBoolean()) you = new Object();


        //---------------------------------

        if (you == null){
            everything = everything.entrySet().stream()
                    .collect(toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (v1, v2) -> v1,
                            WeakHashMap::new
                    ));
        }

        System.gc();

        //---------------------------------

        everything.toString();

    }
}
