import rsa.RSAKeyGenerator
import rsa.alg.FastExponentiation
import java.math.BigInteger

object RSATool {
    @JvmStatic
    fun main(args: Array<String>) {
        print(FastExponentiation(BigInteger.valueOf(7), BigInteger.valueOf(13), BigInteger.valueOf(11)).result)
        RSAKeyGenerator().generateAndPersistKeyPair()
    }
}
