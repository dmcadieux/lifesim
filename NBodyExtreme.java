import examples.StdDraw;

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
                // To not fly off the screen
                if (bodies[i].xxPos >= radius || bodies[i].xxPos <= -radius || bodies[i].yyPos >= radius || bodies[i].yyPos <= -radius) {
                    bodies[i].xxVel = (-bodies[i].xxVel);
                    bodies[i].yyVel = (-bodies[i].yyVel);
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
