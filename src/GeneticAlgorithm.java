import java.io.IOException;
import java.util.*;

public class GeneticAlgorithm {

    static List<List<Integer>> oldGeneration;
    final static int AMOUNT_OF_SOLUTIONS = 100;

    public void generateStartSolutions() {

        List<List<Integer>> vec = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_SOLUTIONS; i++) {

            List<Integer> vector = new ArrayList<>();
            Random random = new Random();
            int amountOf = random.nextInt(17) + 5;
            vector.add(amountOf);
            for (int j = 0; j < amountOf; j++) {
                while (true) {
                    Tower tower = new Tower();
                    int oX = random.nextInt(501);
                    int oY = random.nextInt(501);
                    if (tower.efficiency(oX,oY) > 0) {
                        vector.add(oX);
                        vector.add(oY);
                        break;
                    }
                }
            }
            vec.add(vector);
        }
        oldGeneration = vec;
    }

    //Выбираются два наилучших решения из текущего поколения
    public List<Integer> orangeQudrats() throws IOException {
        Random random = new Random();
        int a, b ,c ,d;
        do {
            a = random.nextInt(oldGeneration.size());
            b = random.nextInt(oldGeneration.size());
            c = random.nextInt(oldGeneration.size());
            d = random.nextInt(oldGeneration.size());
        } while (a == b || a == c || a == d || b == c || b == d || c == d);

        double aVectorSumma = findSummaByIndex(a);
        double bVectorSumma = findSummaByIndex(b);
        double cVectorSumma = findSummaByIndex(c);
        double dVectorSumma = findSummaByIndex(d);

        int firstParent, secondParent;
        if (aVectorSumma > bVectorSumma) {
            firstParent = a;
        } else {
            firstParent = b;
        }

        if (cVectorSumma > dVectorSumma) {
            secondParent = c;
        } else {
            secondParent = d;
        }

        List<Integer> parents = new ArrayList<>();
        parents.add(firstParent);
        parents.add(secondParent);
        return parents;
    }

    //Соединяем два вектора в один
    public List<Integer> mergeVectors(List<Integer> parents) {

        Random random = new Random();
        int newN = random.nextInt(2);

        List<Integer> mergedVector = new ArrayList<>();
        mergedVector.add(oldGeneration.get(parents.get(newN)).get(0));

        List<List<Integer>> setList = new ArrayList<>();

        for (int i = 1; i < (oldGeneration.get(parents.get(0)).get(0) * 2); i+=2) {
            List<Integer> tmpList = new ArrayList<>();
            tmpList.add(oldGeneration.get(parents.get(0)).get(i));
            tmpList.add(oldGeneration.get(parents.get(0)).get(i + 1));
            setList.add(tmpList);
        }

        for (int i = 1; i < (oldGeneration.get(parents.get(1)).get(0) * 2); i+=2) {
            List<Integer> tmpList = new ArrayList<>();
            tmpList.add(oldGeneration.get(parents.get(1)).get(i));
            tmpList.add(oldGeneration.get(parents.get(1)).get(i + 1));
            setList.add(tmpList);
        }

        Set<List<Integer>> setOfDots = new HashSet<>(setList);

        for (List<Integer> dot : setOfDots) {
            mergedVector.add(dot.get(0));
            mergedVector.add(dot.get(1));
        }

        int size = mergedVector.size();
        for (int i = mergedVector.get(0) * 2; i < size - 1; i++) {
            mergedVector.remove(mergedVector.get(0) * 2 + 1);
        }

        mergedVector = deleteBadTowers(mergedVector);

        //Подвергаем получившийся вектор одной случайной мутации
        try {
            switch (random.nextInt(3)) {
                case (0):
                    mergedVector = mutationAdd(mergedVector);
                    break;

                case (1):
                    mergedVector = mutationRemove(mergedVector);
                    break;

                case (2):
                    mergedVector = mutationChange(mergedVector);
                    break;
            }
        } catch (Exception ignored) {}

        return mergedVector;
    }

    //Удаляем из поколения вышки, чья прибыль меньше необходимого минимума
    public List<Integer> deleteBadTowers(List<Integer> oldVector) {
        Tower tower = new Tower();
        List<Integer> newVector = new ArrayList<>();
        newVector.add(0);
        for (int i = 1; i < oldVector.size(); i+=2) {
            if (tower.efficiency(oldVector.get(i), oldVector.get(i + 1)) > 3_000_000) {
                newVector.set(0, newVector.get(0) + 1);
                newVector.add(oldVector.get(i));
                newVector.add(oldVector.get(i + 1));
            }
        }
        return newVector;
    }

    public double findSummaByIndex(int index) {

        List<Integer> solution = oldGeneration.get(index);
        Tower tower = new Tower();
        return tower.checkEffsForSolution(solution);
    }

    public double findSummaByVector(List<Integer> solution) {

        Tower tower = new Tower();
        return tower.checkEffsForSolution(solution);
    }

    //Мутация - добавляется случайная вышка
    public List<Integer> mutationAdd(List<Integer> vector) {
        Random random  = new Random();
        vector.set(0, vector.get(0) + 1);
        int newX = random.nextInt(501);
        int newY = random.nextInt(501);
        vector.add(newX);
        vector.add(newY);

        return vector;
    }

    //Муттация - удаляется случайная вышка
    public List<Integer> mutationRemove(List<Integer> vector) {
        Random random = new Random();
        int index = random.nextInt(vector.get(0)) + 1;

        vector.remove(2 * index);
        vector.remove(2 * index - 1);
        vector.set(0, vector.get(0) - 1);

        return vector;
    }

    //Мутация - изменяется положение случайной вышки
    public List<Integer> mutationChange(List<Integer> vector) {
        Random random = new Random();
        int index = random.nextInt(vector.get(0)) + 1;
        int newX = random.nextInt(501);
        int newY = random.nextInt(501);

        vector.set(2 * index - 1, newX);
        vector.set(2 * index, newY);

        return vector;
    }
}