import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Scanner;
import java.util.Vector;

public class PointsArt {
    private double coordX;
    private double coordY;


    public double getCoordX() {
        return coordX;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public PointsArt(double x, double y) {
        this.coordX = x;
        this.coordY = y;

    }


    public static PointsArt[] coordToClassPoints(int[] coordinateX, int[] coordinateY) {
        PointsArt[] pointsCoord = new PointsArt[coordinateX.length];
        System.out.println("Длинна масива =" + pointsCoord.length);
        for (int i = 0; i < coordinateX.length; i++) {
            pointsCoord[i] = new PointsArt(coordinateX[i], coordinateY[i]);
        }
        return pointsCoord;
    }

    public static PointsArt[] coordToClassPoints(PointsArt[] coordinate, int delPoints) {
        int n = coordinate.length - 3 - delPoints;
        PointsArt[] pointsCoord = new PointsArt[(coordinate.length - delPoints - 3) * 2 + 1];
        System.out.println("длинна массива=  " + pointsCoord.length);
        int j = 0;
        for (int i = 0; i < pointsCoord.length; i++) {
            if (i % 2 == 0) {
                pointsCoord[i] = new PointsArt(coordinate[j].getCoordX(), coordinate[j].getCoordY());
                j++;
            } else {
                pointsCoord[i] = new PointsArt(((coordinate[j].coordX + coordinate[j - 1].coordX) / 2), ((coordinate[j].coordY + coordinate[j - 1].coordY) / 2));
            }
            System.out.println("INDEX  = " + i + "   COORD X =" + pointsCoord[i].getCoordX() + "    COORD Y = " + pointsCoord[i].getCoordY());

        }

        return pointsCoord;
    }

    public static PointsArt[] enterPoints() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите количество точек");
        int arrEnd = scanner.nextInt();
        PointsArt[] arrPoints = new PointsArt[arrEnd + 2];
        int x;
        int y;
        for (int i = 0; i < arrEnd; i++) {

            System.out.println("Введите Х точки " + i);
            x = scanner.nextInt();
            System.out.println("Введите  У точки " + i);
            y = scanner.nextInt();
            arrPoints[i] = new PointsArt(x, y);

        }
        arrPoints[arrEnd] = arrPoints[0];
        arrPoints[arrEnd + 1] = arrPoints[1];
        return arrPoints;
    }

    public static PointsArt[] startDelete(PointsArt[] masStar, int delPoints) {
        double segmentsSum0 = 0;
        double segmentsSum1 = 0;
        int startIndex = 0;
        for (int i = 0; i < masStar.length - 3 - delPoints; i++) {
            if (i % 2 == 0)
                segmentsSum0 += segmentLong(masStar, i);
            else
                segmentsSum1 += segmentLong(masStar, i);
        }
        if (segmentsSum0 > segmentsSum1)
            startIndex = 1;
        for (int i = 0; i < (masStar.length - delPoints) / 2; i++) {
            masStar[i] = masStar[startIndex];
            startIndex += 2;
            System.out.println("" + "- POINT  координ Х " + masStar[i].getCoordX() + "  координ У " + masStar[i].getCoordY());

        }
        return masStar;
    }

    static double segmentLong(PointsArt[] segmentsPoints, int i) {
        return (Math.sqrt((segmentsPoints[i].getCoordX() - segmentsPoints[i + 2].getCoordX()) * (segmentsPoints[i].getCoordX() - segmentsPoints[i + 2].getCoordX())
                + (segmentsPoints[i].getCoordY() - segmentsPoints[i + 2].getCoordY()) * (segmentsPoints[i].getCoordY() - segmentsPoints[i + 2].getCoordY())));
    }

    static double segmentLong(PointsArt a, PointsArt b) {
        return Math.sqrt(Math.pow((a.getCoordX() - b.getCoordX()), 2) + Math.pow((a.getCoordY() - b.getCoordY()), 2));
    }


    public static int doSwelling(VectorsArt[] arrOfVectors, PointsArt[] arrOfPoints, int delPoints) {

        for (int i = 0; i < arrOfVectors.length - 1 - delPoints; i++) {
            System.out.println(VectorsArt.vectorProdact(arrOfVectors, i) + " ++============ " + i);
            if (VectorsArt.vectorProdact(arrOfVectors, i) < 0) {
                arrOfPoints = delElem(arrOfPoints, i + 2, delPoints);

                arrOfVectors = VectorsArt.pointsToClassVectors(arrOfPoints, delPoints);

                System.out.println();
                delPoints++;
                i--;
            }
        }
        return delPoints;
    }

    public static int doSwelling(PointsArt[] arrOfPoints, VectorsArt[] arrOfVectors, int delPoints) {
        for (int i = 0; i < arrOfVectors.length - 1 - delPoints; i++) {
            System.out.println(VectorsArt.vectorProdact(arrOfVectors, i) + " ++============ " + i);
            if (VectorsArt.vectorProdact(arrOfVectors, i) > 0) {
                arrOfPoints = delElem(arrOfPoints, i + 2, delPoints);

                i--;
                arrOfVectors = VectorsArt.pointsToClassVectors(arrOfPoints, delPoints);
                System.out.println(VectorsArt.vectorProdact(arrOfVectors, i) + " ++============ " + i + "  ");
                delPoints++;

            }
        }
        return delPoints;
    }

    public static PointsArt[] delElem(PointsArt[] arrayPoints, int number, int delPoints) {
        if (number == arrayPoints.length - 2 - delPoints) {
            arrayPoints[arrayPoints.length - 2 - delPoints] = arrayPoints[1];
            number = 0;
        } else if (number == arrayPoints.length - 1 - delPoints) {
            number = 1;
            arrayPoints[arrayPoints.length - 1] = arrayPoints[2];
        }
        System.out.println(" X  =" + arrayPoints[number].getCoordX() + "Y =   " + arrayPoints[number].getCoordY());
        for (int i = number; i < arrayPoints.length - 1 - delPoints; i++)
            arrayPoints[i] = arrayPoints[i + 1];
        System.out.println(" - точка");
        return arrayPoints;
    }

    public static PointsArt[] deletePointsOnline(VectorsArt[] vectorsArt, PointsArt[] pointsArts) {
        for (int i = 0; i < vectorsArt.length - 1 - GeometryForArtillery.countDelPoints; i++) {
            if (VectorsArt.vectorProdact(vectorsArt, i) == 0) {
                System.out.println();
                pointsArts = PointsArt.delElem(pointsArts, i, GeometryForArtillery.countDelPoints);
                vectorsArt = VectorsArt.pointsToClassVectors(pointsArts, GeometryForArtillery.countDelPoints);
                i--;
                GeometryForArtillery.countDelPoints++;
            }
        }
        return pointsArts;
    }

    public static void show(LineArt[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.println("Index  " + i + "  Координата начальной точки Х =" + arr[i].getBegin().getCoordX() + " У=  " + arr[i].getBegin().getCoordY() +
                    "   segment long =  " + arr[i].getSegment());
        }
        System.out.println("");
    }
}