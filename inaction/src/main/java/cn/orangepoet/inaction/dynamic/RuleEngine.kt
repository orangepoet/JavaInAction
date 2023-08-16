package cn.orangepoet.inaction.dynamic

import groovy.lang.Binding
import groovy.lang.GroovyShell
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.SimpleBindings
import kotlin.system.measureTimeMillis


// 这些规则可以配置化, 比如apollo推送来支持更新
val rule1 = "'hello, ' +  name +'!'"
val rule2 = "x + y >= z"
val rule3 = "x in y"
val rule4 = "(x == 1) && (y == 2)"
val rule5 = """
        if (context.size(x) == 'big') {
            job.run()
        } else {
            println("none run")
        }
    """.trimIndent()

val calculatorScript = """
    class Cal {
        def add(int x,int y) {
            x+y
        }
    }
    new Cal()
""".trimIndent()

typealias testRuleFunc = (params: Map<String, Any>, script: String) -> Any?

/**
 * 基于GroovyShell实现的脚本代码
 */
fun shellExec(params: Map<String, Any>, script: String): Any? {
    val binding = Binding()
    params.forEach { binding.setVariable(it.key, it.value) }

    val returnValue = GroovyShell(binding).evaluate(script)
    println("shellExec($params, $script) -> $returnValue")
    return returnValue
}

val scriptEngineManager = ScriptEngineManager()
val scriptEngine: ScriptEngine = scriptEngineManager.getEngineByName("groovy")

/**
 * 基于ScriptEngineManager(javax.script)实现
 */
fun scriptExec(params: Map<String, Any>, script: String): Any? {
    val bindings = SimpleBindings(params)
    val returnValue = scriptEngine.eval(script, bindings)
    println("scriptExec($params, $script) -> $returnValue")
    return returnValue
}


fun main() {
    // 执行一段关联的代码
    scriptExec(mapOf("context" to Context(), "job" to Job(), "x" to 20), rule5)

    // 加载一个类
    val calculator = scriptEngine.eval(calculatorScript)
    val add = calculator.javaClass.getMethod("add", *arrayOf(Int::class.java, Int::class.java))
    // 调用类的成员方法
    val value = add.invoke(calculator, 1, 2)
    println(value)
}

/**
 * 测试结论: 基于ScriptEngineManager的性能远好于GroovyShell
 */
private fun measureTestRulePerformance(f: testRuleFunc, times: Int) {
    println("------exec------")
    val runtime = Runtime.getRuntime()
    var reset = runtime.freeMemory()
    val ms0 = measureTimeMillis {
        for (i in 1..times) {
            f(mapOf("name" to "orange"), rule1)

            f(mapOf("x" to 1, "y" to 2, "z" to 3), rule2)
            f(mapOf("x" to 1, "y" to 1, "z" to 3), rule2)

            f(mapOf("x" to 1, "y" to listOf(1, 2, 3)), rule3)
            f(mapOf("x" to 4, "y" to listOf(1, 2, 3)), rule3)

            f(mapOf("x" to 1, "y" to 2), rule4)
            f(mapOf("x" to 1, "y" to 3), rule4)
            f(mapOf("x" to 2, "y" to 2), rule4)
        }
    }
    val used = reset - runtime.freeMemory()
    println("------finish, time->$ms0, memory->$used------")
}


class Context(val configValue: String) {
    constructor() : this("none")

    fun add(x: Int, y: Int) = x + y

    fun size(x: Int) = when {
        x < 0 -> "small"
        x < 10 -> "middle"
        else -> "big"
    }
}

class Job {
    fun run() = println("run somethings")
}