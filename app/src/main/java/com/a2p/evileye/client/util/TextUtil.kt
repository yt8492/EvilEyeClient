package com.a2p.evileye.client.util

fun String.hideWayback(): String {
    return try {
        val regex = """https?://""".toRegex()
        val prefix = regex.findAll(this).map(MatchResult::value).toList()[1]
        prefix + this.split(regex).drop(2).joinToString("")
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}