package com.example.common.navigation

import kotlinx.serialization.Serializable


sealed class SubGraphDest {

    @Serializable
    data object RecipeSearch : SubGraphDest()

}
