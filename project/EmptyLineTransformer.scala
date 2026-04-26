import scala.util.Properties

class EmptyLineTransformer(indices: Seq[Int]) extends StringTransformer {
  override def apply(str: String): String =
    indices.foldLeft(str)(EmptyLineTransformer.insertEmptyLine)
}
object EmptyLineTransformer {
  def insertEmptyLine(str: String, index: Int): String =
    str.linesIterator.patch(index, Iterator.fill(1)(""), 0).mkString(Properties.lineSeparator)
}
