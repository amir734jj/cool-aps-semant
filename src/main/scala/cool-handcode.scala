object cool_implicit {
  object m_Tree extends M_COOL("CoolTree") {};
  val t_Tree = m_Tree.t_Result;
  type T_Tree = m_Tree.T_Result;

  type Program = t_Tree.T_Program;

  type Class = t_Tree.T_Class;
  type Classes = t_Tree.T_Classes;
  type Feature = t_Tree.T_Feature;
  type Features = t_Tree.T_Features;
  type Formal = t_Tree.T_Formal;
  type Formals = t_Tree.T_Formals;
  type Expression = t_Tree.T_Expression;
  type Expressions = t_Tree.T_Expressions;
  type Case = t_Tree.T_Case;
  type Cases = t_Tree.T_Cases;

  type Cclass_decl = t_Tree.T_Class;
  
  class Classes_nil() extends t_Tree.t_Classes.c_none() {
    register;
  }
  class Classes_one(x:Class) extends t_Tree.t_Classes.c_single(x) {
    register;
  }

  class Features_nil() extends t_Tree.t_Features.c_none() {
    register;
  }
  class Features_one(x:Feature) extends t_Tree.t_Features.c_single(x) {
    register;
  }

  class Formals_nil() extends t_Tree.t_Formals.c_none() {
    register;
  }
  class Formals_one(x:Formal) extends t_Tree.t_Formals.c_single(x) {
    register;
  }

  class Expressions_nil() extends t_Tree.t_Expressions.c_none() {
    register;
  }
  class Expressions_one(x:Expression) extends t_Tree.t_Expressions.c_single(x) {
    register;
  }

  class Cases_nil() extends t_Tree.t_Cases.c_none() {
    register;
  }
  class Cases_one(x:Case) extends t_Tree.t_Cases.c_single(x) {
    register;
  }
}
import cool_implicit._;

class Main { }

class CoolParserBase() {
  def get_line_number() : Int = 0;

  def preset_node_numbers() : Unit = {
    PARSE.lineNumber = get_line_number();
  };

  def program(classes:Classes) = {
    preset_node_numbers();
    t_Tree.v_program(classes)
  };

  def class_decl(name:Symbol,parent:Symbol,features:Features,filename:Symbol) = {
    preset_node_numbers();
    t_Tree.v_class_decl(name,parent,features,filename)
  };

  def method(overridep:Boolean,name:Symbol,formals:Formals,return_type:Symbol,expr:Expression) = {
    preset_node_numbers();
    t_Tree.v_method(overridep,name,formals,return_type,expr)
  };

  def attr(name:Symbol,of_type:Symbol) = {
    preset_node_numbers();
    t_Tree.v_attr(name,of_type)
  };

  def formal(name:Symbol,of_type:Symbol) = {
    preset_node_numbers();
    t_Tree.v_formal(name,of_type)
  };

  def branch(name:Symbol,local_type:Symbol,expr:Expression) = {
    preset_node_numbers();
    t_Tree.v_branch(name,local_type,expr)
  };

  def assign(name:Symbol,expr:Expression) = {
    preset_node_numbers();
    t_Tree.v_assign(name,expr)
  };

  def static_dispatch(expr:Expression,type_name:Symbol,name:Symbol,actuals:Expressions) = {
    preset_node_numbers();
    t_Tree.v_static_dispatch(expr,type_name,name,actuals)
  };

  def dispatch(expr:Expression,name:Symbol,actuals:Expressions) = {
    preset_node_numbers();
    t_Tree.v_dispatch(expr,name,actuals)
  };

  def cond(pred:Expression,then_exp:Expression,else_exp:Expression) = {
    preset_node_numbers();
    t_Tree.v_cond(pred,then_exp,else_exp)
  };

  def loop(pred:Expression,body:Expression) = {
    preset_node_numbers();
    t_Tree.v_loop(pred,body)
  };

  def typecase(expr:Expression,cases:Cases) = {
    preset_node_numbers();
    t_Tree.v_typecase(expr,cases)
  };

  def block(body:Expressions) = {
    preset_node_numbers();
    t_Tree.v_block(body)
  };

  def let(identifier:Symbol,local_type:Symbol,init:Expression,body:Expression) = {
    preset_node_numbers();
    t_Tree.v_let(identifier,local_type,init,body)
  };

  def add(e1:Expression,e2:Expression) = {
    preset_node_numbers();
    t_Tree.v_add(e1,e2)
  };

  def sub(e1:Expression,e2:Expression) = {
    preset_node_numbers();
    t_Tree.v_sub(e1,e2)
  };

  def mul(e1:Expression,e2:Expression) = {
    preset_node_numbers();
    t_Tree.v_mul(e1,e2)
  };

  def div(e1:Expression,e2:Expression) = {
    preset_node_numbers();
    t_Tree.v_div(e1,e2)
  };

  def neg(e1:Expression) = {
    preset_node_numbers();
    t_Tree.v_neg(e1)
  };

  def lt(e1:Expression,e2:Expression) = {
    preset_node_numbers();
    t_Tree.v_lt(e1,e2)
  };

  def leq(e1:Expression,e2:Expression) = {
    preset_node_numbers();
    t_Tree.v_leq(e1,e2)
  };

  def comp(e1:Expression) = {
    preset_node_numbers();
    t_Tree.v_comp(e1)
  };

  def int_lit(token:Symbol) = {
    preset_node_numbers();
    t_Tree.v_int_lit(token)
  };

  def bool_lit(value:Boolean) = {
    preset_node_numbers();
    t_Tree.v_bool_lit(value)
  };

  def string_lit(token:Symbol) = {
    preset_node_numbers();
    t_Tree.v_string_lit(token)
  };

  def alloc(type_name:Symbol) = {
    preset_node_numbers();
    t_Tree.v_alloc(type_name)
  };

  def nil() = {
    preset_node_numbers();
    t_Tree.v_nil()
  };

  def unit() = {
    preset_node_numbers();
    t_Tree.v_unit()
  };

  def no_expr() = {
    preset_node_numbers();
    t_Tree.v_no_expr()
  };

  def variable(name:Symbol) = {
    preset_node_numbers();
    t_Tree.v_variable(name)
  };
}
