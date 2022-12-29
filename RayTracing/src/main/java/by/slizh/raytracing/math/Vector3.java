package by.slizh.raytracing.math;

import java.util.Objects;

public class Vector3 {
    private float x;
    private float y;
    private float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Vector3 add(Vector3 vector3) {
        return new Vector3(this.x + vector3.x, this.y + vector3.y, this.z + vector3.z);
    }

    public Vector3 subtract(Vector3 vector3) {
        return new Vector3(this.x - vector3.x, this.y - vector3.y, this.z - vector3.z);
    }

    public Vector3 multiply(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 multiply(Vector3 vector3) {
        return new Vector3(this.x * vector3.x, this.y * vector3.y, this.z * vector3.z);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3 rotate(float pitch, float yaw) {
        float newY = (float) (y * Math.cos(pitch) - z * Math.sin(pitch));
        float newZ = (float) (y * Math.sin(pitch) + z * Math.cos(pitch));
        float newX = (float) (x * Math.cos(yaw) + newZ * Math.sin(yaw));
        newZ = (float) (-x * Math.sin(yaw) + newZ * Math.cos(yaw));

        return new Vector3(newX, newY, newZ);

    }

    public static Vector3 normalize(Vector3 vector3) {
        float length = vector3.length();
        return new Vector3(vector3.x / length, vector3.y / length, vector3.z / length);
    }

    public static float dotProduct(Vector3 v1, Vector3 v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static float distance(Vector3 v1, Vector3 v2){
        return (float) Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2) + Math.pow(v1.z - v2.z, 2));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Float.compare(vector3.x, x) == 0 && Float.compare(vector3.y, y) == 0 && Float.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
