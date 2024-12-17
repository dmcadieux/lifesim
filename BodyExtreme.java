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

    private double redRed = 0.17;
    private double redYellow = -66666;
    private double redGreen = -66666;
    private double redBlue = -66666;

    private double yellowRed = -66666;
    private double yellowYellow = 0.15;
    private double yellowGreen = -0.15;
    private double yellowBlue = 0;

    private double greenRed = -66666;
    private double greenYellow = 0.34;
    private double greenGreen = 80;
    private double greenBlue = 0;

    private double blueRed = -66666;
    private double blueYellow = -232;
    private double blueGreen = -500;
    private double blueBlue = 0;

    private double maxRedRed;
    private double maxRedGreen;
    private double maxRedYellow;
    private double maxRedBlue;

    private double maxYellowRed;
    private double maxYellowYellow;
    private double maxYellowGreen;
    private double maxYellowBlue;

    private double maxGreenRed;
    private double maxGreenYellow;
    private double maxGreenGreen;
    private double maxGreenBlue;

    private double maxBlueRed;
    private double maxBlueYellow;
    private double maxBlueGreen;
    private double maxBlueBlue;


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

        // Corrects for 0 distance
        double distance = Math.max(this.calcDistance(b), 1E-7);

                 /* if (this.calcDistance(b) > this.distanceRule(b.color, this.color)) {
            return 0;
        } */

        // Force calc
        double value = ((G * this.mass * b.mass) / (distance * distance)) * this.rule(b.color, this.color);

        // Fixes broken values
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return 0;
        }

        return value;
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
                xTotal += this.calcForceExertedByX(b);
            }
        }
        return xTotal;
    }

    public double calcNetForceExertedByY(BodyExtreme[] bodies) {
        double yTotal = 0.0;
        for (BodyExtreme b : bodies) {
            if (this != b) {
                // Calculates net force scaled by color interaction rule
                yTotal += this.calcForceExertedByY(b);
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

    public double distanceRule(String d1, String d2) {
        if (d1.equals("red") && d2.equals("red")) { return maxRedRed; }
        else if(d1.equals("red") && d2.equals("yellow")) { return maxRedYellow; }
        else if(d1.equals("red") && d2.equals("green")) { return maxRedGreen; }
        else if(d1.equals("red") && d2.equals("blue")) { return maxRedBlue; }

        else if(d1.equals("green") && d2.equals("red")) { return maxGreenRed; }
        else if(d1.equals("green") && d2.equals("red")) { return maxGreenRed; }

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
