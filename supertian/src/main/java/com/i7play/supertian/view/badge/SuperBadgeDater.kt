package com.i7play.supertian.view.badge

import java.io.Serializable
import java.util.HashMap

class SuperBadgeDater private constructor() : Serializable {

    internal var map: MutableMap<String, SuperBadgeHelper> = HashMap()

    fun addBadge(superBadge: SuperBadgeHelper) {
        map[superBadge.tag!!] = superBadge
    }

    fun getBadge(tag: String): SuperBadgeHelper {
        return map[tag]!!
    }

    companion object {

        val instance = SuperBadgeDater()
    }
}