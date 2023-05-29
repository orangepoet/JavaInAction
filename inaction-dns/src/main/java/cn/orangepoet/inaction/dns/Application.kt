package cn.orangepoet.inaction.dns


fun main() {
    DnsServer(mapOf("orange.home" to listOf("192.168.1.102", "192.168.1.103", "192.168.1.104"))).start()
}