package by.slizh.raytracing.frame;

import by.slizh.raytracing.entity.Solid;
import by.slizh.raytracing.entity.Sphere;
import by.slizh.raytracing.math.Ray;
import by.slizh.raytracing.math.RayHit;
import by.slizh.raytracing.math.Vector3;
import by.slizh.raytracing.rendering.Camera;
import by.slizh.raytracing.rendering.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ViewPort extends JPanel {
    private JFrame frame;
    private Scene scene;
    private Robot robot;
    private float resolution = 0.4f;

    private boolean cameraCursorMoveFlag = false;

    private Vector3 lightPoint = new Vector3(1000f, 2000f, 1000f);

    public ViewPort(JFrame container) {
        setFocusable(true);

        cameraCursorMoveFlag = false;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        // Processing key presses
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Camera camera = scene.getCamera();
                float pitch = camera.getPitch();
                float yaw = camera.getYaw();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_D ->
                            camera.setPosition(camera.getPosition().add(new Vector3(0.05f, 0.0f, 0.0f).rotate(pitch, yaw)));
                    case KeyEvent.VK_A ->
                            camera.setPosition(camera.getPosition().add(new Vector3(-0.05f, 0.0f, 0.0f).rotate(pitch, yaw)));
                    case KeyEvent.VK_W -> {
                        Vector3 moveVector = new Vector3(0.0f, 0.0f, 0.05f).rotate(pitch, yaw);
                        moveVector.setY(0.0f);
                        camera.setPosition(camera.getPosition().add(moveVector));
                    }
                    case KeyEvent.VK_S ->{
                        Vector3 moveVector = new Vector3(0.0f, 0.0f, -0.05f).rotate(pitch, yaw);
                        moveVector.setY(0.0f);
                        camera.setPosition(camera.getPosition().add(moveVector));
                    }
                    case KeyEvent.VK_E -> camera.setPosition(camera.getPosition().add(new Vector3(0.0f, 0.2f, 0.0f)));
                    case KeyEvent.VK_Q -> camera.setPosition(camera.getPosition().add(new Vector3(0.0f, -0.2f, 0.0f)));
                }

                repaint();
            }
        });

        // Processing camera mouse movement
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (cameraCursorMoveFlag) {

                    // Coefficient to convert integer mouse movements to radians
                    final float TO_RADIANS_COEF = (float) Math.PI / 5000;

                    int centerX = frame.getX() + frame.getWidth() / 2;
                    int centerY = frame.getY() + frame.getHeight() / 2;

                    int mouseXOffset = e.getXOnScreen() - centerX;
                    int mouseYOffset = e.getYOnScreen() - centerY;

                    if (mouseXOffset != 0 || mouseYOffset != 0) {
                        //System.out.println(mouseXOffset);
                        //System.out.println(mouseYOffset);

                        Camera camera = scene.getCamera();
                        camera.setPitch(Math.min((float) Math.PI / 2,
                                Math.max((float) (-Math.PI / 2), camera.getPitch() + mouseYOffset * TO_RADIANS_COEF)));
                        camera.setYaw(camera.getYaw() + mouseXOffset * TO_RADIANS_COEF);
                        //camera.setPitch(Math.min(90, Math.max(-90, camera.getPitch() + mouseYOffset)));
                        //camera.setYaw(camera.getYaw() + mouseXOffset);
                        repaint();
                        // Move mouse to the center
                        robot.mouseMove(centerX, centerY);
                    }


                }
            }
        });

        // Enable / Disable camera mouse movement
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cameraCursorMoveFlag = !cameraCursorMoveFlag;
            }
        });

        frame = container;
        scene = new

                Scene();

//        scene.addSolid(new Sphere(new Vector3(-1, 0, 1), Color.RED, 0.4f));
//        scene.addSolid(new Sphere(new Vector3(0, 0, 2), Color.GREEN, 0.4f));
//        scene.addSolid(new Sphere(new Vector3(1, 0, 3), Color.BLUE, 0.4f));

        scene.addSolid(new

                Sphere(new Vector3(0, 0, 1), Color.RED, 0.4f));
        scene.addSolid(new

                Sphere(new Vector3(0, 0, 2), Color.GREEN, 0.4f));
        scene.addSolid(new

                Sphere(new Vector3(0, 0, 3), Color.BLUE, 0.4f));

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        int blockSize = (int) (1 / resolution);

        for (int x = 0; x < getWidth(); x += blockSize) {
            for (int y = 0; y < getHeight(); y += blockSize) {
                float u;
                float v;
                if (getWidth() > getHeight()) {
                    u = (float) (x - getWidth() / 2 + getHeight() / 2) / getHeight() * 2 - 1;
                    v = -((float) y / getHeight() * 2 - 1);
                } else {
                    u = (float) x / getWidth() * 2 - 1;
                    v = -((float) y - getHeight() / 2 + getWidth() / 2) / getWidth() * 2 - 1;
                }
                Vector3 eyePos = new Vector3(0, 0, (float) (-1 / Math.tan(Math.toRadians(scene.getCamera().getFieldOfVision() / 2))));
                Camera camera = scene.getCamera();
                Vector3 rayDir = new Vector3(u, v, 0).subtract(eyePos).rotate(camera.getPitch(), camera.getYaw());
                Ray ray = new Ray(eyePos.add(camera.getPosition()), rayDir);
                //System.out.println(ray);
                //Ray ray = new Ray(new Vector3(u, v, 0.0f), new Vector3(0.0f, 0.0f, 1.0f));
                RayHit rayHit = scene.calculateRayHit(ray);
                if (rayHit != null) {
                    //Vector3 lightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition())).rotate(-camera.getPitch(),-camera.getYaw());
                    Vector3 lightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
                    float cos = Vector3.dotProduct(rayHit.getNormal(), lightVector);
                    float coef = Math.max(cos, 0.0f);
                    Color color = new Color((int) (rayHit.getSolid().getColor().getRed() * coef),
                            (int) (rayHit.getSolid().getColor().getBlue() * coef),
                            (int) (rayHit.getSolid().getColor().getGreen() * coef));
                    g.setColor(color);
                    g.fillRect(x, y, blockSize, blockSize);
                }
//                for (Solid solid : scene.getSolids()) {
//                    Vector3 intersection = solid.calculateIntersection(ray);
//                    if (intersection != null) {
//                        Vector3 normal = solid.calculateNormal(ray);
//                        RayHit rayHit = new RayHit(intersection, ray, normal, solid);
//                        float cos = Vector3.dotProduct(rayHit.getNormal(), lightVector);
//                        float coef = Math.max(cos, 0.0f);
//                        Color color = new Color((int) (solid.getColor().getRed() * coef),
//                                (int) (solid.getColor().getBlue() * coef),
//                                (int) (solid.getColor().getGreen() * coef));
//                        g.setColor(color);
//                        g.fillRect(x, y, blockSize, blockSize);
//                    }
//                }
            }
        }
    }
}
