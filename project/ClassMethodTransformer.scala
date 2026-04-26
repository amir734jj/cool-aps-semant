import scala.meta.*

class ClassMethodTransformer(className: String, methodName: String, rhsTransformer: Transformer) extends TreeTransformer {
  override def apply(tree: Tree): Tree = tree match {
    case q"..$mods class ${tname@Type.Name(`className`)}[..$tparams] ..$ctorMods (...$paramss) $template" =>
      println("matches class")
      val template1 = template match {
        case template"{ ..$earlyStats } with ..$inits { $self => ..$stats }" =>
          println("matches template")
          val stats1 = stats.map {
            case q"..$mods def ${ename@Term.Name(`methodName`)}[..$tparams](...$paramss): $tpeopt = $expr" =>
              println("matches method")
              val expr1 = rhsTransformer(expr).asInstanceOf[Term]
              q"..$mods def $ename[..$tparams](...$paramss): $tpeopt = $expr1"
            case t => t
          }
          template"{ ..$earlyStats } with ..$inits { $self => ..$stats1 }"
      }
      q"..$mods class $tname[..$tparams] ..$ctorMods (...$paramss) $template1"
    case _ => super.apply(tree)
  }
}
