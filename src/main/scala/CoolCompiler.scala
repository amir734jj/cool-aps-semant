import cool_implicit._

/** The COOL Compiler main program.
 * John Boyland
 * The main program is written in Scala: "extended Cool"
 * (but we try to avoid using the features,
 *  as a Scala programmer will notice)
 */
object CoolCompiler extends IO() {
  def main(args:Array[String]) : Unit = {
    var i : Int = 0;
    var a2i : A2I = new A2I();
    var options : CoolOptions = new CoolOptions();
    var had_arg = false;
    while (i < args.length) {
      args(i) match {
        case "-h" => help()
	case "-l" => options.set_scan_debug(true)
	case "-p" => options.set_parse_debug(true)
	case "-s" => options.set_semant_debug(true)
	case "-a" => options.set_analysis_debug(true)
	case "-c" => options.set_cgen_debug(true)
	case "-O" => options.set_optimize(true)
	case "-g" => options.set_enable_gc(true)
	case "-b" => i = i + 1; check(i,args); options.set_basic_file(args(i))
	case "-A" => i = i + 1; check(i,args); options.set_analysis(args(i))
	case "-o" => i = i + 1; check(i,args);
	             options.set_out_filename(args(i))
	case "-1" => options.set_strip_attributes(1)
	case "-2" => options.set_strip_attributes(2)
	case "-f" => options.set_strip_attributes(10)
	case "-r" => options.set_right_to_left_children(true)
	case "-E" => i = i + 1; check(i,args); 
                     options.set_max_errors(a2i.a2i(args(i)));
	case "-P" => i = i + 1; check(i,args);
	             options.set_parse(false);
	             options.set_semant(false);
                     options.set_codegen(false);
	             var j : Int = 0;
	             var n : Int = args(i).length();
	             while (j < n) {
		       var ch : Int = args(i).charAt(j);
		       if (ch == 112) options.set_parse(true)
		       else if (ch == 115) options.set_semant(true)
		       else if (ch == 99) options.set_codegen(true)
		       else if (ch == 108) options.set_scan(true)
		       else if (ch == 97) ();
		       j = j + 1
		     }
        case x:String => handle_file(x,options); had_arg = true
      }
      i = i + 1;
    }
    if (!had_arg) help()
    else if (options.get_parse())
      if (errorsFound) abort("Parse errors found") 
      else handle(t_Tree.v_program(results),options)
    else if (!options.get_scan()) {
      println("Error: can't run as a separate phase.");
    };
  }

  def check(i : Int, args : Array[String]) : Unit = {
    if (i >= args.length) help();
  }

  def help() : Unit = {
    println("usage: coolc [-lvpscaOgTt -P lapsc -A opt -b basic.cl -o outname] input-files\n");
    abort("");
  }

  var results : Classes = null;

  var errorsFound : Boolean = false;

  var parser : CoolParser = null;

  var firstFilename : String = null

  def handle_file(filename : String, options : CoolOptions) : Unit = {
    if (is_null(firstFilename)) firstFilename = filename else ();
    if (options.get_parse()) do_parse(options,filename,false)
    else if (options.get_scan()) 
      do_scan(filename,filename == options.get_basic_file())
    else ()
  }

  def get_scanner(filename : String, is_basic : Boolean) : CoolScanner = {
    var scanner : CoolScanner = null;
    try {
      scanner = new CoolScanner(new java.io.FileReader(filename));
      scanner.set_in_basic_file(is_basic);
    } catch {
      case e:java.io.IOException => abort(e.toString());
    }
    return scanner;
  }

  def do_scan(filename : String, is_basic : Boolean) : Unit = {
    var scanner : CoolScanner = get_scanner(filename,is_basic);
    while (scanner.hasNext()) {
      println(scanner.next());
    }
  };

  def do_parse(options : CoolOptions, filename : String, is_basic : Boolean) : Unit = {
    if (results eq null) {
      results = new Classes_nil();
      parser = new CoolParser();
      parser.yydebug = options.get_parse_debug();
      parser.set_options(options);
      // println("Debugging is " + (if (parser.yydebug) "on" else "off"));
      do_parse(options,options.get_basic_file(),true);
    }
    val scanner = get_scanner(filename,is_basic);
    parser.reset(scanner,filename);
    if (parser.yyparse()) {
      results = results.concat(parser.result);
      if (parser.get_errors() > 0) errorsFound = true;
    } else {
      errorsFound = true;
    }
  }

  def handle(p : Program, options : CoolOptions) : Unit = {
    if (options.get_semant()) do_semant(p,options) else ();
  };
      
  def do_semant(p : Program, options : CoolOptions) : Unit = {
    if (!new Semant(p,options).run()) {
      abort("Semant errors found");
    } else ()
  };

}

class FileOutput(w : java.io.Writer) extends IO()
{
  override def out(s : String) : IO = {
    w.write(s);
    this
  };
}

