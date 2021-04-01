import java.io.IOException;
import java.util.List;

public class Threads implements Runnable{
    final Object sync = new Object();

    @Override
    public void run() {
        while (Solution.newGen.size() < GeneticAlgorithm.AMOUNT_OF_SOLUTIONS){
            synchronized (sync) {
                GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
                List<Integer> gen  = null;
                //Генерируем поколение
                try {
                    gen = geneticAlgorithm.mergeVectors(geneticAlgorithm.orangeQudrats());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Solution.newGen.add(gen);
            }
        }
    }
}
