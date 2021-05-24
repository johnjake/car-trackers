package com.cartrackers.app.comms

import com.cartrackers.app.BuildConfig
import com.cartrackers.app.data.vo.DistanceDuration
import com.cartrackers.app.data.vo.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.google.maps.GeoApiContext
import kotlin.math.*

typealias listCoorDinates = List<LatLng>
typealias listAddress = Array<String>
class GeocodeSource {
    companion object {
        private const val apiKey: String = BuildConfig.API_KEY
        private val geoContext = GeoApiContext
            .Builder()
            .apiKey(apiKey)
            .build()

        /** manual calculation */
        private suspend fun calcDistanceDuration(origin: GeoLocation, destination: GeoLocation): DistanceDuration? {
            return try {
                null
            } catch (ex: Exception) {
                val distanceMetres = straightLineDistance(origin.lat.toDouble(), destination.lat.toDouble(), origin.lng.toDouble(), destination.lng.toDouble(), 0.0, 0.0).toInt()
                val durationSeconds = distanceMetres / (40000/3600) //avg speed 40kph
                DistanceDuration(distanceMetres, durationSeconds)
            }

        }
    }
}

/**
 * Calculate distance between two points in latitude and longitude taking
 * into account height difference. If you are not interested in height
 * difference pass 0.0. Uses Haversine method as its base.
 *
 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
 * el2 End altitude in meters
 * @returns Distance in Meters
 */
fun straightLineDistance(lat1: Double, lat2: Double, lon1: Double,
                         lon2: Double, el1: Double, el2: Double): Double {
    val radius = 6371 // Radius of the earth
    val latDistance = Math.toRadians(lat2 - lat1)
    val lonDistance = Math.toRadians(lon2 - lon1)
    val a = (sin(latDistance / 2) * sin(latDistance / 2)
        + (cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
        * sin(lonDistance / 2) * sin(lonDistance / 2)))
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    var distance = radius * c * 1000 // convert to meters
    val height = el1 - el2
    distance = distance.pow(2.0) + height.pow(2.0)
    return sqrt(distance)
}
