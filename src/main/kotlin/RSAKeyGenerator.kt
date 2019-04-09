import java.io.File
import java.math.BigInteger
import java.math.BigInteger.ONE

class RSAKeyGenerator {

    fun createAndPersistKeyPair(){
        val keyPair = generateKeyPair()
        persistKeyPair(keyPair)
    }

    private fun generateKeyPair(): RSAKeyPair {
        val q = BigInteger.probablePrime(1024, java.util.Random())
        val p = BigInteger.probablePrime(1024, java.util.Random())
        val n = q.multiply(p)
        val phiOfN = q.subtract(ONE).multiply(p.subtract(ONE))
        val e = ONE.modInverse(phiOfN)
        val d = findSuitableD(e, phiOfN)
        return RSAKeyPair(n, e, d)
    }

    private fun persistKeyPair(keyPair: RSAKeyPair) {
        persistKey("sk.txt", keyPair.n, keyPair.e)
        persistKey("pk.txt", keyPair.n, keyPair.d)
    }

    private fun findSuitableD(e: BigInteger, phiOfN: BigInteger): BigInteger {
        var d = ExtendenEuclideanAlgorithm(e, phiOfN).calculate()[3]
        while (d.signum() == -1) {
            d = d.add(phiOfN)
        }
        return d
    }

    private fun persistKey(keyFileName: String, n: BigInteger, ed: BigInteger) {
        val file = File(keyFileName)
        val keyString = generateKeyString(n, ed)
        file.createNewFile()
        file.writeText(keyString)
    }

    private fun generateKeyString(n: BigInteger, ed: BigInteger): String {
        return "(".plus(n).plus(",").plus(ed).plus(")")
    }
}
