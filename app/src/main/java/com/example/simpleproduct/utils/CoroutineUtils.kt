package com.example.simpleproduct.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
/**
 * Channel.CONFLATED creates a ConflatedChannel which always buffers the most recent item sent to
 * the channel and emits this to the receiver
 * */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> ConflatedChannel() = Channel<T>(Channel.CONFLATED)

/**
 * Sending an item to a channel is done by calling the send suspending function
 * */

fun <T> Channel<T>.sendValue(value: T) = runCatching { this.trySend(value) }

/**
 * Recieve an item to a channel is done by calling the collect suspending function
 * */
suspend inline fun ReceiveChannel<Unit>.collect(crossinline action: suspend () -> Unit) {
    try {
        val iterator = iterator()
        while (iterator.hasNext()) {
            iterator.next()
            action()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
}