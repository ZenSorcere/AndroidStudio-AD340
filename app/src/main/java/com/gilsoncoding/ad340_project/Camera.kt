package com.gilsoncoding.ad340_project

data class Camera (val Features:List<Location>) {

    data class Location (val PointCoordinate: List<Double>, val Cameras: List<CamAttr>)

    data class CamAttr ( val Id: String, val Description: String, val ImageUrl: String, val Type: String )
}

