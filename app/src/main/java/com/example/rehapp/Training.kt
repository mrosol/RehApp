package com.example.rehapp

class Training(val date: String, val pulse: Int, val sysPressure: Int, val diasPress: Int, val temperature: Float) {
    constructor() : this("",0,0,0, 0F)
}