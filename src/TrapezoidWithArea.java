public class TrapezoidWithArea extends PointsOfTrapezoid {

    private double area;

    TrapezoidWithArea(PointsArt p1, PointsArt p2, PointsArt p3, PointsArt p4, double area) {
        super(p1, p2, p3, p4);
        this.area = area;
    }


    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public static PointsOfTrapezoid maxArea(PointsOfTrapezoid trapezoid) {
        double leftSide = PointsArt.segmentLong(trapezoid.getP1(), trapezoid.getP2());
        double rightSide = PointsArt.segmentLong(trapezoid.getP3(), trapezoid.getP4());
        double top = PointsArt.segmentLong(trapezoid.getP1(), trapezoid.getP4());
        double bottom = PointsArt.segmentLong(trapezoid.getP2(), trapezoid.getP3());
        if (top > bottom) {
            if (leftSide > rightSide) {
                trapezoid.setP2(PointsOfTrapezoid.movedPoint(trapezoid.getP1(), trapezoid.getP4(), trapezoid.getP3(), trapezoid.getP2()));
            } else {
                trapezoid.setP3(PointsOfTrapezoid.movedPoint(trapezoid.getP1(), trapezoid.getP4(), trapezoid.getP2(), trapezoid.getP3()));
            }
        } else if (leftSide > rightSide) {
            trapezoid.setP1(PointsOfTrapezoid.movedPoint(trapezoid.getP2(), trapezoid.getP3(), trapezoid.getP4(), trapezoid.getP1()));
        } else
            trapezoid.setP4(PointsOfTrapezoid.movedPoint(trapezoid.getP2(), trapezoid.getP3(), trapezoid.getP1(), trapezoid.getP4()));


        return trapezoid;
    }

    public static double area(PointsOfTrapezoid trapezoid) {
        double a = PointsArt.segmentLong(trapezoid.getP1(), trapezoid.getP4());
        double b = PointsArt.segmentLong(trapezoid.getP2(), trapezoid.getP3());
        double c = PointsArt.segmentLong(trapezoid.getP1(), trapezoid.getP2());
        double d = PointsArt.segmentLong(trapezoid.getP3(), trapezoid.getP4());
        double area = (a + b) / 2 * Math.sqrt(c * c - Math.pow((Math.pow((b - a), 2) + c * c - d * d) / 2 * (b - a), 2));

        return area;
    }

    public static void show(PointsOfTrapezoid trapezoid) {
        System.out.println(" координаты очки 1  Х =  " + trapezoid.getP1().getCoordX() + "  Y=   " + trapezoid.getP1().getCoordY());
        System.out.println(" координаты очки 2  Х =  " + trapezoid.getP2().getCoordX() + "  Y=   " + trapezoid.getP2().getCoordY());
        System.out.println(" координаты очки 3  Х =  " + trapezoid.getP3().getCoordX() + "  Y=   " + trapezoid.getP3().getCoordY());
        System.out.println(" координаты очки 4   Х =  " + trapezoid.getP4().getCoordX() + "  Y=   " + trapezoid.getP4().getCoordY());
        System.out.println(" площадь трапеции =  " + area(trapezoid));
        System.out.println();

    }


}
