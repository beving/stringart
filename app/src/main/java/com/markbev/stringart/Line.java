package com.markbev.stringart;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

/**A Line contains two points.  It contains a unique object to paint with.
 *    Therefore giving a line unique attributes like: a different color, size, solid/dotted, etc.
 * Created by e62032 on 4/27/2016.
 */
public class Line implements Parcelable {

    private final Point startPoint;
    private final Point endPoint;
    private int color = Color.RED; //Default
    private int thickness = 2;

    public Line(Point startPoint, Point endPoint, int color, int thickness) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.color = color;
        this.thickness = thickness;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(color);
        out.writeParcelable(startPoint , flags);
        out.writeParcelable(endPoint, flags);
    }

        public static final Parcelable.Creator<Line> CREATOR
                = new Parcelable.Creator<Line>() {
            public Line createFromParcel(Parcel in) {
                return new Line(in);
            }

            public Line[] newArray(int size) {
                return new Line[size];
            }
        };

        private Line(Parcel in) {
            setColor(in.readInt());
            startPoint = in.readParcelable(Point.class.getClassLoader());
            endPoint = in.readParcelable(Point.class.getClassLoader());
        }

}
