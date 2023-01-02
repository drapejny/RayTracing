package by.slizh.raytracing.entity;

import by.slizh.raytracing.math.Ray;
import by.slizh.raytracing.math.Vector3;

import java.awt.*;

public abstract class Solid {
    protected Vector3 position;
    protected Color color;
    protected float reflectivity;

    public Solid(Vector3 position, Color color, float reflectivity) {
        this.position = position;
        this.color = color;
        this.reflectivity = reflectivity;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public abstract Vector3 calculateIntersection(Ray ray);

    public abstract Vector3 calculateNormal(Ray ray);


}
