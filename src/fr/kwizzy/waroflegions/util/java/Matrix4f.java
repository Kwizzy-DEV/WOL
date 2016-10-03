package fr.kwizzy.waroflegions.util.java;


import fr.kwizzy.waroflegions.util.java.bistream.BiStream;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Matrix4f {
    private float[][] matrix;

    public Matrix4f() {
        this.matrix = new float[4][4];
    }

    public Matrix4f set(int row , int column,float value){
        matrix[row][column] = value;
        return this;
    }

    public float get(int row , int column){
        return matrix[row][column];
    }

    public Matrix4f add(int row , int column,float value){
        set(row , column , get(row , column) + value);
        return this;
    }

    public Matrix4f multiply(int row , int column,float value){
        set(row , column , get(row , column) * value);
        return this;
    }

    public Matrix4f change(int row , int column,float value){
        if(get(row , column) == 0)set(row , column , value);
        else multiply(row , column , value);
        return this;
    }

    public Matrix4f translate(Vector translation){
        return add(0 , 3 , (float) translation.getX()).add(1 , 3 , (float) translation.getY())
                .add(2 , 3 , (float) translation.getZ());
    }

    public Matrix4f scale(Vector scale){
        return change(0 , 0 , (float) scale.getX()).change(1 , 1 , (float) scale.getY())
                .change(2 , 2 , (float) scale.getZ());
    }

    public Matrix4f rotateX(float angleX){
        float cosX = (float) cos(toRadians(angleX));
        float sinX = (float) sin(toRadians(angleX));
        return multiply(1, 1 , cosX).multiply(2 , 2 , cosX).
                multiply(2, 1 , sinX).multiply(1 , 2 , -sinX);
    }

    public Matrix4f rotateY(float angleY){
        float cosY = (float) cos(toRadians(angleY));
        float sinY = (float) sin(toRadians(angleY));
        return change(0, 0 , cosY).change(1 , 1 , cosY).
                change(1, 0 , sinY).change(0 , 1 , -sinY);
    }

    public Matrix4f rotateZ(float angleZ){
        float cosZ = (float) cos(toRadians(angleZ));
        float sinY = (float) sin(toRadians(angleZ));
        return change(0, 0 , cosZ).change(2 , 2 , cosZ).
                change(2, 0 , sinY).change(0 , 2 , -sinY);
    }

    public Vector multiply(Vector v){
        return new Vector(v.getX()*get(0, 0) + v.getY()*get(0, 1) + v.getZ()*get(0, 2) + get(0, 3),
                v.getX()*get(1, 0) + v.getY()*get(1, 1) + v.getZ()*get(1, 2) + get(1, 3),
                v.getX()*get(2, 0) + v.getY()*get(2, 1) + v.getZ()*get(2, 2) + get(2, 3));
    }

    public Matrix4f clone(){
        Matrix4f m = new Matrix4f();
        for(int row = 0 ; row < 4 ; row++)
            for(int column = 0 ; column < 4 ; column++)m.set(row , column , get(row , column));
        return m;
    }

    public Matrix4f copy(Matrix4f m){
        matrix = Arrays.copyOf(m.matrix , m.matrix.length);
        return this;
    }


    @Override
    public String toString() {
        return String.join(",",Arrays.stream(matrix).map(Arrays::toString).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Matrix4f &&
                BiStream.wrap(matrix , ((Matrix4f)o).matrix).parallel().filter(Arrays::equals).count() == 4;
    }

    public boolean isIdentity(){
        return equals(Matrix4f.identity());
    }

    public boolean isZero(){
        return equals(new Matrix4f());
    }


    public static Matrix4f identity(){
        Matrix4f m = new Matrix4f();
        for(int i = 0 ; i < 4 ; i++) if(m.get(i , i) == 0)m.set(i , i ,1);
        return m;
    }

}
