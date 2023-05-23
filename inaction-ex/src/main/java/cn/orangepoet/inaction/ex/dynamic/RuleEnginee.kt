package cn.orangepoet.inaction.ex.dynamic

import groovy.lang.Binding
import groovy.lang.GroovyShell


val rule1 = "'hello, ' +  name +'!'"
val rule2 = "x + y >= z"
val rule3 = "x in y"
val rule4 = "(x == 1) && (y == 2)"

fun testRule(params: Map<String, Any>, script: String): Any? {
    val binding = Binding()
    params.forEach { binding.setProperty(it.key, it.value) }
    val returnValue = GroovyShell(binding).evaluate(script)
    println("testRule($params, $script) -> $returnValue")
    return returnValue
}

fun main() {
    testRule(mapOf("name" to "orange"), rule1)

    testRule(mapOf("x" to 1, "y" to 2, "z" to 3), rule2)
    testRule(mapOf("x" to 1, "y" to 1, "z" to 3), rule2)

    testRule(mapOf("x" to 1, "y" to listOf(1, 2, 3)), rule3)
    testRule(mapOf("x" to 4, "y" to listOf(1, 2, 3)), rule3)

    testRule(mapOf("x" to 1, "y" to 2), rule4)
    testRule(mapOf("x" to 1, "y" to 3), rule4)
    testRule(mapOf("x" to 2, "y" to 2), rule4)

}




