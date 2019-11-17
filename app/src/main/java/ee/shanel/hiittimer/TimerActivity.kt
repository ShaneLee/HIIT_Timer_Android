package ee.shanel.hiittimer

import android.os.Bundle
import android.os.CountDownTimer
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import ee.shanel.hiittimer.timer.HiitData
import ee.shanel.hiittimer.timer.Workout
import ee.shanel.hiittimer.timer.WorkoutSet

import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.content_timer.*

class TimerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val workoutSets = calclateTimer()
            startWorkout(workoutSets)
        }
    }

    private fun startWorkout(workoutSets: ArrayList<WorkoutSet>) {
        timerStatusText.setText("Go")
        timerStatusText.setText(workoutSets[0].status)
        object : CountDownTimer(workoutSets[0].secs, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutesRemaining = millisUntilFinished / 60000
                val secondsRemaining = (millisUntilFinished % 60000) / 1000
                val minutes = appendZero(minutesRemaining)
                val seconds = appendZero(secondsRemaining)
                val timerText = "${minutes} : ${seconds}"
                timer.setText(timerText)
            }
            override fun onFinish() {
                workoutSets.remove(workoutSets[0])
                startWorkout(workoutSets)
            }
        }.start()

    }


    private fun calclateTimer(): ArrayList<WorkoutSet> {
        val hiitData = getIntent().getExtras().getParcelable<HiitData>("hiitData")
        val workout: ArrayList<WorkoutSet> = ArrayList()

        for (i in 0..hiitData.sets) {
            val work = WorkoutSet("Workout", (hiitData.workoutSecs * 1000).toLong())
            val rest = WorkoutSet("Rest", (hiitData.restSecs * 1000).toLong())
            workout.add(work)

            if (i != hiitData.sets) {
                workout.add(rest)
            }
        }
        return workout
    }

    private fun appendZero(time: Long): String {
        val timeString = time.toString()
        return if (time < 10) "0$timeString" else timeString
    }

}
