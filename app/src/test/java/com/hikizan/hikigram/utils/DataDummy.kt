package com.hikizan.hikigram.utils

import com.hikizan.hikigram.domain.story.model.response.Story

object DataDummy {

    fun generateDummyStoryResponse(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                i.toString(),
                "name + $i",
                "description $i",
                "https://story-api.dicoding.dev/images/stories/${i}.jpg",
                "${2023+i}-07-17T14:04:17.992Z",
                null,
                null
            )
            items.add(story)
        }
        return items
    }
}