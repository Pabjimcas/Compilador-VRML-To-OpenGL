/////////////////////////////////////
// Procesador.java (clase principal)
/////////////////////////////////////
import java.io.*;
import antlr.collections.AST;
import antlr.*;
import antlr.debug.misc.*;

public class Procesador {
	public static void main(String args[]) {
		try {
			FileInputStream fis = new FileInputStream("entrada");
			
			Analex analex = null;
			Anasint anasint = null;
			Evaluador evaluador = null; 
			AST arbol = null;
			analex = new Analex(fis);
			anasint = new Anasint(analex);
			anasint.entrada();
			arbol = anasint.getAST();
			
			ASTFrame frame= new ASTFrame("entrada",arbol);
			frame.setVisible(true);
			evaluador = new Evaluador(); 
	        evaluador.entrada(arbol);

	        
		} catch (ANTLRException re) {
			System.err.println(re.getMessage());
		} catch (FileNotFoundException fnfe) {
			System.err.println("No existe el fichero");
		}
	}
}
