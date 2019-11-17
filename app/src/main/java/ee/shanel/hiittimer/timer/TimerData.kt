package ee.shanel.hiittimer.timer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HiitData (var workoutSecs: Int, val restSecs: Int, val sets: Int): Parcelable

data class Workout(val sets: ArrayList<WorkoutSet>)

data class WorkoutSet(val status: String, val secs: Long)
