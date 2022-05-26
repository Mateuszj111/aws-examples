package com.mj;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtility {
    public static int nextInt(int bound) {
        if (bound == 0){
            bound = 1;
        }else if (bound < 0){
            bound = (-1)*bound;
        }

        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static double nextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }
}
