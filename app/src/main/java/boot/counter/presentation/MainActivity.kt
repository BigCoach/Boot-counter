package boot.counter.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import boot.counter.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}