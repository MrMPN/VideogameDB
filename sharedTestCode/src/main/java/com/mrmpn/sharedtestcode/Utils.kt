package com.mrmpn.sharedtestcode

import com.google.common.truth.Correspondence
import com.google.common.truth.Truth
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
inline fun <reified T> assertIsInstance(value: Any?) {
    contract {
        returns() implies (value is T)
    }
    Truth.assertThat(value).isInstanceOf(T::class.java)
}

inline fun <reified T> loadFileText(
    caller: T,
    filePath: String
): String =
    T::class.java.getResource(filePath)?.readText() ?: throw IllegalArgumentException(
        "Could not find file $filePath. Make sure to put it in the correct resources folder for $caller's runtime."
    )

val isInstanceCorrespondence: Correspondence<Any, Class<*>> =
    Correspondence.transforming({ it!!::class.java }, "is instance of")

fun Iterable<*>.containsExactlyInstancesOf(vararg classes: Class<*>) {
    Truth.assertThat(this).comparingElementsUsing(isInstanceCorrespondence)
        .containsExactlyElementsIn(
            classes
        )
}