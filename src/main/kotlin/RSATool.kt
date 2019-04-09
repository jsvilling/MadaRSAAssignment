object RSATool {
    @JvmStatic
    fun main(args: Array<String>) {
        RSAKeyGenerator().createAndPersistKeyPair()
    }
}
