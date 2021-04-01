import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Solution {

    public static List<Integer> bestSolution;
    public static int currentGeneration = 1;
    public static List<List<Integer>> newGen = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {

        //Создаём поле 500х500, состоящее из чёрно-белых пикселей загружаемой картинки
        CreateMap defaultMap = new CreateMap();
        defaultMap.createBrightnessMap();

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        geneticAlgorithm.generateStartSolutions();

        //Задаём время, которое будет работать алгоритм
        System.out.println("Input time for program");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int time = Integer.parseInt(reader.readLine()) * 1000;
        System.out.println("Program has start working!\n");

        Date startTime = new Date();

        while (true) {
            int index = currentGeneration++;
            System.out.println();
            newGen = new ArrayList<>();

            //Распределяем нагрузку на потоки процессора
            List<Thread> listOfThreads = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                Thread thread = new Thread(new Threads());
                listOfThreads.add(thread);
                thread.start();
            }
            for (Thread thread : listOfThreads) {
                thread.join();
            }

            //До конца итерации цикла, заменяем наихудшее решение из нового поколения нилучшим решением из старого поколения
            List<List<Integer>> newGeneration = new ArrayList<>(newGen);

            double maxSummaInOldGeneration = geneticAlgorithm.findSummaByVector(GeneticAlgorithm.oldGeneration.get(0));
            double minSummaInNewGeneration = geneticAlgorithm.findSummaByVector(newGeneration.get(0));
            int maxIndexInOldGeneration = 0;
            int minIndexInNewGeneration = 0;

            for (int i = 1; i < GeneticAlgorithm.AMOUNT_OF_SOLUTIONS; i++) {
                if (geneticAlgorithm.findSummaByVector(GeneticAlgorithm.oldGeneration.get(i)) > maxSummaInOldGeneration) {
                    maxSummaInOldGeneration = geneticAlgorithm.findSummaByVector(GeneticAlgorithm.oldGeneration.get(i));
                    maxIndexInOldGeneration = i;
                }
                if (geneticAlgorithm.findSummaByVector(newGeneration.get(i)) < minSummaInNewGeneration) {
                    minSummaInNewGeneration = geneticAlgorithm.findSummaByVector(newGeneration.get(i));
                    minIndexInNewGeneration = i;
                }
            }

            newGeneration.set(minIndexInNewGeneration, GeneticAlgorithm.oldGeneration.get(maxIndexInOldGeneration));

            double maxSummaInNewGeneration = geneticAlgorithm.findSummaByVector(newGeneration.get(0));
            int maxIndexInNewGeneration = 0;
            for (int i = 0; i < GeneticAlgorithm.AMOUNT_OF_SOLUTIONS; i++) {
                if (geneticAlgorithm.findSummaByVector(newGeneration.get(i)) > maxSummaInNewGeneration) {
                    maxSummaInNewGeneration = geneticAlgorithm.findSummaByVector(newGeneration.get(i));
                    maxIndexInNewGeneration = i;
                }
            }

            bestSolution = newGeneration.get(maxIndexInNewGeneration);

            String prettyMoney = String.format("%.3f", geneticAlgorithm.findSummaByVector(bestSolution));
            System.out.println("Максимальная выгода в новом изменённом поколении: " + prettyMoney + " mln. Iteration = " + index);
            System.out.println();

            GeneticAlgorithm.oldGeneration = new ArrayList<>(newGeneration);

            Date currentTime = new Date();
            if (currentTime.getTime() > (startTime.getTime() + time)) {
                break;
            }
        }
        Draw mf = new Draw();
    }
}