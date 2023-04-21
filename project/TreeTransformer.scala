import scala.meta.*

trait TreeTransformer extends Transformer with StringTransformer {
  override def apply(str: String): String = {
    val origTree = str.parse[Source].get
    val newTree  = apply(origTree)
    newTree.toString
  }
}
