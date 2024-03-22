package levi.lin.tensorflow.domain

import android.graphics.Bitmap

interface Classifier {
    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}