/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
public class RSA
{
    private BigInteger P;
    private BigInteger Q;
    private BigInteger N;
    private BigInteger PHI;
    private BigInteger e;
    private BigInteger d;
    private int maxLength = 1024;
    private Random R;
    public RSA()
    {
        R = new Random();
        //Use "probablePrime" to generate large primes//
        P = BigInteger.probablePrime(maxLength, R);
        Q = BigInteger.probablePrime(maxLength, R);
        //Compute N by multiplying P and Q
        N = P.multiply(Q);
        //Compute PHI by multiplying (p-1) and (q-1) together
        PHI = P.subtract(BigInteger.ONE).multiply(  Q.subtract(BigInteger.ONE));
        //Compute e by finding a prime half the size of P and Q
        e = BigInteger.probablePrime(maxLength / 2, R);
        while (PHI.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(PHI) < 0)
        {
            e.add(BigInteger.ONE);
        }
        //Compute d as the modular inverse of e and PHI
        d = e.modInverse(PHI);
    }
    public RSA(BigInteger e, BigInteger d, BigInteger N)
    {
        this.e = e;
        this.d = d;
        this.N = N;
    }
    public static void main (String [] arguments) throws IOException
    {
        long firstTime = System.nanoTime();
        RSA rsa = new RSA();
        long endRSACreation = System.nanoTime();
        DataInputStream input = new DataInputStream(System.in);
        String inputString;
        System.out.println("Enter message you wish to send.");
        inputString = input.readLine();
        System.out.println("Encrypting the message: " + inputString);
        System.out.println("The message in bytes is:: "
                + bToS(inputString.getBytes()));
        // encryption
        long beforeCipher = System.nanoTime();
        byte[] cipher = rsa.encryptMessage(inputString.getBytes());
        long afterCipher = System.nanoTime();
        // decryption
        long beforeDecrypt = System.nanoTime();
        byte[] plain = rsa.decryptMessage(cipher);
        long afterDecrypt = System.nanoTime();
        System.out.println("Decrypting Bytes: " + bToS(plain));
        System.out.println("Plain message is: " + new String(plain));
        System.out.println("Time to create RSA object: " +
                (endRSACreation - firstTime)/1000 + " nanoseconds");
        System.out.println("Time to encrypt: " +
                (afterCipher - beforeCipher) + " nanoseconds");
        System.out.println("Time to decrypt: " +
                (afterDecrypt-beforeDecrypt) + " nanoseconds");
    }
    private static String bToS(byte[] cipher)
    {
        String temp = "";
        for (byte b : cipher)
        {
            temp += Byte.toString(b);
        }
        return temp;
    }
    // Encrypting the message
    public byte[] encryptMessage(byte[] message)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }
    // Decrypting the message
    public byte[] decryptMessage(byte[] message)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }
} 
