public class VectorsArt {
    private double xCoord;
    private double yCoord;

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    VectorsArt(double x, double y) {
        xCoord = x;
        yCoord = y;
    }

    public static VectorsArt[] pointsToClassVectors(PointsArt[] arrPoints, int delPoints) {                                         // переводим точки в вектора
        VectorsArt[] vectorsCoord = new VectorsArt[arrPoints.length - 1 - delPoints];
        for (int i = 0; i < arrPoints.length - 1 - delPoints; i++) {
            vectorsCoord[i] = new VectorsArt((arrPoints[i].getCoordX() - arrPoints[i + 1].getCoordX()), (arrPoints[i].getCoordY() - arrPoints[i + 1].getCoordY()));
        }
        return vectorsCoord;
    }

    public static int vievSign(VectorsArt[] masVect) {                               // проверка на впуклость + подсчет знаков векторных произведений для определения направления впуклости
        int countSignPlus = 0;                          //schetchik znakov
        int countSignMinus = 0;
        double sign;
        for (int i = 0; i < masVect.length - 1; i++) {
            sign = vectorProdact(masVect, i);         //proverkq  vsey figuri na znak kotoriy budet cv itoge
            if (sign > 0)
                countSignPlus++;
            else if (sign < 0)
                countSignMinus--;
            System.out.println(" индекс векторного произведения  " + i + " ===" + sign);
        }
        System.out.println("");
        return countSignMinus == 0 || countSignPlus == 0 ? 0 : Math.abs(countSignMinus) == Math.abs(countSignPlus) ? 1 : Math.abs(countSignMinus) > Math.abs(countSignPlus) ? 2 : 3;
    }

    public static double vectorProdact(VectorsArt[] massiveVectors, int i) {
        return (massiveVectors[i].getxCoord() * massiveVectors[i + 1].getyCoord() - massiveVectors[i + 1].getxCoord() * massiveVectors[i].getyCoord());
    }


}