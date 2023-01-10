package by.slizh.raytracing.entity;

import by.slizh.raytracing.math.Ray;
import by.slizh.raytracing.math.Vector3;

import java.awt.*;
import java.util.Objects;

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

    public void setColor(Color color) {
        this.color = color;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public abstract Vector3 calculateIntersection(Ray ray);

    public abstract Vector3 calculateNormal(Ray ray);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Solid solid)) return false;
        return Float.compare(solid.getReflectivity(), getReflectivity()) == 0 && Objects.equals(getPosition(), solid.getPosition()) && Objects.equals(getColor(), solid.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getColor(), getReflectivity());
    }
}
