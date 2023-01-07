package com.task.project.get

import com.task.project.model.Location
import retrofit2.Call
import retrofit2.http.GET

interface JSONPlaceholder {
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @get:GET("places.json")
    val location: Call<List<Location?>?>?
}