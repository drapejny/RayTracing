package by.slizh.raytracing.entity;

import by.slizh.raytracing.Main;
import by.slizh.raytracing.math.Ray;
import by.slizh.raytracing.math.Vector3;

import java.awt.*;

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

//    public Vector3 test(Ray ray) {
//        float t = Vector3.dotProduct(position.subtract(ray.getOrigin()), ray.getDirection());
//        Vector3 p = ray.getOrigin().add(ray.getDirection().multiply(t));
//
//        float y = position.subtract(p).length();
//        if (y < radius) {
//            float x = (float) Math.sqrt(radius * radius - y * y);
//            float t1 = t - x;
//            if (t1 > 0) {
//                return ray.getOrigin().add(ray.getDirection().multiply(t1));
//            }
//            else {
//                return null;}
//        } else {
//            return null;
//        }
//    }
}
