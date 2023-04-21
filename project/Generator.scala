import sbt.*

object Generator {
  def gen(inputDir: File, outputDir: File, filesToTransform: Seq[String] = Seq(), emptyLineIndices: Map[String, Seq[Int]] = Map()): Unit = {
    val finder: PathFinder = inputDir ** "*.scala"

    for (inputFile <- finder.get) yield {
      val inputStr = IO.read(inputFile)
      val outputFile = outputDir / inputFile.relativeTo(inputDir).get.toString
      val outputStr =
        if (filesToTransform.isEmpty || filesToTransform.contains(inputFile.name))
          ScalametaTransformer.transform(inputStr, emptyLineIndices.getOrElse(inputFile.name, Seq()))
        else inputStr
      IO.write(outputFile, outputStr)
    }
  }
}