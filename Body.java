import examples.StdDraw;

public class Body {

    public static double G = 6.67e-11;

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;


    public Body(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = "images/" + imgFileName;
    }

    public Body(Body b) {
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double distX = b.xxPos - this.xxPos;
        double distY = b.yyPos - this.yyPos;
        // sqrt of dx^2 + dx^y
        return Math.sqrt((distX * distX) + (distY * distY));
    }

    public double calcForceExertedBy(Body b) {
        return (G * this.mass * b.mass) / Math.pow(this.calcDistance(b), 2);
    }

    public double calcForceExertedByX(Body b) {
        return (this.calcForceExertedBy(b) * (b.xxPos - this.xxPos)) / this.calcDistance(b);
    }

    public double calcForceExertedByY(Body b) {
        return (this.calcForceExertedBy(b) * (b.yyPos - this.yyPos)) / this.calcDistance(b);
    }

    public double calcNetForceExertedByX(Body[] bodies) {
        double xTotal = 0.0;
        for (Body b : bodies) {
            if (!this.equals(b)) {
                xTotal = xTotal + this.calcForceExertedByX(b);
            }
        }
        return xTotal;
    }

    public double calcNetForceExertedByY(Body[] bodies) {
        double yTotal = 0.0;
        for (Body b : bodies) {
            if (!this.equals(b)) {
                yTotal = yTotal + this.calcForceExertedByY(b);
            }
        }
        return yTotal;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        this.xxVel = this.xxVel + (dt * aX);
        this.yyVel = this.yyVel + (dt * aY);
        this.xxPos = this.xxPos + (dt * xxVel);
        this.yyPos = this.yyPos + (dt * yyVel);
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, this.imgFileName);
    }



}
