package com.marksoft.stringart;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

/**A Line contains two points.  It contains a unique object to paint with.
 *    Therefore giving a line unique attributes like: a different color, size, solid/dotted, etc.
 * Created by e62032 on 4/27/2016.
 */
public class Line implements Parcelable {

    private Point startPoint;
    private Point endPoint;
    private int color = Color.RED; //Default

    public Line(Point startPoint, Point endPoint,  int color) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.color = color;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(color);
        out.writeParcelable((Parcelable) startPoint , flags);
        out.writeParcelable((Parcelable) endPoint, flags);
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
