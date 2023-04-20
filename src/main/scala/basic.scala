import cool_implicit._
// Basic classes for Cool 2009 in Scala
// John Boyland
// January 2009

/** The IO class provides simple input and output operations */
class IO() {

  /** Terminates program with given message. */
  def abort(message : String) : Nothing = {
    println("Abort: " + message);
    System.exit(-1);
    throw new Error("Abort: " + message)
  }

  /** Print the argument (without quotes) to stdout and return itself */
  def out(arg : String) : IO = {
    print(arg); this
  }

  def is_null(arg : Any) : Boolean = {
    arg match { 
      case null => true 
      case x:Any => false 
    }
  }

  /** Convert to a string and print */
  def out_any(arg : Any) : IO = {
    out(if (is_null(arg)) "null" else arg.toString())
  }

  /** Read and return characters from stdin to the next newline character. */
  def in() : String = scala.io.StdIn.readLine();

  def symbol(name : String) : Symbol = Symbol(name);

  def symbol_name(sym : Symbol) : String = sym.name;

  def getArgC() : Int = cool_main.getArgC();

  def getArg(i : Int) : String = cool_main.getArgV(i);
}

/** An array is a mutable fixed-size container holding any objects.
 * The elements are numbered from 0 to size-1.
 * An array may be void.  It is not legal to inherit from ArrayAny.
 */
class ArrayAny(size : Int) {

  val array : Array[Any] = new Array(size);

  /** Return length of array. */
  def length() : Int = array.length;

  /** Return a new array of size s (the original array is unchanged).  
   * Any values in the original array that fit within the new array 
   * are copied over.  If the new array is larger than the original array,
   * the additional entries start void.  If the new array is smaller 
   * than the original array, entries past the end of the new array are 
   * not copied over.
   */
  def resize(s : Int) : ArrayAny = {
    val copy = new ArrayAny(s);
    var n : Int = s;
    if (n > length()) n = length;
    for (i <- 0 until n) {
      copy.array(i) = array(i);
    }
    copy
  }

  /* Returns the entry at location index.
   * precondition: 0 <= index < length()
   */
  def get(index : Int) : Any = array(index);

  /* change the entry at location index.
   * return the old value if any (or null)
   * precondition: 0 <= index < length()
   */
  def set(index : Int, obj : Any) : Any = {
    var result = array(index);
    array(index) = obj;
    result
  }
}

class Statistics() extends IO() {
  var base : Long = 0;
  private def reset() : Unit = {
    base = java.lang.System.currentTimeMillis();
  }
  { reset() };

  def clear() : Statistics = { reset(); this };
  def get(i : Int) : Int = {
     val diff = java.lang.System.currentTimeMillis() - base;
     diff.asInstanceOf[Int]
  }
  def print() : Statistics = {
     super.out("Execution time: " + get(0) + " milliseconds.\n"); this
  }
}

object cool_main {
  var programArgs : Array[String] = null;
  def getArgC() : Int = programArgs.length;
  def getArgV(i : Int) : String = programArgs(i);

  def main(args : Array[String]) : Unit = {
    programArgs = args;
    new Main()
  };
}



