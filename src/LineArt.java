import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

public class LineArt {


    private static boolean crossExist;

    private PointsArt begin;
    private PointsArt end;
    private double segment;


    public PointsArt getEnd() {
        return end;
    }

    public void setEnd(PointsArt end) {
        this.end = end;
    }

    public double getSegment() {
        return segment;
    }

    public void setSegment(double segment) {
        this.segment = segment;
    }

    public PointsArt getBegin() {
        return begin;
    }

    public void setBegin(PointsArt begin) {
        this.begin = begin;
    }


    public LineArt(PointsArt begin, PointsArt end, PointsArt gun) {

        this.begin = begin;
        this.end = end;
        this.segment = Math.sqrt(Math.pow((begin.getCoordX() - gun.getCoordX()), 2) + Math.pow((begin.getCoordY() - gun.getCoordY()), 2));
    }

    public static LineArt[] sortSHake(LineArt[] lines) {
        LineArt buff;
        int left = 0;
        int right = lines.length - 2;
        do {
            for (int i = left; i < right; i++) {
                if (lines[i].getSegment() > lines[i + 1].getSegment()) {
                    buff = lines[i];
                    lines[i] = lines[i + 1];
                    lines[i + 1] = buff;
                }
            }
            right--;
            for (int i = right; i > left; i--) {
                if (lines[i].getSegment() < lines[i - 1].getSegment()) {
                    buff = lines[i];
                    lines[i] = lines[i - 1];
                    lines[i - 1] = buff;
                }
            }
            left++;
        } while (left < right);

        return lines;
    }

    public static LineArt[] closeArr(LineArt[] arrFull) {
        LineArt[] buff = new LineArt[arrFull.length / 3 + 2];
        for (int i = 0; i < buff.length - 1; i++)
            buff[i] = arrFull[i];
        return buff;
    }

    public static LineArt[] farArr(LineArt[] arrFull) {
        LineArt[] buff = new LineArt[arrFull.length / 3 + 2];
        for (int i = 0; i < buff.length - 1; i++)
            buff[i] = arrFull[arrFull.length - 2 - i];
        return buff;

    }

    public static PointsArt crossPoint(LineArt[] cLines, LineArt[] fLines, PointsArt gun, int i) {

        PointsArt cross = new PointsArt(0, 0);
        for (int j = 0; j < cLines.length - 1; j++) {                   // ONe line with array on bord
            double x11 = fLines[i].begin.getCoordX();
            double y11 = fLines[i].begin.getCoordY();
            double x12 = gun.getCoordX();
            double y12 = gun.getCoordY();
            double x21 = cLines[j].begin.getCoordX();
            double y21 = cLines[j].begin.getCoordY();
            double x22 = cLines[j].end.getCoordX();
            double y22 = cLines[j].end.getCoordY();

            double a1 = y11 - y12;
            double a2 = y21 - y22;
            double b1 = x12 - x11;
            double b2 = x22 - x21;
            double c1 = y12 * x11 - x12 * y11;
            double c2 = y22 * x21 - x22 * y21;
            double d = a1 * b2 - a2 * b1;
            double xCross = (b1 * c2 - b2 * c1) / d;
            double yCross = (a2 * c1 - a1 * c2) / d;

            if (xCross >= Math.min(x21, x22) && xCross <= Math.max(x21, x22) && Math.min(y21, y22) <= yCross && yCross <= Math.max(y21, y22)) {

                cross.setCoordX(xCross);
                cross.setCoordY(yCross);
                crossExist = true;
                return cross;
            }
        }
        return cross;
    }

    public static PointsOfTrapezoid maxFarPoints(LineArt[] farPoints, LineArt[] closePoints, PointsArt gun) {
        PointsOfTrapezoid trapezoid = new PointsOfTrapezoid(gun, gun, gun, gun);
        double areaMax = 0;
        double areMaxNew;
        PointsOfTrapezoid maxTrapezoid = new PointsOfTrapezoid(gun, gun, gun, gun);
        for (int i = 0; i < farPoints.length - 2; i++) {
            PointsArt cross1 = LineArt.crossPoint(closePoints, farPoints, gun, i);
            trapezoid.setP1(farPoints[i].getBegin());
            trapezoid.setP2(cross1);
            if (crossExist == true) {
                for (int j = i + 1; j < farPoints.length - 1; j++) {
                    crossExist = false;
                    PointsArt cross2 = LineArt.crossPoint(closePoints, farPoints, gun, j);
                    trapezoid.setP3(cross2);
                    trapezoid.setP4(farPoints[j].getBegin());
                    if (crossExist == true) {
                        PointsOfTrapezoid trapezoidMax = new PointsOfTrapezoid(farPoints[i].getBegin(), cross1, cross2, farPoints[j].getBegin());
                        trapezoidMax = TrapezoidWithArea.maxArea(trapezoidMax);
                        areMaxNew = TrapezoidWithArea.area(trapezoidMax);
                        if (areaMax < areMaxNew) {
                            maxTrapezoid = trapezoidMax;
                            areaMax = areMaxNew;
                        }
                    }
                }
            }
        }
        return maxTrapezoid;
    }

    public static PointsOfTrapezoid maxFarPoints(PointsArt gun, LineArt[] closePoints, LineArt[] farPoints) {
        double areaMax = 0;
        double areMaxNew;
        PointsOfTrapezoid maxTrapezoid = new PointsOfTrapezoid(gun, gun, gun, gun);
        PointsOfTrapezoid trapezoid = new PointsOfTrapezoid(gun, gun, gun, gun);
        for (int i = 0; i < farPoints.length - 2; i++) {
            PointsArt cross1 = LineArt.crossPoint(closePoints, farPoints, gun, i);
            trapezoid.setP1(farPoints[i].getBegin());
            trapezoid.setP2(cross1);
            if (crossExist == true) {
                for (int j = i; j < closePoints.length - 1; j++) {
                    crossExist = false;
                    PointsArt cross2 = LineArt.crossPoint(farPoints, closePoints, gun, j);
                    trapezoid.setP4(cross2);
                    trapezoid.setP3(closePoints[j].getBegin());
                    if (crossExist == true) {
                        PointsOfTrapezoid trapezoidMax = new PointsOfTrapezoid(farPoints[i].getBegin(), cross1, closePoints[j].getBegin(), cross2);
                        trapezoidMax = TrapezoidWithArea.maxArea(trapezoidMax);
                        areMaxNew = TrapezoidWithArea.area(trapezoidMax);
                        if (areaMax < areMaxNew) {
                            maxTrapezoid = trapezoidMax;
                            areaMax = areMaxNew;

                        }
                    }
                }
            }
        }
        return maxTrapezoid;
    }


}









