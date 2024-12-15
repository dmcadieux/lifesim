import examples.StdDraw;

import java.util.*;
import java.util.function.Predicate;

public class NBodyExtreme {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Map<String, List<BodyExtreme>> bodiesByColor = readBodies(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");



        StdDraw.show();


        double xForces[];
        double yForces[];

        int size = bodies.length;
        for (double time = 0; time <= T; time = time + dt) {
            xForces = new double[size];
            yForces = new double[size];
            for (int i = 0; i < size; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            for (int i = 0; i < size; i++) {
                // To not fly off the screen
                if (bodies[i].xxPos >= radius || bodies[i].xxPos <= -radius || bodies[i].yyPos >= radius || bodies[i].yyPos <= -radius) {
                    bodies[i].xxVel = -bodies[i].xxVel;
                    bodies[i].yyVel = -bodies[i].yyVel;
                }
                bodies[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");

            for (Map.Entry<String, List<BodyExtreme>> entry : bodiesByColor.entrySet()) {
                String color = entry.getKey();
                List<BodyExtreme> bodies = entry.getValue();

                for (BodyExtreme body : bodies) {
                    body.draw();
                }
            }

            StdDraw.show();

            StdDraw.pause(10);

        }

    }
    public void rule(Map<String, List<BodyExtreme>> bodiesByColor) {

    }

    public static Map<String, List<BodyExtreme>> readBodies(String path) {
        In in = new In(path);
        int nBodies = in.readInt();
        double radius = in.readDouble();

        Map<String, List<BodyExtreme>> bodiesByColor = new HashMap<>();

        for (int nthBody = 0; nthBody < nBodies; nthBody++) {
            BodyExtreme b = new BodyExtreme(
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readString(),
                    in.readString()
                );

            bodiesByColor.computeIfAbsent(b.color, k -> new ArrayList<>()).add(b);
        }
        return bodiesByColor;

    }

    public static double readRadius(String path) {
        In in = new In(path);
        int nBodies = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static int bodyNum(String path) {
        In in = new In(path);
        int nBodies = in.readInt();
        return nBodies;
    }


}
