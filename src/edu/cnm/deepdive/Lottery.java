package edu.cnm.deepdive;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lottery {

  private final int[] pool;
  private final Random rng;

  public static void main(String[] args) {
    // allow consumer to type numbers. size of pool and draw. third amount of draws (optional)
    int poolSize = Integer.parseInt(args[0]);
    int sampleSize = Integer.parseInt(args[1]);
    int sampleCount = (args.length > 2) ? Integer.parseInt(args[2]) : 1;
    Lottery lottery = new Lottery(poolSize);
    for (int i = 0; i < sampleCount; i++) {
      System.out.println(
          // int stream.
          Arrays.stream(lottery.draw(sampleSize))
              // transforms to sorted stream.
          .sorted()
              // takes int stream and makes int stream objects.
              .mapToObj(String::valueOf)
              // gather elements together of stream into something else, like a list, etc.
          .collect(Collectors.joining(", "))
      );
    }
  }

  public Lottery(int poolSize, Random rng) {
    this.rng = rng;
    // Stream not array nor list. We add toArray.
    pool = IntStream.rangeClosed(1, poolSize).toArray();
  }

  public Lottery(int poolSize) {
    // Invokes constructor on top. this().
  this(poolSize, new SecureRandom());
  }

  public int[] draw(int sampleSize) {
    if (sampleSize > pool.length) {
      throw new IllegalArgumentException("Sample size must not exceed pool size");
    }
    for (int i = pool.length - 1; i >= pool.length - sampleSize; i--) {
      // Up to and including i.
      int swapIndex = rng.nextInt(i + 1);
      // Temp made to swap.
      int temp = pool[swapIndex];
      // Put in swapped.
      pool[swapIndex] = pool[i];
      pool[i] = temp;
    }
    return Arrays.copyOfRange(pool, pool.length - sampleSize, pool.length);
  }

}
