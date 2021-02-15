package com.example.rehapp

class Patient(val id: String, val name: String, val trainingList: List<Training>){
    constructor() : this("", "", listOf())
}