import java.util.List;

public class Tower {

    public double efficiency(int centerByX, int centerByY) {

        int[][] tmpMap = new int[500][500];
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 500; j++) {
                tmpMap[i][j] = CreateMap.brightnessMap[i][j];
            }
        }

        int currentSumma = 0;
        for (int i = centerByX - 50; i < centerByX + 50; i++) {
            for (int j = centerByY - 50; j < centerByY + 50; j++) {
                if (Math.sqrt(Math.pow(centerByX - i, 2) + Math.pow(centerByY - j, 2)) <= 50 ) {
                    if (i >= 500 || j >= 500 || i < 0 || j < 0) {
                        currentSumma += 0;
                    } else {
                        currentSumma += tmpMap[i][j];
                        tmpMap[i][j] = 0;
                    }
                }
            }
        }
        return currentSumma * 10 - 5_000_000;
    }
    
     public double checkEffsForSolution(List<Integer> vector) {

         int[][] tmpMap = new int[500][500];
         for (int i = 0; i < 500; i++) {
             for (int j = 0; j < 500; j++) {
                 tmpMap[i][j] = CreateMap.brightnessMap[i][j];
             }
         }
        double value = 0;

        for (int k = 1; k < vector.size() - 1; k+=2) {
            for (int i = vector.get(k) - 50; i < vector.get(k) + 50; i++) {
                for (int j = vector.get(k + 1) - 50; j < vector.get(k + 1) + 50; j++) {
                    if (Math.sqrt(Math.pow(vector.get(k) - i, 2) + Math.pow(vector.get(k + 1) - j, 2)) <= 50 ) {
                        if (i < 500 && j < 500 && i >= 0 && j >= 0) {
                            value += tmpMap[i][j];
                            tmpMap[i][j] = 0;
                        }
                    }
                }
            }
        }
        return (value * 10 - (5_000_000 * vector.get(0))) / 1_000_000;
    }
}