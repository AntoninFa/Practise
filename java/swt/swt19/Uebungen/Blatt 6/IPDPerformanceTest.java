package org.iMage.HDrize.parallel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.lang3.time.StopWatch;
import org.iMage.HDrize.HDrize;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.matrix.MatrixCalculator;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Performanzermittlung
 *
 * @author tk
 * @author Mathias
 * @author weigelt
 * @author Dominik Fuchss
 */
public class IPDPerformanceTest {

  /** Anzahl der Messläufe. */
  private static final int NUMLOOPS = 5;

  /** Alle zu testenden Fadenanzahlen. */
  private Integer[] threads = { 1, 2, 4, 8, 16, 32, 64, 128 };

  private byte[] image1;
  private byte[] image2;
  private byte[] image3;

  private static int SAMPLES = 500;
  private static float LAMBDA = 30;

  private static PrintStream OUT;

  @BeforeClass
  public static void disableSysOut() {
    IPDPerformanceTest.OUT = System.out;
    System.setOut(new PrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        // DO NOTHING ..
      }
    }));
  }

  @AfterClass
  public static void enableSysOut() {
    System.setOut(IPDPerformanceTest.OUT);
  }

  @Before
  public void loadImages() throws IOException {
    this.image1 = this.getClass().getResourceAsStream("/image/image_1_10.jpg").readAllBytes();
    this.image2 = this.getClass().getResourceAsStream("/image/image_1_25.jpg").readAllBytes();
    this.image3 = this.getClass().getResourceAsStream("/image/image_1_80.jpg").readAllBytes();
  }

  @Test
  public void testPerformace() throws ImageReadException, IOException {
    long time = System.currentTimeMillis();
    this.testPerformanceSequential();
    this.testPerformanceParallel();
    System.err.println(
        "This measurement took " + ((System.currentTimeMillis() - time) / 1000) + " seconds");
  }

  private void testPerformanceParallel() throws ImageReadException, IOException {
    long[] measurements = new long[this.threads.length];

    System.err.println("Performanzmessung");
    for (int t = 0; t < this.threads.length; t++) {
      System.err.println("Starte Messung für " + this.threads[t] + " Fäden");
      int curThreadCount = this.threads[t];

      // Für mehr Messgenauigkeit :)
      StopWatch sw = new StopWatch();
      for (int loop = 0; loop < IPDPerformanceTest.NUMLOOPS; loop++) {
        // Start der Messung
        sw.reset();
        sw.start();
        this.executeParallel(curThreadCount);
        // Ende der Messung
        sw.stop();
        measurements[t] += sw.getTime();
      }
      measurements[t] /= IPDPerformanceTest.NUMLOOPS;
    }
    System.err.println("Messergebnisse:");
    System.err.println(Arrays.toString(measurements));
  }

  private void testPerformanceSequential() throws ImageReadException, IOException {
    long measurements = 0;

    System.err.println("Performanzmessung Sequential");

    // Für mehr Messgenauigkeit :)
    StopWatch sw = new StopWatch();
    for (int loop = 0; loop < IPDPerformanceTest.NUMLOOPS; loop++) {
      // Start der Messung
      sw.reset();
      sw.start();
      this.executeSequential();

      // Ende der Messung
      sw.stop();
      measurements += sw.getTime();
    }
    measurements /= IPDPerformanceTest.NUMLOOPS;
    System.err.println("Messergebnisse:");
    System.err.println(measurements);
  }

  private BufferedImage executeSequential() throws ImageReadException, IOException {

    EnhancedImage[] images = new EnhancedImage[] {
        new EnhancedImage(new ByteArrayInputStream(this.image1)),
        new EnhancedImage(new ByteArrayInputStream(this.image2)),
        new EnhancedImage(new ByteArrayInputStream(this.image3)) };

    HDrize hdrize = new HDrize();
    MatrixCalculator mc = new MatrixCalculator();
    BufferedImage hdr = hdrize.createRGB(images, IPDPerformanceTest.SAMPLES,
        IPDPerformanceTest.LAMBDA, mc);

    return hdr;
  }

  private BufferedImage executeParallel(int threads) throws ImageReadException, IOException {
    InputStream[] images = new InputStream[] { //
        new ByteArrayInputStream(this.image1), //
        new ByteArrayInputStream(this.image2), //
        new ByteArrayInputStream(this.image3) //
    };

    HDrizeParallel hdrize = new HDrizeParallel(threads);
    BufferedImage hdr = hdrize.createRGB(images, IPDPerformanceTest.SAMPLES,
        IPDPerformanceTest.LAMBDA);
    return hdr;
  }

}
