package com.marksoft.stringart;

import android.graphics.Point;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Test Point Utility class
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
/*
    @SmallTest
    public void testCalculateMaxSize() {
        Point maxPoint = PointUtility.calculateMaxSize(points);
        assertEquals(120, maxPoint.x);
        assertEquals(220, maxPoint.y);
    }

    @SmallTest
    public void testCalculateMaxSizeIsNullPoints() {
        try {
            PointUtility.calculateMaxSize(null);
            Assert.fail("Should have thrown NullPointer exception");
        }
        catch (NullPointerException e) {
            //Success
        }
    }

    @SmallTest
    public void testCalculateMaxSizeIsEmptyReturns0() {
        points = new ArrayList<>();

        Point maxPoint = PointUtility.calculateMaxSize(points);
        assertEquals(20, maxPoint.x);
        assertEquals(20, maxPoint.y);
    }
*/

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
