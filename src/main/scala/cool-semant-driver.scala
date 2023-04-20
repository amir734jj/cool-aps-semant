import cool_implicit._;

class Semant(p : Program, o : CoolOptions) {
  def run() : Boolean = {
    if (o.get_semant_debug()) {
      Debug.activate();
    }
    val t_Semant = new M_SEMANT[T_Tree]("Semant",t_Tree);

    t_Semant.finish();
    val errors = t_Semant.v_errors;
    for (m <- errors) {
      println(m)
    };
    errors.size == 0
    true
  }
}
