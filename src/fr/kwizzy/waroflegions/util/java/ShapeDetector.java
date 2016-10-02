package fr.kwizzy.waroflegions.util.java;

import java.awt.*;
import java.util.*;

/**
 * Par Alexis le 01/10/2016.
 */

public class ShapeDetector {

    public static void main(String[] args) {
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 0);
        Point p2 = new Point(0, 1);
        Point p3 = new Point(1, 0);
        System.out.println((3^3));
    }

    public static boolean isSquare(Point... p){
        // TODO: 02/10/2016 Rewrite this method
        int size = 1;

        if(p.length != 4)
            return false;

        Point A = p[0];
        Point B = p[1];
        Point C = p[2];
        Point D = p[3];

        boolean bo= A.x == C.x && B.x == D.x && A.y == B.y && C.y == D.y;
        System.out.println(bo);
        boolean bo2 = A.x - B.x == size && A.y - C.y == size;
        System.out.println(bo2);

        return  bo && bo2;
    }

    public static boolean isLine(Point... points) {

        int number = points[0].x;
        for (int i = 0; i < points.length; i++) {
            if(number != points[i].x)
                break;
            if(i == points.length-1)
                return true;
        }
        number = points[0].y;
        for (int i = 0; i < points.length; i++) {
            if(number != points[i].y)
                break;
            if(i == points.length-1)
                return true;
        }
        return false;

    }

    public Boolean isPoint(Point... p){
        return p.length == 1;
    }

    public enum Shapes{
        SQUARE(3),
        LINE3(2),
        LINE2(1),
        POINT(0);

        int level;

        Shapes(int level) {
            this.level = level;
        }
    }
}
