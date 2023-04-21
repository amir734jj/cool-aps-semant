import scala.meta.*

object ScalametaTransformer {
  private val ifElseTransformer = new Transformer {
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
//        val thenExpr1 = super.apply(thenExpr).asInstanceOf[Term]
//        val elseExpr1 = super.apply(elseExpr).asInstanceOf[Term]
//        q"""
//        def $condName() = $condExpr1
//        def $thenName() = $thenExpr1
//        def $elseName() = $elseExpr1
//        if ($condName()) $thenName() else $elseName()
//      """
      case _ => super.apply(tree)
    }
  }

  private val classMethodTransformer = new Transformer {
    override def apply(tree: Tree): Tree = tree match {
      case q"..$mods class M_SEMANT[..$tparams] ..$ctorMods (...$paramss) $template" =>
        println("matches M_SEMANT")
        val template1 = template match {
          case template"{ ..$earlyStats } with ..$inits { $self => ..$stats }" =>
            println("matches template")
            val stats1 = stats.map {
              case q"..$mods def visit_1_1_0[..$tparams](...$paramss): $tpeopt = $expr" =>
                println("matches visit_1_1_0")
                val expr1 = ifElseTransformer(expr).asInstanceOf[Term]
                q"..$mods def visit_1_1_0[..$tparams](...$paramss): $tpeopt = $expr1"
              case t => t
            }

            template"{ ..$earlyStats } with ..$inits { $self => ..$stats1 }"
        }
        q"..$mods class M_SEMANT[..$tparams] ..$ctorMods (...$paramss) $template1"
      case _ => super.apply(tree)
    }
  }

  def transform(str: String): String = {
    val origTree = str.parse[Source].get
    val newTree = classMethodTransformer(origTree)
    newTree.toString
  }

}
