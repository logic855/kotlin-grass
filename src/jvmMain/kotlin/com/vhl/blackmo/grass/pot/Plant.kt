package com.vhl.blackmo.grass.pot

import com.vhl.blackmo.grass.context.GrassParserContext
import com.vhl.blackmo.grass.vein.DateTimeTypes
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Top level implementation of conversion engine of csv contents to **data class**
 * @param ctx context configuration [GrassParserContext]
 * @param type data class definition
 * @author blackmo18
 */
@ExperimentalStdlibApi
actual class Plant<T> actual constructor(val ctx: GrassParserContext, type: KClass<*>)
    : Stem<T>(type, ctx.trimWhiteSpace, ctx.customKeyMap) {

    actual val dateTimeTypes = DateTimeTypes(ctx.dateFormat, ctx.timeFormat, ctx.dateTimeSeparator)

    override fun getType(type: KType) = when {
        dateTimeTypes.mapTypes.containsKey(type) -> dateTimeTypes.mapTypes[type]
        else -> super.getType(type)
    }

    /**
     * @return [List&lt;T&gt;] where T is the target data class
     */
    actual fun harvest(seed: List<Map<String, String>>): List<T> {
        return harvestData(seed)
    }

    /**
     * @return [Sequence&lt;T&gt;] where T is the target data class
     */
    actual fun harvest(seed: Sequence<Map<String, String>>): Sequence<T> {
        return harvestData(seed)
    }
}