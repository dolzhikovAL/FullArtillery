import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.omg.PortableServer.POA;
import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;

public class GeometryForArtillery {
    static int countDelPoints = 0;

    public static void main(String[] args) {
        // PointsArt[] inPoints =PointsArt.enterPoints();
        //  Scanner scanner = new Scanner(System.in);
        // System.out.println("введите координаті точки орудия");
        // PointsArt gun = new PointsArt(scanner.nextInt(),scanner.nextInt());
        PointsArt gun = new PointsArt(-3, 15);
        int[] masX = new int[]{1, 1, 2, 4, 6, 9, 8, 8, 6, 2, 1, 1};
        int[] masY = new int[]{1, 3, 5, 6, 5, 7, 1, -3, -4, -2, 1, 3};
        //    int[] masX = new int[]{0, 2, 3, 5, 3, 4, 2, 0, -1, -3, 0, 2};
        //  int[] masY = new int[]{2, 5, 2, 3, 1, -2, -1, -3, -1, -1, 2, 5};
        System.out.println(masX.length);
        PointsArt[] pointsArts = PointsArt.coordToClassPoints(masX, masY);
        VectorsArt[] vectorsArt = VectorsArt.pointsToClassVectors(pointsArts, countDelPoints);
        int sign = VectorsArt.vievSign(vectorsArt);
        if (sign == 1) {
            System.out.println("ваша фигура типа звезда");
            pointsArts = PointsArt.deletePointsOnline(vectorsArt, pointsArts);
            pointsArts = PointsArt.startDelete(pointsArts, countDelPoints);
            countDelPoints += (pointsArts.length / 2 - 1);
            pointsArts[masX.length / 2 + 1] = pointsArts[1];
            vectorsArt = VectorsArt.pointsToClassVectors(pointsArts, countDelPoints);
            sign = VectorsArt.vievSign(vectorsArt);
        }
        switch (sign) {
            case 0:
                System.out.println("фигурa впуклая ");
                break;

            case 2:
                countDelPoints = PointsArt.doSwelling(pointsArts, vectorsArt, countDelPoints);
                break;
            case 3:
                countDelPoints = PointsArt.doSwelling(vectorsArt, pointsArts, countDelPoints);
        }
        PointsArt[] cleanArr = PointsArt.coordToClassPoints(pointsArts, countDelPoints);
        LineArt[] linesArr = new LineArt[cleanArr.length - 1];
        for (int i = 0; i < cleanArr.length - 1; i++) {
            linesArr[i] = new LineArt(cleanArr[i], cleanArr[i + 1], gun);
            System.out.println(i + "     координаты конечной фигуры  Х =" + cleanArr[i].getCoordX() + " У=  " + cleanArr[i].getCoordY());
        }
        linesArr = LineArt.sortSHake(linesArr);
        System.out.println("");
        System.out.println("Отсортированные точки");
        PointsArt.show(linesArr);
        LineArt[] closePoints = LineArt.closeArr(linesArr);
        System.out.println("ближние точки");
        PointsArt.show(closePoints);
        LineArt[] farPoints = LineArt.farArr(linesArr);
        System.out.println("Результат по 2 дальним точкам ");
        PointsArt.show(farPoints);
        PointsOfTrapezoid trapezoidMaxFarPoints = LineArt.maxFarPoints(farPoints, closePoints, gun);
        TrapezoidWithArea.show(trapezoidMaxFarPoints);
        System.out.println("результат по 2 ближним ");
        PointsOfTrapezoid trapezoidMaxclosePoints = LineArt.maxFarPoints(closePoints, farPoints, gun);
        TrapezoidWithArea.show(trapezoidMaxclosePoints);
        System.out.println("Результат по 1 дальней и 1ой бляжней точке");
        PointsOfTrapezoid trapezoidOneCloseOneFar = LineArt.maxFarPoints(gun, closePoints, farPoints);
        TrapezoidWithArea.show(trapezoidOneCloseOneFar);
    }
}









































































