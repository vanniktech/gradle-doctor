package com.osacky.doctor

import io.reactivex.Observable
import org.gradle.api.internal.tasks.compile.CompileJavaBuildOperationType
import org.gradle.internal.operations.OperationFinishEvent
import org.gradle.internal.operations.OperationProgressEvent
import org.gradle.internal.operations.OperationStartEvent
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

interface OperationEvents {

    fun starts() : Observable<OperationStartEvent>
    fun progress() : Observable<OperationProgressEvent>
    fun finishes() : Observable<OperationFinishEvent>
    fun <T : Any> progressDetailsOfType(clazz : Class<T>) : Observable<T> {
        return progress()
                .filter { it.details != null }
                .map { it.details }
                .filter { clazz.isAssignableFrom(it::class.java) }
                .cast(clazz)
    }
    fun <T : Any> finisheResultsOfType(clazz : Class<T>) : Observable<T> {
        return finishes()
                .filter { it.result != null }
                .map { it.result }
                .filter { clazz.isAssignableFrom(it::class.java) }
                .cast(clazz)
    }
}
