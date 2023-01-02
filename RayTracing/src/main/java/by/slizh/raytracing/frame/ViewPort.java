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

    //private Vector3 lightPoint = new Vector3(1000f, 2000f, 1000f);
    private Vector3 lightPoint = new Vector3(0, 0, -10);

    private float kA = 0.4f; // коэффициент фонового освещения
    private Color colorA = new Color(255, 229, 0); // цвет фонового света
    private float kS = 1.0f; // коэффициент зеркального освещения
    private float a = 10; // коэффициент блеска поверхности
    private Color colorS = new Color(255, 255, 255); // цвет зеркального света

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
                    case KeyEvent.VK_S -> {
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

//        scene.addSolid(new Sphere(new Vector3(-1, 0, 1), Color.RED, 0.4f,0.6f));
//        scene.addSolid(new Sphere(new Vector3(0, 0, 1), Color.GREEN, 0.4f,0.6f));
//        scene.addSolid(new Sphere(new Vector3(1, 0, 1), Color.BLUE, 0.4f,0.6f));

        scene.addSolid(new Sphere(new Vector3(-2, 0, 1), Color.RED, 0.4f,0.6f));
        scene.addSolid(new Sphere(new Vector3(0, 0, 1), Color.GREEN, 0.8f,0.6f));
        scene.addSolid(new Sphere(new Vector3(2, 0, 1), Color.BLUE, 0.4f,0.6f));

//        scene.addSolid(new Sphere(new Vector3(0, 0, 1), Color.RED, 0.4f, 0.5f));
//        scene.addSolid(new Sphere(new Vector3(0, 0, 5), Color.BLUE, 2f, 0.5f));
//        scene.addSolid(new Sphere(new Vector3(-1.3f, 0, 1), Color.GREEN, 0.1f, 0.5f));



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
                RayHit rayHit = scene.calculateRayHit(ray);

                // Если луч нашёл какой-либо объект
                if (rayHit != null) {
                    Color pixelColor = calculatePixelColor(rayHit, 3);
                    g.setColor(pixelColor);
                    g.fillRect(x, y, blockSize, blockSize);
//                    Vector3 toLightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
//                    Ray toLightRay = new Ray(rayHit.getHitPosition(), toLightVector);
//
//                    // Считаем фоновое освещение
//                    Color color;
//                    Color iA = new Color((int) (kA * colorA.getRed()), (int) (kA * colorA.getBlue()), (int) (kA * colorA.getGreen()));
//                    Color iD;
//                    Color iS;
//
//                    // Если луч направленный к свету врезался в препядствие
//                    if (scene.calculateRayHit(toLightRay) != null) {
//                        // Оставляем только фоновое освещение
//                        color = iA;
//                    } else {
//                        Vector3 lightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
//                        float cos = Vector3.dotProduct(rayHit.getNormal(), lightVector);
//                        float coef = Math.max(cos, 0.0f);
//                        iD = new Color((int) (rayHit.getSolid().getColor().getRed() * coef),
//                                (int) (rayHit.getSolid().getColor().getGreen() * coef),
//                                (int) (rayHit.getSolid().getColor().getBlue() * coef));
//                        Vector3 viewVector = rayHit.getRay().getDirection();
//                        //// TODO: 30.12.2022 зачем-то инвершу, наверное функция отражения через жопу написана
//                        Vector3 reflectLightVector = Vector3.inverse(Vector3.normalize(Vector3.reflect(lightVector, rayHit.getNormal())));
//                        cos = Vector3.dotProduct(reflectLightVector, viewVector);
//                        coef = Math.max(cos, 0.0f);
//                        iS = new Color((int) (colorS.getRed() * kS * Math.pow(coef, a)),
//                                (int) (colorS.getGreen() * kS * Math.pow(coef, a)),
//                                (int) (colorS.getBlue() * kS * Math.pow(coef, a)));
//                        color = new Color((int) ((iA.getRed() + iD.getRed() + iS.getRed()) / 3.0f),
//                                (int) ((iA.getGreen() + iD.getGreen() + iS.getGreen()) / 3.0f),
//                                (int) ((iA.getBlue() + iD.getBlue() + iS.getBlue()) / 3.0f));
//                    }
//                    g.setColor(color);
//                    g.fillRect(x, y, blockSize, blockSize);
                }
            }
        }
    }

    public Color calculatePixelColor(RayHit rayHit, int recursionNumber) {
        Vector3 hitPos = rayHit.getHitPosition();
        Vector3 rayDir = rayHit.getRay().getDirection();
        Solid hitSolid = rayHit.getSolid();
        Color hitColor = hitSolid.getColor();
        float brightness = getDiffuseBrightness(rayHit);
        float reflectivity = hitSolid.getReflectivity();

        Color reflection;
        Vector3 reflectionVector = Vector3.inverse(Vector3.normalize(Vector3.reflect(rayDir, rayHit.getNormal())));
        Vector3 reflectionRayOrigin = hitPos.add(reflectionVector.multiply(0.001f)); // Чтобы в тот же солид не попасть
        Ray reflectionRay = new Ray(reflectionRayOrigin, reflectionVector);

        RayHit reflectionHit = null;

        if (recursionNumber > 0) {
            reflectionHit = scene.calculateRayHit(reflectionRay);
        }

        // Рекурсия !!!
        if (reflectionHit != null) {
            reflection = calculatePixelColor(reflectionHit, recursionNumber - 1);
        } else {
            reflection = Color.WHITE;
        }

        Color lerpColor = lerp(hitColor, reflection, reflectivity);
        return multiplyColor(lerpColor, brightness);
    }

    private Color multiplyColor(Color color, float scalar) {
        scalar = Math.min(1, scalar);
        return new Color((int)(color.getRed() * scalar), (int)(color.getGreen() * scalar), (int)(color.getBlue() * scalar));
    }

    private float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    public Color lerp(Color a, Color b, float t) {
        return new Color((int) lerp(a.getRed(), b.getRed(), t), (int) lerp(a.getGreen(), b.getGreen(), t), (int) lerp(a.getBlue(), b.getBlue(), t));
    }

    private float getDiffuseBrightness(RayHit rayHit) {
        Vector3 toLightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
        Ray toLightRay = new Ray(rayHit.getHitPosition(), toLightVector);
        RayHit lightBlockerHit = scene.calculateRayHit(toLightRay);

        if (lightBlockerHit != null && lightBlockerHit.getSolid() != rayHit.getSolid()) {
            return kA;
        } else {
            Vector3 lightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
            float cos = Vector3.dotProduct(rayHit.getNormal(), lightVector);
            float coef = Math.max(cos, 0.0f);
            return Math.max(coef, kA);
        }
    }

}
