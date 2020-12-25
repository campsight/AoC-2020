import java.io.IOException;
import java.math.BigInteger;

public class Day25_2020 {
    public static void main(String[] args) throws IOException {
        //BigInteger key = BigInteger.valueOf(5764801);
        //BigInteger door = BigInteger.valueOf(17807724);
        BigInteger key = BigInteger.valueOf(1717001);
        BigInteger door = BigInteger.valueOf(523731);

        BigInteger subjectNumber = BigInteger.valueOf(7);

        int keyLoopSize = runLoop(subjectNumber, subjectNumber, key);
        System.out.println("Key loop size: " + keyLoopSize);
        int doorLoopSize = runLoop(subjectNumber, subjectNumber, door);
        System.out.println("Door loop size: " + doorLoopSize);
        BigInteger encryptionKey = runLoop(keyLoopSize, door, door);
        System.out.println("Private key key: " + encryptionKey);
        BigInteger encryptionKey2 = runLoop(doorLoopSize, key, key);
        System.out.println("Private key door: " + encryptionKey2);

    }

    private static int runLoop(BigInteger value, BigInteger subjectNumber, BigInteger publicKey) {
        boolean found = false;
        int loopSize = 1;
        while (!found) {
            value = value.multiply(subjectNumber).remainder(BigInteger.valueOf(20201227));
            if (value.equals(publicKey)) { found = true; }
            loopSize++;
        }
        return loopSize;
    }

    private static BigInteger runLoop(int nbTimes, BigInteger value, BigInteger subjectNumber) {
        for (int i = 1; i < nbTimes; i++){
            value = value.multiply(subjectNumber).remainder(BigInteger.valueOf(20201227));
        }
        return value;
    }
}
