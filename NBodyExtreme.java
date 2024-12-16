import examples.StdDraw;

import static java.lang.Math.pow;

public class NBodyExtreme {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        BodyExtreme[] bodies = readBodies(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(-100, -100, "images/starfield.jpg");
        for (BodyExtreme b : bodies) {
            b.draw();
        }

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
                boolean xCollision = false, yCollision = false;

                // Determine which wall was hit
                if (bodies[i].xxPos >= radius) {
                    bodies[i].xxPos = radius;  // Prevent sticking outside
                    xCollision = true;
                } else if (bodies[i].xxPos <= -radius) {
                    bodies[i].xxPos = -radius;  // Prevent sticking outside
                    xCollision = true;
                }

                if (bodies[i].yyPos >= radius) {
                    bodies[i].yyPos = radius;  // Prevent sticking outside
                    yCollision = true;
                } else if (bodies[i].yyPos <= -radius) {
                    bodies[i].yyPos = -radius;  // Prevent sticking outside
                    yCollision = true;
                }

                // Calculate reflection if a collision occurred
                if (xCollision || yCollision) {
                    // Current velocity vector
                    double vx = bodies[i].xxVel;
                    double vy = bodies[i].yyVel;

                    // Normal vector (depends on which wall was hit)
                    double nx = xCollision ? (bodies[i].xxPos > 0 ? 1 : -1) : 0;
                    double ny = yCollision ? (bodies[i].yyPos > 0 ? 1 : -1) : 0;

                    // Dot product
                    double dotProduct = vx * nx + vy * ny;

                    // Reflection vector calculation
                    double rx = vx - 2 * dotProduct * nx;
                    double ry = vy - 2 * dotProduct * ny;

                    // Reduce velocity by 75%
                    bodies[i].xxVel = rx * 0.75;
                    bodies[i].yyVel = ry * 0.75;
                }

                bodies[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(-100, -100, "images/starfield.jpg");

            for (BodyExtreme b : bodies) {
                b.draw();
            }

            StdDraw.show();

            StdDraw.pause(10);

        }

    }


    public static BodyExtreme[] readBodies(String path) {
        In in = new In(path);
        int nBodies = in.readInt();
        double radius = in.readDouble();
        BodyExtreme[] bodies = new BodyExtreme[nBodies];
        for (int nthBody = 0; nthBody < nBodies; nthBody++) {
            BodyExtreme b = new BodyExtreme(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString(), in.readString());
            bodies[nthBody] = b;
        }
        return bodies;
    }

    public static double readRadius(String path) {
        In in = new In(path);
        int nBodies = in.readInt();
        double radius = in.readDouble();
        return radius;
    }


}
