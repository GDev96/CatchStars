package it.uninsubria.catchstars.data.model

class Astronauta(private val date: String,
                 private val points: Int,
                 private val time: String){

     fun getDate(): String {
          return date
     }

     fun getPoints(): Int{
          return points
     }

     fun getTime(): String{
          return time
     }

}

