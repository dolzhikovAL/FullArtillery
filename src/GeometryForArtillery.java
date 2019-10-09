public class GeometryForArtillery {
    static int countDelPoints = 0;

    public static void main(String[] args) {

        //счетчик удаленных точек


        int[] masX = new int[12], masY = new int[12];
       //   masX = new int[]{1, 1, 2, 4, 6, 5, 8, 8, 6, 2,1,1};            // массив точек 1 ne впуклая
      //    masY = new int[]{1, 3, 5, 6, 5, 3, 1, -3, -4, -2,1,3};
        masX = new int[]{0, 2, 3, 5, 3, 4, 2, 0, -1, -3, 0, 2};            // массив точек звезда 2 последние дублируются
        masY = new int[]{2, 5, 2, 3, 1, -2, -1, -3, -1, -1, 2, 5};
        System.out.println(masX.length);
        //    PointsArt[] pointsArt=new PointsArt[masX.length];
        //   VectorsArt[] vectorsArts = new VectorsArt[masX.length-1];
        PointsArt[] pointsArts = coordToClassPoints(masX, masY);         // создаем массив собственного класа
        VectorsArt[] vectorsArt = pointsToClassVectors(pointsArts);
        int sign = vievSign(vectorsArt);
        if (sign == 1) {
            System.out.println("ваша фигура типа звезда");
            for (int i = 0; i < vectorsArt.length - 1 - countDelPoints; i++) {              //убираем точки которые принадлежат  отрезку между соседними точеками
                if (vectorProdact(vectorsArt, i) == 0) {
                    pointsArts = delElem(pointsArts, i);
                    vectorsArt = pointsToClassVectors(pointsArts);
                    i--;
                    countDelPoints++;
                }
            }
            int pointStep = startDelete(pointsArts);                     //переписіваем массивс вершинани с меньшим периметром
            for (int i = 0; i < (pointsArts.length - countDelPoints) / 2; i++) {
                pointsArts[i] = pointsArts[pointStep];
                pointStep += 2;
                System.out.println("" + "- POINT  координ Х " +  pointsArts[i].coordX+ "  координ У " + pointsArts[i].coordY );

            }
            countDelPoints += (masX.length / 2 - 1);               // добавление дублирующей второй точки и массив векторов
            pointsArts[masX.length / 2 + 1] = pointsArts[1];
            vectorsArt = pointsToClassVectors(pointsArts);
            System.out.println("");
            sign = vievSign(vectorsArt);                          // повторная проверка на впуклость
        }

        switch (sign) {
            case 1:
                System.out.println("фигурa впуклая ");
                break;

            case 2:
                pointsArts = doSwelling(pointsArts, vectorsArt);
                break;
            case 3:
                pointsArts = doSwelling(vectorsArt, pointsArts);
        }
    }

    public static PointsArt[] coordToClassPoints(int[] coordinateX, int[] coordinateY) {                                         // переводим точки в класс точки
        PointsArt[] pointsCoord = new PointsArt[coordinateX.length - countDelPoints];
        System.out.println(pointsCoord.length);
        for (int i = 0; i < coordinateX.length - countDelPoints; i++) {
            pointsCoord[i] = new PointsArt(coordinateX[i], coordinateY[i]);
        }
        return pointsCoord;
    }

    public static VectorsArt[] pointsToClassVectors(PointsArt[] arrPoints) {                                         // переводим точки в вектора
        VectorsArt[] vectorsCoord = new VectorsArt[arrPoints.length - 1 - countDelPoints];
        for (int i = 0; i < arrPoints.length - 1 - countDelPoints; i++) {
            vectorsCoord[i] = new VectorsArt((arrPoints[i].coordX - arrPoints[i + 1].coordX), (arrPoints[i].coordY - arrPoints[i + 1].coordY));

        }
        return vectorsCoord;
    }


    public static int vievSign(VectorsArt[] masVect) {                               // проверка на впуклость + подсчет знаков векторных произведений для определения направления впуклости
        int countSignPlus = 0;                          //schetchik znakov
        int countSignMinus = 0;
        int sign;
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

    public static PointsArt[] delElem(PointsArt[] arrayPoints, int number) {                   //УДАЛЕНИЕ ТОЧКИ ИЗ МАССИВА точек СО СДВИГОМ
        if (number == arrayPoints.length - 2 - countDelPoints) {
            arrayPoints[arrayPoints.length - 2 - countDelPoints] = arrayPoints[1];
            number = 0;
        } else if (number == arrayPoints.length - 1 - countDelPoints) {
            number = 1;
            arrayPoints[arrayPoints.length - 1] = arrayPoints[2];                                  //замена дублируеміх точек если они попали под замену
        }
        for (int i = number; i < arrayPoints.length - 1 - countDelPoints; i++)
            arrayPoints[i] = arrayPoints[i + 1];
        System.out.println(" - точка");
        countDelPoints++;
        return arrayPoints;
    }

    public static int vectorProdact(VectorsArt[] massiveVectors, int i) {
        return (massiveVectors[i].xLong * massiveVectors[i + 1].yLong - massiveVectors[i + 1].xLong * massiveVectors[i].yLong);
    }

    public static int startDelete(PointsArt[] masStar) {
        double segmentsSum0 = 0;
        double segmentsSum1 = 0;
        int startIndex = 0;                                                             //начальный элемент массива вершин звезды
        for (int i = 0; i < masStar.length - 3 - countDelPoints; i++) {        // - потому что 2 последних  элемента повторяютсяф
            if (i % 2 == 0)
                segmentsSum0 += segmentLong(masStar, i);             // подсчет периметров между точек через одну
            else
                segmentsSum1 += segmentLong(masStar, i);
        }
        if (segmentsSum0 > segmentsSum1)
            startIndex = 1;
        return startIndex;
    }

    static double segmentLong(PointsArt[] segmentsPoints, int i) {                                                                                           //подсчет длин отрезков
        return (Math.sqrt((segmentsPoints[i].coordX - segmentsPoints[i + 2].coordX) * (segmentsPoints[i].coordX - segmentsPoints[i + 2].coordX)
                + (segmentsPoints[i].coordY - segmentsPoints[i + 2].coordY) * (segmentsPoints[i].coordY - segmentsPoints[i + 2].coordY)));
    }

    public static PointsArt[] doSwelling(PointsArt[] arrOfPoints, VectorsArt[] arrOfVectors) {
                            for (int i = 0; i < arrOfVectors.length  - countDelPoints; i++) {
                System.out.println(vectorProdact(arrOfVectors, i) + " ++============ " + i);
                if (vectorProdact(arrOfVectors, i) > 0) {
                    arrOfPoints = delElem(arrOfPoints, i);
                    arrOfVectors = pointsToClassVectors(arrOfPoints);
                    System.out.println(vectorProdact(arrOfVectors,i) + " ++============ " + i + "  " );
                }
            }
        return arrOfPoints;
    }

    public static PointsArt[] doSwelling(VectorsArt[] arrOfVectors, PointsArt[] arrOfPoints) {

            for (int i = 0; i < arrOfVectors.length  - countDelPoints; i++) {
                System.out.println(vectorProdact(arrOfVectors, i) + " ++============ " + i);
                if (vectorProdact(arrOfVectors, i) < 0) {
                    arrOfPoints = delElem(arrOfPoints, i);
                    arrOfVectors = pointsToClassVectors(arrOfPoints);
                    countDelPoints++;
                    System.out.println();


                }
            }
        return arrOfPoints;
    }

}

