package com.example.simpleproduct.utils

class PostBodyHolder(
    /**
     * The URL for this body.
     */
    var url: String) {

    /**
     * The params of this body.
     */
    val params: MutableList<Param> = ArrayList()

    fun add(name: String, value: String): PostBodyHolder {
        params.add(Param(name, value))
        return this
    }

    /**
     * Holder for body params.
     */
    inner class Param(
        /**
         * The name of the Param
         */
        var name: String,
        /**
         * The Value of the Param
         */
        var value: String)
}