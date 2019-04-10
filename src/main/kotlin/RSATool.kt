import rsa.RSAKeyGenerator

object RSATool {
    @JvmStatic
    fun main(args: Array<String>) {
        RSAKeyGenerator().generateAndPersistKeyPair()
    }
}
