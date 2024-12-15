import examples.StdDraw;

public class BodyExtreme {

    public static double G = 6.67e-11;

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String color;
    public String imgFileName;

    private double redRed = 6.50001;
    private double redYellow = 0;
    private double redGreen = -200;
    private double redBlue = 310.5;

    private double yellowRed = 26;
    private double yellowYellow = -460;
    private double yellowGreen = 14;
    private double yellowBlue = 999;

    private double greenRed = 0;
    private double greenYellow = 0;
    private double greenGreen = 16.5;
    private double greenBlue = 0;

    private double blueRed = 1.5;
    private double blueYellow = 2.5;
    private double blueGreen = 15.6;
    private double blueBlue = -13.5;



    public BodyExtreme(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String color, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.color = color;
        this.imgFileName = "images/" + imgFileName;
    }

    public BodyExtreme(BodyExtreme b) {
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.color = b.color;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(BodyExtreme b) {
        if (this == b) {
            return Double.MAX_VALUE;
        }
        double distX = b.xxPos - this.xxPos;
        double distY = b.yyPos - this.yyPos;
        // Pythagoras
        return Math.sqrt((distX * distX) + (distY * distY));
    }

    public double calcForceExertedBy(BodyExtreme b) {
        double distance = this.calcDistance(b);
        if (distance == 0) {
            distance = 1E-7;
        }
        return (G * this.mass * b.mass) / (distance * distance);
    }

    public double calcForceExertedByX(BodyExtreme b) {
        return (this.calcForceExertedBy(b) * (b.xxPos - this.xxPos)) / this.calcDistance(b);
    }

    public double calcForceExertedByY(BodyExtreme b) {
        return (this.calcForceExertedBy(b) * (b.yyPos - this.yyPos)) / this.calcDistance(b);
    }

    public double calcNetForceExertedByX(BodyExtreme[] bodies) {
        double xTotal = 0.0;

        for (BodyExtreme b : bodies) {
            if (this != b) {
                // Calculates net force scaled by color interaction rule
                xTotal += this.calcForceExertedByX(b) * this.rule(b.color, this.color);
            }
        }
        return xTotal;
    }

    public double calcNetForceExertedByY(BodyExtreme[] bodies) {
        double yTotal = 0.0;
        for (BodyExtreme b : bodies) {
            if (this != b) {
                // Calculates net force scaled by color interaction rule
                yTotal += this.calcForceExertedByY(b) * this.rule(b.color, this.color);
            }
        }
        return yTotal;
    }

    public double rule(String c1, String c2) {

        if (c1.equals("red") && c2.equals("red")) { return redRed; }
        if (c1.equals("red") && c2.equals("yellow")) { return redYellow; }
        if (c1.equals("red") && c2.equals("green")) { return redGreen; }
        if (c1.equals("red") && c2.equals("blue")) { return redBlue; }

        if (c1.equals("yellow") && c2.equals("red")) { return yellowRed; }
        if (c1.equals("yellow") && c2.equals("yellow")) { return yellowYellow; }
        if (c1.equals("yellow") && c2.equals("blue")) { return yellowBlue; }
        if (c1.equals("yellow") && c2.equals("green")) { return yellowGreen; }

        if (c1.equals("green") && c2.equals("red")) { return greenRed; }
        if (c1.equals("green") && c2.equals("yellow")) { return greenYellow; }
        if (c1.equals("green") && c2.equals("green")) { return greenGreen; }
        if (c1.equals("green") && c2.equals("blue")) { return greenBlue; }

        if (c1.equals("blue") && c2.equals("red")) { return blueRed; }
        if (c1.equals("blue") && c2.equals("yellow")) { return blueYellow; }
        if (c1.equals("blue") && c2.equals("green")) { return blueGreen; }
        if (c1.equals("blue") && c2.equals("blue")) { return blueBlue; }

        return 0.0;
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
