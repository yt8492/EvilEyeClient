package com.a2p.evileye.client.util

fun String.hideWayback(): String {
    val regex = """https?://""".toRegex()
    val prefix = regex.findAll(this).map(MatchResult::value).toList()[1]
    return prefix + this.split(regex).drop(2).joinToString("")
}