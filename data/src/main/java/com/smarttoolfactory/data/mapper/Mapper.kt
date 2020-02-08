package com.smarttoolfactory.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}