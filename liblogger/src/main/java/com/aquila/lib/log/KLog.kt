package com.aquila.lib.log

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat


fun Any.logE(vararg charSequence: CharSequence?): Any {
    KLog.stakeIndex++
    KLog.e(*charSequence)
    KLog.stakeIndex--
    return this
}

fun Any.logW(vararg charSequence: CharSequence?): Any {
    KLog.stakeIndex++
    KLog.w(*charSequence)
    KLog.stakeIndex--
    return this
}

fun Any.logD(vararg charSequence: CharSequence?): Any {
    KLog.stakeIndex++
    KLog.d(*charSequence)
    KLog.stakeIndex--
    return this
}

fun Any.logV(vararg charSequence: CharSequence?): Any {
    KLog.stakeIndex++
    KLog.v(*charSequence)
    KLog.stakeIndex--
    return this
}

fun Any.logI(vararg charSequence: CharSequence?): Any {
    KLog.stakeIndex++
    KLog.i(*charSequence)
    KLog.stakeIndex--
    return this
}

/***
 *@date 创建时间 2019-06-03 11:56
 *@author 作者: W.YuLong
 *@description 日志打印，并保存日志到文件中
 */
object KLog {
    private const val V = "V"
    private const val D = "D"
    private const val I = "I"
    private const val W = "W"
    private const val E = "E"
    private const val WTF = "WTF"
    private const val JSON = "JSON"
    private const val SYSO = "SYSO"

    //堆栈的索引
    @Volatile
    internal var stakeIndex = 4

    val JSON_INDENT = 4
    val LINE_SEPARATOR = System.getProperty("line.separator") ?: "/"

    private val logMaxCount = 3000

    private val TAG_DEFAULT = "KLog_"

    private var isShowLog = true


    @JvmStatic
    fun setIsShowLog(value: Boolean) {
        isShowLog = value
    }

    @JvmStatic
    fun v(vararg msg: CharSequence?) {
        printLog(V, *msg)
    }

    @JvmStatic
    fun d(vararg msg: CharSequence?) {
        printLog(D, *msg)
    }

    @JvmStatic
    fun i(vararg msg: CharSequence?) {
        printLog(I, *msg)
    }

    @JvmStatic
    fun w(vararg msg: CharSequence?) {
        printLog(W, *msg)
    }

    @JvmStatic
    fun wtf(vararg msg: CharSequence?) {
        printLog(WTF, *msg)
    }

    @JvmStatic
    fun e(vararg msg: CharSequence?) {
        printLog(E, *msg)
    }


    @JvmStatic
    fun syso(text: CharSequence?) {
        printLog(SYSO, text)
    }

    @JvmStatic
    fun json(jsonFormat: CharSequence?) {
        printLog(JSON, jsonFormat)
    }


    private fun getObjectsString(vararg objArgs: CharSequence?): String {
        return when (objArgs.size) {
            0 -> "Empty Params"
            1 -> objArgs[0]?.toString() ?: "params is null"
            else -> {
                val sb = StringBuilder()
                sb.append("多参数打印：\n")
                for (i in objArgs.indices) {
                    sb.append("Param[$i] = ${objArgs[i]}\n")
                }
                sb.toString()
            }
        }
    }

    fun printLine(tag: String, isTop: Boolean, type: String = D) {
        val line = if (isTop) {
            "╔═══════════════════════════════════════════════════════════════════════════════════════"
        } else {
            "╚═══════════════════════════════════════════════════════════════════════════════════════"
        }

        when (type) {
            I -> Log.i(tag, line)
            D -> Log.d(tag, line)
            V -> Log.v(tag, line)
            W -> Log.w(tag, line)
            E -> Log.e(tag, line)
            WTF -> Log.wtf(tag, line)
        }
    }

    fun printJson(tag: String, headString: String, msg: String) {
        var message: String
        try {
            if (msg.startsWith("{")) {
                val jsonObject = JSONObject(msg)
                message = jsonObject.toString(JSON_INDENT)
            } else if (msg.startsWith("[")) {
                val jsonArray = JSONArray(msg)
                message = jsonArray.toString(JSON_INDENT)
            } else {
                message = msg
            }
        } catch (e: JSONException) {
            message = msg
        }
        printLine(tag, true)
        message = headString + LINE_SEPARATOR + message
        val lines =
            message.split(LINE_SEPARATOR.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        for (line in lines) {
            Log.d(tag, "║ $line")
        }
        printLine(tag, false)
    }


    fun printDefault(type: String, tag: String, msg: String) {
        if (!isShowLog) {
            return
        }
        var index = 0
        val length = msg.length
        val countOfSub = length / logMaxCount
        if (countOfSub > 0) {
            for (i in 0 until countOfSub) {
                val sub = msg.substring(index, index + logMaxCount)
                printSub(type, tag, sub)
                index += logMaxCount
            }
            printSub(type, tag, msg.substring(index, length))
        } else {
            printSub(type, tag, msg)
        }

    }


    @JvmStatic
    fun httpLog(logList: List<String>?) {
        if (isShowLog) {
            val stackTrace = Thread.currentThread().stackTrace
            val targetElement = stackTrace[3]
            val tag = TAG_DEFAULT + targetElement.fileName
            val headString = "[(%s:%s).%s()] ".format(
                targetElement.fileName,
                targetElement.lineNumber,
                targetElement.methodName
            )
            printHttpLog(tag, headString, logList)
        }
    }

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    fun printHttpLog(tag: String, headString: String, logList: List<String>?) {
        if (logList != null) {
            printLine(tag, true, V)
            Log.v(tag, "║ $headString")
            var subLine: String
            logList.forEach { line ->
                subLine = line
                if (subLine.length > logMaxCount) {
                    var i = 1
                    while (subLine.length > logMaxCount) {
                        Log.v(
                            tag,
                            String.format(" ~~ %d ~~║ %s", i++, subLine.substring(0,
                                logMaxCount
                            ))
                        )
                        subLine = subLine.substring(logMaxCount)
                    }
                    if (subLine.length > 0) {
                        Log.v(tag, String.format(" ~~ %d ~~║ %s", i, subLine))
                    }
                } else {
                    Log.v(tag, "║ $subLine")
                }

            }
            printLine(tag, false, V)
        }
    }

    private fun printLog(type: String, vararg objects: CharSequence?) {

        val targetElement = Thread.currentThread().stackTrace[stakeIndex]
        val tag = TAG_DEFAULT + targetElement.fileName
        val headString = "[(%s:%s).%s()] ".format(
            targetElement.fileName, targetElement.lineNumber, targetElement.methodName
        )

        val msg: String = getObjectsString(*objects)
        if (isShowLog) {
            when (type) {
                JSON -> printJson(
                    tag,
                    headString,
                    msg
                )
                //            V, D, I, W, E, WTF,SYSO,
                else -> printDefault(type, tag, headString + msg)
            }
        }
    }


    private fun printSub(type: String, tag: String, sub: String) {
        when (type) {
            V -> Log.v(tag, sub)
            D -> Log.d(tag, sub)
            I -> Log.i(tag, sub)
            W -> Log.w(tag, sub)
            E -> Log.e(tag, sub)
            WTF -> Log.wtf(tag, sub)
            SYSO -> println(sub)
        }


    }

}