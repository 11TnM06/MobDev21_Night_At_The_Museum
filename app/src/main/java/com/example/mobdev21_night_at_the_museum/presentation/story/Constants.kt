package com.example.mobdev21_night_at_the_museum.presentation.story

import com.example.mobdev21_night_at_the_museum.domain.model.Story
import com.example.mobdev21_night_at_the_museum.domain.model.StoryDetailModel

object Constants {
    fun getStoryData():ArrayList<Story>{
        // create an arraylist of type employee class
        val employeeList=ArrayList<Story>()
        val storyDetailModels : ArrayList<StoryDetailModel> = ArrayList<StoryDetailModel>()
        val sd1 = StoryDetailModel("DESCRIP1", "https://picsum.photos/10000")
        val sd2 = StoryDetailModel("DESCRIP2", "https://picsum.photos/10000")
        val sd3 = StoryDetailModel("DESCRIP2", "https://picsum.photos/1000")
        val sd4 = StoryDetailModel("DESCRIP2", "https://picsum.photos/1000")
        val sd5 = StoryDetailModel("DESCRIP2", "https://picsum.photos/1000")
        val sd6 = StoryDetailModel("DESCRIP2", "https://picsum.photos/1000")

        storyDetailModels.add(sd1)
        storyDetailModels.add(sd2)
        storyDetailModels.add(sd3)
        storyDetailModels.add(sd4)
        storyDetailModels.add(sd5)
        storyDetailModels.add(sd6)


        val emp1=Story("Chinmaya Mohapatra","chinmaya@gmail.com", "https://picsum.photos/200", "1", storyDetailModels)
        employeeList.add(emp1)
        val emp2=Story("Chinmaya Mohapatra","chinmaya@gmail.com", "https://picsum.photos/200", "1", storyDetailModels)
        employeeList.add(emp2)
        val emp3=Story("Chinmaya Mohapatra","chinmaya@gmail.com", "https://picsum.photos/200", "1", storyDetailModels)
        val emp4=Story("Chinmaya Mohapatra","chinmaya@gmail.com", "https://picsum.photos/200", "1", storyDetailModels)
        employeeList.add(emp3)
        employeeList.add(emp4)

        return  employeeList
    }
}