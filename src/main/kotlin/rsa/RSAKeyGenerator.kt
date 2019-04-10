package rsa

import rsa.dto.RSAKey
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.util.*

class RSAKeyGenerator(val fileService: RSAKeyFileService = RSAKeyFileService()) {

    fun generateAndPersistKeyPair(){
        val keyPair = generateKeyPair()
        fileService.persistKeyPair(keyPair)
    }

    private fun generateKeyPair(): Pair<RSAKey, RSAKey> {
        val q = BigInteger.probablePrime(1024, Random())
        val p = BigInteger.probablePrime(1024, Random())
        val n = q.multiply(p)
        val phiOfN = q.subtract(ONE).multiply(p.subtract(ONE))
        val e = BigInteger(1024, Random())
        val d = findSuitableD(e, phiOfN)

        val privateKey = RSAKey(n, e)
        val publicKey = RSAKey(n, d)
        return Pair(privateKey, publicKey)
    }

    private fun findSuitableD(e: BigInteger, phiOfN: BigInteger): BigInteger {
        var d = ExtendenEuclideanAlgorithm(e, phiOfN).y0
        while (d.signum() == -1) {
            d = d.add(phiOfN)
        }
        return d
    }
}