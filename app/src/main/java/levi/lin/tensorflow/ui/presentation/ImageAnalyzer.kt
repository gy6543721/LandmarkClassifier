package levi.lin.tensorflow.ui.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import levi.lin.tensorflow.domain.Classification
import levi.lin.tensorflow.domain.Classifier
import levi.lin.tensorflow.utility.centerCrop

class ImageAnalyzer(
    private val classifier: Classifier,
    private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if(frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            // Default size for TensorFlow input
            val bitmap = image
                .toBitmap()
                .centerCrop(
                    desiredWidth = 321,
                    desiredHeight = 321
                )

            val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter++

        image.close()
    }
}