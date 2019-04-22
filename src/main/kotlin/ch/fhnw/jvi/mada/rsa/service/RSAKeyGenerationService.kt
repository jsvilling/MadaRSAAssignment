package ch.fhnw.jvi.mada.rsa.service

import ch.fhnw.jvi.mada.rsa.alg.ExtendenEuclideanAlgorithm
import ch.fhnw.jvi.mada.rsa.dto.RSAKey
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.util.*

class RSAKeyGenerationService(val fileService: RSAKeyFileService = RSAKeyFileService(), val random: Random = Random()) {

    fun generateAndPersistKeyPair(){
        val keyPair = generateKeyPair()
        fileService.persistKeyPair(keyPair)
    }

    private fun generateKeyPair(): Pair<RSAKey, RSAKey> {
        val q = BigInteger.probablePrime(1024, random)
        val p = BigInteger.probablePrime(1024, random)
        val n = q.multiply(p)
        val phiOfN = q.subtract(ONE).multiply(p.subtract(ONE))
        val e = findCoprime(phiOfN)
        val d = findSuitableD(e, phiOfN)

        val privateKey = RSAKey(n, e)
        val publicKey = RSAKey(n, d)
        return Pair(privateKey, publicKey)
    }

    private fun findCoprime(phiOfN: BigInteger): BigInteger {
        var e = BigInteger.probablePrime(1023, random)
        while (!phiOfN.gcd(e).equals(BigInteger.ONE)) {
            e = BigInteger.probablePrime(1023, random)
        }
        return e
    }

    private fun findSuitableD(e: BigInteger, phiOfN: BigInteger): BigInteger {
        val alg = ExtendenEuclideanAlgorithm(e, phiOfN)
        var d = alg.x0
        while (d.signum() == -1) {
            d = d.add(phiOfN)
        }
        return d
    }
}