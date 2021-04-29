import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static boolean Combinations(Integer[][] basis, Integer[] candidate, int size) {
        for (int i = 0; i < size; i++) {
            Integer[] tmp = new Integer[basis[0].length];
            tmp = basis[i];
            if (Arrays.equals(tmp, candidate)) {
                return false;
            }
            for (int j = i + 1; j < size; j++) {

                tmp = XOR_Array(tmp, basis[j]);
                Integer[] tmp2 = new Integer[basis[0].length];
                tmp2 = basis[i];
                for (int q = j + 1; q < size; q++) {
                    tmp2 = XOR_Array(tmp2, basis[q]);
                    {
                        if (Arrays.equals(tmp, candidate)) {
                            return false;
                        }
                    }
                }
                if (Arrays.equals(tmp, candidate)) {
                    return false;
                }
            }

            tmp = basis[i];
            for (int j = i + 1; j < size; j++) {
                if (Arrays.equals(XOR_Array(tmp, basis[j]), candidate)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Integer[][] CreateBinaryLinearCode(Integer[][] basis, int k, int n) {
        Integer[][] truthTable = new Integer[(int) Math.pow(2, k)][k];
        Integer[][] result = new Integer[(int) Math.pow(2, k)][n];
        int rows = (int) Math.pow(2, k);
        for (int i = 0; i < rows; i++) {
            for (int j = k - 1; j >= 0; j--) {
                truthTable[i][j] = i / ((int) Math.pow(2, j)) % 2;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = k - 1; j >= 0; j--) {
                System.out.print(truthTable[i][j] + " ");
            }
            System.out.println("");
        }

        int count = 0;
        for (int i = 0; i < rows; i++) {
            Integer[][] tmp = new Integer[k][n];
            for (int j = 0; j < k; j++) {
                if (truthTable[i][j] == 1) {
                    tmp[j] = basis[j];

                } else {
                    for (int h = 0; h < n; h++) {
                        tmp[j][h] = 0;
                    }
                }
            }
            Integer[] intermediateResult = new Integer[n];
            intermediateResult = tmp[0];
            for (int s = 0; s < tmp.length - 1; s++) {
                intermediateResult = XOR_Array(intermediateResult, tmp[s + 1]);
            }
            result[count] = intermediateResult;
            count++;
        }

        for (int i = 0; i < rows; i++) {

            StringBuilder sb = new StringBuilder();
            StringBuilder sb1 = new StringBuilder();
            for (int r = k - 1; r >= 0; r--) {
                sb.append(truthTable[i][r]);
            }

            for (int r = 0; r < n; r++) {
                sb1.append(result[i][r]);
            }

            System.out.print("Message: " + sb.toString() + "; CodeWord: " + sb1.toString() + "\n");
        }

        return result;
    }

    public static int XOR(Integer[] first, Integer[] second) {
        int res = 0;
        for (int i = 0; i < first.length; i++) {

            if ((first[i] == 0 && second[i] == 1) || (first[i] == 1 && second[i] == 0)) {
                res++;
            }
        }
        return res;
    }

    public static Integer[] XOR_Array(Integer[] first, Integer[] second) {
        Integer[] result = new Integer[first.length];
        for (int i = 0; i < first.length; i++) {

            if ((first[i] == 0 && second[i] == 1) || (first[i] == 1 && second[i] == 0)) {
                result[i] = 1;
            } else {
                result[i] = 0;
            }
        }
        return result;
    }

    public static int comb(int n, int k) {
        if (k == 0)
            return 1;
        if (n == 0)
            return 0;
        return comb(n - 1, k - 1) + comb(n - 1, k);
    }

    public static boolean EqualsArray(Integer[][] basis, Integer[] result, int size) {
        int num = 0;
        for (int i = 0; i < size; i++) {
            num = 0;
            for (int j = 0; j < basis[0].length; j++) {
                if (basis[i][j] == result[j]) {
                    num++;
                }
            }
            if (num == basis[0].length) {
                return true;
            }
        }
        if (num < basis[0].length) {
            return false;
        }
        return true;
    }

    public static int weightArray(Integer[] array) {
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                size++;
            }
        }
        size++;
        return size;
    }

    public static Integer[][] createNewTableNew(Integer[][] basis, int k, int n, int d_min) {
        int count = 0;
        boolean flag = true;
        int number = 1;
        while (flag) {
            if (count == 5000) {
                flag = false;
                continue;

            }
            Integer[] result = new Integer[n - k];
            Random rand = new Random();
            for (int i = 0; i < n - k; i++) {
                result[i] = rand.nextInt(2) % 2;
            }
            if (weightArray(result) < d_min) {
                continue;
            }

            if (weightArray(result) > 0 && !(EqualsArray(basis, result, number))) {
                basis[number] = result;
                number++;
                count = 0;
                if (number == k) {
                    flag = false;
                    continue;
                }
            }
            count++;
        }
        return basis;
    }

    public static Integer[][] generateMtrx(int k, int n, Integer[][] basis, Integer[][] fullMatrix) {

        int place = 0;
        while (place != k) {
            fullMatrix[place][place] = 1;
            place++;
        }

        for (int i = 0; i < k; i++) {
            int colm = 0;
            for (int j = k; j < n; j++) {
                fullMatrix[i][j] = basis[i][colm];
                colm++;
            }
        }
        return fullMatrix;
    }

    public static Integer[] inputNKD(int n, int k, int d) {
        System.out.println("Input 'n', 'k', 'd': ");
        Scanner scanner1 = new Scanner(System.in);
        n = scanner1.nextInt(); // столбцы
        k = scanner1.nextInt(); // строки
        d = scanner1.nextInt();
        Integer[] tmp = new Integer[3];
        tmp[0] = n;
        tmp[1] = k;
        tmp[2] = d;
        return tmp;
    }

    public static void correctParametr(int n, int k, int d, Integer[][] basis) {
        boolean flag = true;
        Random rand = new Random();
        Integer[] nkd = new Integer[3];
        int rows = n - k;
        while (flag) {
            if (n - k < d) {
                System.out.println("n-k < d");
                nkd = inputNKD(n, k, d);
                n = nkd[0];
                k = nkd[1];
                d = nkd[2];
                continue;
            }
            for (int i = 0; i < rows; i++) {
                basis[0][i] = rand.nextInt(2) % 2;
            }
            if (weightArray(basis[0]) < d) {
                if (d == k) {
                    System.out.println("Please,write another parametr. d == k !");
                    nkd = inputNKD(n, k, d);
                    n = nkd[0];
                    k = nkd[1];
                    d = nkd[2];
                    basis = new Integer[k][n - k];
                }
                if (n - k - 1 == d) {
                    System.out.println("Please,write another parametr. d == n-k-1 || n-k !");
                    nkd = inputNKD(n, k, d);
                    n = nkd[0];
                    k = nkd[1];
                    d = nkd[2];
                    basis = new Integer[k][n - k];
                }
                continue;
            }
            if (weightArray(basis[0]) == 0) {
                flag = true;
                for (int q = 0; q < basis[0].length; q++) {
                    basis[0][q] = 0;
                }
            } else {
                flag = false;
            }
        }
        int d_min = d;
        createNewTableNew(basis, k, n, d_min);

        Integer[][] fullMatrix = new Integer[k][n];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                fullMatrix[i][j] = 0;
            }
        }

        generateMtrx(k, n, basis, fullMatrix);

        System.out.println("_____");
        System.out.println("Generating matrix:");
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(fullMatrix[i][j] + " ");
            }
            System.out.println(" ");
        }
        System.out.println("_____");

        CreateBinaryLinearCode(fullMatrix, k, n);
    }

    public static boolean Borders(int n, int k, int d) {
        int t = (d - 1) / 2;
        System.out.println("Corrective ability = " + t);
        System.out.println("Number of words = " + Math.pow(2, k));

        int l;
        l = n; // в числитель, но стоит снизу
        int sum = 0;
        for (int i = 0; i <= t; i++) {
            sum += comb(l, i);

        }

        boolean right = true;
        System.out.println("| Hamming border: " + (Math.pow(2, n) / (Math.pow(2, k))) + " >= " + sum + " |");
        if ((Math.pow(2, n) / (Math.pow(2, k))) < sum) {
            right = false;
            System.out.println("! The Hamming boundary does not pass !");
        }

        sum = 0;
        for (int i = 0; i <= d - 1; i++) {
            sum += comb(l, i);
        }

        System.out
                .println("| Warshamov — Gilbert border: " + (Math.pow(2, n) / (Math.pow(2, k))) + " <= " + sum + " |");

        if ((Math.pow(2, n) / (Math.pow(2, k))) >= sum) {
            right = false;
            System.out.println("! The Warshamov — Gilbert boundary does not pass !");
        }

        System.out.println("| Singleton border: " + n + " >= " + k + " + " + d + " - " + 1 + " |");

        if (n < k + d - 1) {
            right = false;
            System.out.println("! The Singleton boundary does not pass !");
        }
        return right;
    }

    public static boolean LI(Integer[][] basis, Integer[] candidat, int btm) {
        for (int i = 0; i < btm; i++) {
            if (i + 1 < btm && weightArray(basis[i + 1]) != 0) {
                int bottom = i + 1;
                Integer[] tmp = new Integer[basis[i].length];
                while (bottom != basis.length) {
                    tmp = basis[i];
                    tmp = XOR_Array(tmp, basis[bottom]);
                    if (tmp.equals(candidat)) {
                        return false;
                    }
                    bottom++;
                }
            }
            return true;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {

        Integer[] tmp = new Integer[3];
        int n = 0;
        int k = 0;
        int d = 0;
        tmp = inputNKD(n, k, d);
        n = tmp[0];
        k = tmp[1];
        d = tmp[2];

        if (k <= d) {
            System.out.println("k <= d");
            return;
        }
        if (n <= d) {
            System.out.println("n <= d");
        }

        Integer[][] basis = new Integer[k][n - k];

        if (Borders(n, k, d)) {
            correctParametr(n, k, d, basis);
        }
    }
}