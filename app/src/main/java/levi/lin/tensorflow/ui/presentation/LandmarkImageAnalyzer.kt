package levi.lin.tensorflow.ui.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import levi.lin.tensorflow.domain.Classification
import levi.lin.tensorflow.domain.Classifier
import levi.lin.tensorflow.utility.centerCrop

class LandmarkImageAnalyzer(
    private val classifier: Classifier,
    private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if(frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(
                    desiredWidth = 300,
                    desiredHeight = 300
                )

            val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter++

        image.close()
    }
}