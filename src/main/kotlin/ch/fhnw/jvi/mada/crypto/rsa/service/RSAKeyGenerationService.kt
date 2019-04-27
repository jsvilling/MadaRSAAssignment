package ch.fhnw.jvi.mada.crypto.rsa.service

import ch.fhnw.jvi.mada.crypto.rsa.alg.ExtendenEuclideanAlgorithm
import ch.fhnw.jvi.mada.crypto.rsa.dto.RSAKey
import ch.fhnw.jvi.mada.crypto.shared.KeyGenerationService
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.util.*

/**
 * Service to generate a valid RSA Keypair. The service randomly generates a matching private and public key and
 * persists them to text files. The service uses the following files in the working directory:
 *
 * <ul>
 *  <li>sk.txt - Textfile containing the private / secret key information</li>
 *  <li>pk.txt - Texfile containing the public key information</li>
 * </ul>
 *
 * If the files are not present they will be created
 */
class RSAKeyGenerationService(
    private val fileService: RSAKeyFileService = RSAKeyFileService(),
    private val random: Random = Random(),
    private val primeBitLength: Int = 2048
) : KeyGenerationService {

    /**
     * Generates and persists a valid RSAKey pair. The keys are persisted to the following files in the working
     * directory:
     *
     * <ul>
     *  <li>sk.txt - Textfile containing the private / secret key information</li>
     *  <li>pk.txt - Texfile containing the public key information</li>
     * </ul>
     */
    override fun generateAndPersistKeyPair() {
        val keyPair = generateKeyPair()
        fileService.persistKeyPair(keyPair)
    }

    /**
     * Helper method doing the actual key generation.
     *
     * @return A Pair consisting of the two RSAKeys. The first key is the private key. The second key is the public key.
     */
    private fun generateKeyPair(): Pair<RSAKey, RSAKey> {
        val q = BigInteger.probablePrime(primeBitLength, random)            // Choose random prime q
        val p = findDistinctPrime(q)                                        // Choose second, distinct random prime q
        val n = q.multiply(p)                                               // Calculate n = p * q
        val phiOfN = q.subtract(ONE).multiply(p.subtract(ONE))              // Calculate phiOfN = (p-1)*(q-1)
        val e = findCoprime(phiOfN)                                         // Coose random e coprime to phiOfN
        val d = findSuitableD(e, phiOfN)                                    // Calculate matching d

        val privateKey = RSAKey(n, e)                                       // Create private key with n and e
        val publicKey = RSAKey(n, d)                                        // Create public key with n and d
        return Pair(privateKey, publicKey)
    }

    /**
     * Creates a random 1024 bit prime using BigInteger. If the created prime is equal to the passed numer
     * @param firstPrime another prime is generated. This is repeated until a prime not equal to the firstPrime has
     * been created.
     */
    private fun findDistinctPrime(firstPrime: BigInteger): BigInteger {
        var newPrime = BigInteger.probablePrime(primeBitLength, random)
        while (newPrime.equals(firstPrime)) {
            newPrime = BigInteger.probablePrime(primeBitLength, random)
        }
        return newPrime
    }

    /**
     * Creates a random 1024 bit prime using BigInteger. If the created prime is not coprime to the passed number
     * @param phiOfN another prime is generated. This is repeated until a prime that is coprime to the passed phiOfN
     * is found.
     */
    private fun findCoprime(phiOfN: BigInteger): BigInteger {
        var e = BigInteger.probablePrime(primeBitLength, random)
        while (!phiOfN.gcd(e).equals(BigInteger.ONE)) {
            e = BigInteger.probablePrime(primeBitLength, random)
        }
        return e
    }

    /**
     * Calculates a number d such that:
     *
     * <ul>
     *     <li>d element Z*phiOfN</li>
     *     <li>e * d congruent 1 (mod piOfN)</li>
     * </ul>
     *
     * The calculation is done using ExtendedEuclideanAlgorithm
     */
    private fun findSuitableD(e: BigInteger, phiOfN: BigInteger): BigInteger {
        // Calculate d such that e * d congruent 1 (mod piOfN)
        var d = ExtendenEuclideanAlgorithm(e, phiOfN).x0
        // If the calculated d is negative add phiOfN untill the number is positive.
        while (d.signum() == -1) {
            d = d.add(phiOfN)
        }
        return d
    }
}