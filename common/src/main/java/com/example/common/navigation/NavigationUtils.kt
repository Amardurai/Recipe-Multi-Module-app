package com.example.common.navigation

import kotlinx.serialization.Serializable


sealed class SubGraphDest {

    @Serializable
    data object RecipeSearch : SubGraphDest()

}


sealed class Dest {

    @Serializable
    data object RecipeList : Dest()

    @Serializable
    data class RecipeDetail(val id:String): Dest()
}