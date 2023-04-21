import sbt.*

object Generator {
  val ALL: Seq[String] = Seq()

  def isAll(filesToTransform: Seq[String]): Boolean = filesToTransform.isEmpty

  def gen(
           inputDir: File,
           outputDir: File,
           filesToTransform: Seq[String] = ALL,
           emptyLineIndices: Map[String, Seq[Int]] = Map(),
           className: String,
           methodName: String,
         ): Unit = {
    val finder: PathFinder = inputDir ** "*.scala"
    val scalametaTransformer = new ClassMethodTransformer(className, methodName, IfElseTransformer)

    for (inputFile <- finder.get) yield {
      val inputFileName = inputFile.name
      val indices = emptyLineIndices.getOrElse(inputFileName, Seq())
      val emptyLineTransformer = new EmptyLineTransformer(indices)
      val inputStr = IO.read(inputFile)
      val transform: String => String =
        if (isAll(filesToTransform) || filesToTransform.contains(inputFileName))
          (scalametaTransformer(_: String)) andThen emptyLineTransformer.apply
        else identity
      val outputStr = transform(inputStr)
      val outputFile = outputDir / inputFile.relativeTo(inputDir).get.toString
      IO.write(outputFile, outputStr)
    }
  }
}