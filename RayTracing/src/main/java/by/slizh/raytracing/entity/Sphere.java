package by.slizh.raytracing.entity;

import by.slizh.raytracing.Main;
import by.slizh.raytracing.math.Ray;
import by.slizh.raytracing.math.Vector3;

import java.awt.*;
import java.util.Objects;

public class Sphere extends Solid {

    private float radius;

    public Sphere(Vector3 position, Color color, float radius, float reflectivity) {
        super(position, color, reflectivity);
        this.radius = radius;
    }

    @Override
    public Vector3 calculateIntersection(Ray ray) {
        Vector3 L = position.subtract(ray.getOrigin());
        float tc = Vector3.dotProduct(L, ray.getDirection());
        if (tc < 0.0f) {
            return null;
        }
        float l = L.length();
        float d2 = l * l - tc * tc;
        float radius2 = radius * radius;
        if (d2 > radius2) {
            return null;
        }
        float t1c = (float) Math.sqrt(radius2 - d2);
        if (t1c > 0.0f) {
            return ray.getOrigin().add(ray.getDirection().multiply(tc - t1c));
        } else {
            return null;
        }
    }

    @Override
    public Vector3 calculateNormal(Ray ray) {
        Vector3 intersection = calculateIntersection(ray);
        Vector3 normal = Vector3.normalize(intersection.subtract(position));
        return normal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sphere sphere)) return false;
        if (!super.equals(o)) return false;
        return Float.compare(sphere.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "radius=" + radius +
                ", position=" + position +
                ", color=" + color +
                ", reflectivity=" + reflectivity +
                '}';
    }
}
