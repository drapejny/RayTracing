package by.slizh.raytracing.entity;

import by.slizh.raytracing.math.Ray;
import by.slizh.raytracing.math.Vector3;

import java.awt.*;

public class Plane extends Solid {

    private boolean checkerPattern;

    public Plane(Vector3 position, Color color, boolean checkerPattern, float reflectivity) {
        super(position, color, reflectivity);
        this.checkerPattern = checkerPattern;
    }

    @Override
    public Vector3 calculateIntersection(Ray ray) {
        float t = -(ray.getOrigin().getY() - position.getY()) / ray.getDirection().getY();
        if (t > 0 && Float.isFinite(t)) {
            return ray.getOrigin().add(ray.getDirection().multiply(t));
        }

        return null;
    }

    @Override
    public Vector3 calculateNormal(Ray ray) {
        return new Vector3(0, 1, 0);
    }
}
