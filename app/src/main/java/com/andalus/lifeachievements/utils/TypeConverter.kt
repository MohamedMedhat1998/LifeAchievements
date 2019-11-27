package com.andalus.lifeachievements.utils

import com.andalus.lifeachievements.type.Type

class TypeConverter {
    companion object {
        fun getType(type: String): Type {
            return when (type) {
                Type.GET_RID_OF.toString() -> Type.GET_RID_OF
                Type.DO_IT.toString() -> Type.DO_IT
                else -> Type.`$UNKNOWN`
            }
        }
    }
}