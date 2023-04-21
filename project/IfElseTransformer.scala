import scala.meta.*

object IfElseTransformer extends TreeTransformer {
  override def apply(tree: Tree): Tree = tree match {
    case q"if ($condExpr) $thenExpr else $elseExpr" =>
      println(s"matches if-else")
      val condName = Term.fresh("cond")
      val thenName = Term.fresh("then")
      val elseName = Term.fresh("else")
      q"""
          def $condName(): Boolean = $condExpr
          def $thenName() = $thenExpr
          def $elseName() = $elseExpr
          if ($condName()) $thenName() else $elseName()
        """
    // apply recursively:
//      val condExpr1 = super.apply(condExpr).asInstanceOf[Term]
//      val thenExpr1 = super.apply(thenExpr).asInstanceOf[Term]
//      val elseExpr1 = super.apply(elseExpr).asInstanceOf[Term]
//      q"""
//        def $condName() = $condExpr1
//        def $thenName() = $thenExpr1
//        def $elseName() = $elseExpr1
//        if ($condName()) $thenName() else $elseName()
//      """
    case _ => super.apply(tree)
  }
}