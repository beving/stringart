package com.marksoft.stringart;

import android.graphics.Point;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Test PointUtility class.
 * Created by e62032 on 7/26/2016.
 * @See PointUtility
 */
public class PointUtilityTest extends TestCase {

    protected List<Point> points;

    @Override
    public void setUp() throws Exception {
        points = new ArrayList<>();

        points.add(new Point(10, 10));
        points.add(new Point(11, 12));
        points.add(new Point(12, 13));
        points.add(new Point(100, 2));
        points.add(new Point(20, 10));
        points.add(new Point(20, 10));
        points.add(new Point(22, 200));
    }

    @SmallTest
      public void testToArrayReturnsCorrectNumber() {
        float[] pointsAsFloats = PointUtility.toArray(points);
        assertEquals(14, pointsAsFloats.length);
    }

    @SmallTest
    public void testToArrayReturnsValues() {
        float[] pointsAsFloats = PointUtility.toArray(points);
        assertEquals(10.0f, pointsAsFloats[0]);
        assertEquals(11.0f, pointsAsFloats[2]);
        assertEquals(12.0f, pointsAsFloats[3]);
        assertEquals(200.0f, pointsAsFloats[13]);
    }

}
