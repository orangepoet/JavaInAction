package cn.orangepoet.inaction.dns

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.atomic.AtomicInteger


class DnsServer(config: Map<String, List<String>>) {

    private val roundRobin = mutableMapOf<String, AtomicInteger>()

    var dnsConfig = config

    fun start() {
        val receiveData = ByteArray(1024)
        DatagramSocket(53).use { serverSocket ->
            while (true) {
                val receivePacket = DatagramPacket(receiveData, receiveData.size)
                serverSocket.receive(receivePacket)

                val domainName = String(receivePacket.data, 0, receivePacket.length - 1)
                println("domainName: $domainName")
                val ip = resolve(domainName)

                val sendData = ip.toByteArray()

                val address = receivePacket.address
                val port = receivePacket.port

                val sendPacket = DatagramPacket(sendData, sendData.size, address, port)
                serverSocket.send(sendPacket)
            }
        }
    }

    private fun resolve(domain: String): String {
        if (domain in dnsConfig.keys) {
            val ipList = dnsConfig[domain]!!
            val next = roundRobin.getOrPut(domain) { AtomicInteger(-1) }.incrementAndGet() % ipList.size
            return ipList[next]
        }
        return "dns domain not found"
    }
}