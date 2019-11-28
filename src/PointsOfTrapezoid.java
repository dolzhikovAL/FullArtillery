public class PointsOfTrapezoid {
    private PointsArt p1;
    private PointsArt p2;
    private PointsArt p3;
    private PointsArt p4;

    public PointsArt getP1() {
        return p1;
    }

    public void setP1(PointsArt p1) {
        this.p1 = p1;
    }

    public PointsArt getP2() {
        return p2;
    }

    public void setP2(PointsArt p2) {
        this.p2 = p2;
    }

    public PointsArt getP3() {
        return p3;
    }

    public void setP3(PointsArt p3) {
        this.p3 = p3;
    }

    public PointsArt getP4() {
        return p4;
    }

    public void setP4(PointsArt p4) {
        this.p4 = p4;
    }

    PointsOfTrapezoid(PointsArt x1, PointsArt x2, PointsArt x3, PointsArt x4) {
        this.p1 = x1;
        this.p2 = x2;
        this.p3 = x3;
        this.p4 = x4;
    }


    public static PointsArt movedPoint(PointsArt z1, PointsArt z4, PointsArt out, PointsArt changePoint) {
        double a1 = z1.getCoordY() - z4.getCoordY();
        double b1 = z4.getCoordX() - z1.getCoordX();
        double c1 = a1 * (-out.getCoordX()) + b1 * (-out.getCoordY());
        double a2 = changePoint.getCoordY() - z1.getCoordY();
        double b2 = z1.getCoordX() - changePoint.getCoordX();
        double c2 = z1.getCoordY() * changePoint.getCoordX() - z1.getCoordX() * changePoint.getCoordY();
        double d = a1 * b2 - a2 * b1;

        double x =((b1 * c2 - b2 * c1) / d);
        double y =((a2 * c1 - a1 * c2) / d);
        PointsArt newPoint= new PointsArt(x,y);
        return newPoint;





    }

}