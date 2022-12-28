package by.slizh.raytracing.math;

import java.util.Objects;

public class Ray {

    private Vector3 origin;
    private Vector3 direction;

    public Ray(Vector3 position, Vector3 direction) {
        // Just normalize
        if (direction.length() != 1.0f) {
            direction = Vector3.normalize(direction);
        }
        this.direction = direction;
        this.origin = position;
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public Vector3 getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(origin, ray.origin) && Objects.equals(direction, ray.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "origin=" + origin +
                ", direction=" + direction +
                '}';
    }
}
