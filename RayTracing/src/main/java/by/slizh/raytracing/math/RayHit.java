package by.slizh.raytracing.math;

import by.slizh.raytracing.entity.Solid;

import java.util.Objects;

public class RayHit {

    private Vector3 hitPosition;
    private Ray ray;
    private Vector3 normal;
    private Solid solid;

    public RayHit(Vector3 hitPosition, Ray ray, Vector3 normal, Solid solid) {
        this.hitPosition = hitPosition;
        this.ray = ray;
        this.normal = normal;
        this.solid = solid;
    }

    public Vector3 getHitPosition() {
        return hitPosition;
    }

    public void setHitPosition(Vector3 hitPosition) {
        this.hitPosition = hitPosition;
    }

    public Ray getRay() {
        return ray;
    }

    public void setRay(Ray ray) {
        this.ray = ray;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public Solid getSolid() {
        return solid;
    }

    public void setSolid(Solid solid) {
        this.solid = solid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RayHit rayHit = (RayHit) o;
        return Objects.equals(hitPosition, rayHit.hitPosition) && Objects.equals(ray, rayHit.ray) && Objects.equals(normal, rayHit.normal) && Objects.equals(solid, rayHit.solid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hitPosition, ray, normal, solid);
    }

    @Override
    public String toString() {
        return "RayHit{" +
                "hitPosition=" + hitPosition +
                ", ray=" + ray +
                ", normal=" + normal +
                ", solid=" + solid +
                '}';
    }
}
