/////////////////////////////// 
// Evaluador de expresiones 
///////////////////////////////
header{
import java.util.*;

}
class Evaluador extends TreeParser;

options 
{ 
 importVocab = Anasint; 
} 
{Hashtable<String,String> fDef =new Hashtable<String,String>();
 Hashtable<String,ArrayList<Integer>> tablaM = new Hashtable<String,ArrayList<Integer>>();
 ArrayList<String> listaMain = new ArrayList<String>();
 	
	String creaFigura(ArrayList<String> fig,ArrayList<String> trans)
	{
		String resultado="";
		for(int i=0; i<fig.size()-1;i++)
		{
			resultado+=fig.get(i)+"\n";	
		}
		for(int i=0; i<trans.size();i++)
		{
			resultado+= trans.get(i)+"\n"; 	
		}
		return resultado;	
	}
	
	String getLast (ArrayList<String> lista)
	{
		return lista.get(lista.size()-1);
	}
	
	ArrayList<String> añadeTransform(String trans,ArrayList<String> listaTrans)
	{
		listaTrans.add(trans);
		return listaTrans;
	}
	void añadeDef(String def,String funcion)
	{
		fDef.put(def,funcion);	
	}
	String añadeMetodo(String funcion)
	{
		Integer i =2;
		ArrayList<Integer> lista=new ArrayList<Integer>();
		String resultado="";
		if(tablaM.containsKey(funcion))
		{
			lista = tablaM.get(funcion);
			i = lista.size()+1;
			resultado = funcion+i.toString();
			lista.add(i);
			listaMain.add(resultado);
			tablaM.put(funcion,lista);
		}
		else
		{
			resultado=funcion;
			listaMain.add(resultado);
			lista.add(0);
			tablaM.put(funcion,lista);	
		}
		
		return resultado;	
	}
	
	String confirmaEImprime(String use)
	{
		String resultado="";
		if(fDef.containsKey(use))
		{
			resultado = fDef.get(use);	
		}
		else
		{
			resultado = "\n¡No existe esa función!\n";	
		}
		return resultado;	
	}
	
	String displayFuncion()
	{
		String resultado="int main (int argc, char *argv){\n";
		resultado+="\tglutInit(&argc, argv);\n";
		resultado+="\tglutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);\n";
		resultado+="\tglutInitWindowSize(400,350);\n\n";
		for(int i= 0; i<listaMain.size();i++)
		{
			resultado+="\tglutDisplayFunc(display"+listaMain.get(i)+");\n";	
		}
		resultado+="\n\tglutMainLoop();\n";
		resultado+="\treturn 0;\n}";
		return resultado;	
	}
}
 
entrada {String n;ArrayList<String> trans=new ArrayList<String>();}: #(CABECERA
         {System.out.println("#include <GL"+'\\'+"glut.h>\n");} 
         #(LISTA_NODOS
          (n=nodo[trans]
         {System.out.println(n);}
         					)*))
         {System.out.println(displayFuncion());}
;
nodo[ArrayList<String> trans] returns [String result = ""] {ArrayList<String> a1;String a2="",a3="";}: 
	  (#(DEF ID_MAY #(NODO_SHAPE .)))=>
	   #(DEF def1:ID_MAY a1=nodo_Shape) 
	   {result="void display"+añadeMetodo(def1.getText())+"(void){\n"+
	   	"\tglMatrixMode(GL_MODELVIEW);\n"+
	   	"\tglClear(GL_COLOR_BUFFER_BIT);\n"+
	   	"\tglLoadIdentity();\n\n"+creaFigura(a1,trans)+"\tglFlush();\n"+"}\n";
	    añadeDef(def1.getText(),result);
	    trans.clear();}
	 |(#(DEF ID_MAY #(NODO_GROUP .)))=>
	   #(DEF def2:ID_MAY #(NODO_GROUP #(CHILDREN a2=nodo[trans] 
	   									 {result+=a2;}
	   		                       (a3=nodo[trans]{result+=a3;})*)))
	   {añadeDef(def2.getText(),result);}
	 |(#(DEF ID_MAY #(NODO_TRANSF .)))=>
	   #(DEF def3:ID_MAY #(NODO_TRANSF(#(TRANSLATION t1:NUMERO t2:NUMERO t3:NUMERO)
	   					          {a2+="\tglTranslatef("+t1.getText()+","+t2.getText()+","+t3.getText()+");\n";})?
	 					         (#(ROTATION r1:NUMERO r2:NUMERO r3:NUMERO r4:NUMERO)
	 					          {a2+="\tglRotatef("+r4.getText()+","+r1.getText()+","+r2.getText()+","+r3.getText()+");\n";})?
	 					         (#(SCALE s1:NUMERO s2:NUMERO s3:NUMERO)
	 					          {a2+="\tglScalef("+s1.getText()+","+s2.getText()+","+s3.getText()+");\n";})? 
	 					          #(CHILDREN 
	 					          {trans.add(a2);} a3=nodo[trans])))
	   {result = a3;
	    añadeDef(def3.getText(),result);}
	 |a1=nodo_Shape
	    {result="void display"+añadeMetodo(getLast(a1))+"(void){\n"+
	    	"\tglMatrixMode(GL_MODELVIEW);\n"+
	   	    "\tglClear(GL_COLOR_BUFFER_BIT);\n"+
	   	    "\tglLoadIdentity();\n\n"+creaFigura(a1,trans)+"\tglFlush();\n"+"}\n";
	     trans.clear();}
	 |#(NODO_GROUP #(CHILDREN a2=nodo[trans] 
	   					{result+=a2;}
	   		            (a3=nodo[trans]{result+=a3;})*))//PROBLEMA EN LA LISTA NO ESTÁ HACIENDO LAS TRANSFORMACIONES
	   
	   
	 |#(NODO_TRANSF(#(TRANSLATION tt1:NUMERO tt2:NUMERO tt3:NUMERO)
	   				  {a2+="\tglTranslatef("+tt1.getText()+","+tt2.getText()+","+tt3.getText()+");\n";})?
	 			   (#(ROTATION rr1:NUMERO rr2:NUMERO rr3:NUMERO rr4:NUMERO)
	 				  {a2+="\tglRotatef("+rr4.getText()+","+rr1.getText()+","+rr2.getText()+","+rr3.getText()+");\n";})?
	 			   (#(SCALE ss1:NUMERO ss2:NUMERO ss3:NUMERO)
	 				  {a2+="\tglScalef("+ss1.getText()+","+ss2.getText()+","+ss3.getText()+");\n";})? 
	 			    #(CHILDREN 
	 			    {trans.add(a2);} a3=nodo[trans]))
	   {result = a3;}
	 |#(USE h:ID_MAY)
	   {result=confirmaEImprime(h.getText());}
	 ;
nodo_aparienciaDef returns [String result=""]{String m1;}: #(DEF def:ID_MAY #(NODO_APPEARANCE #(MATERIAL m1=nodo_materialDef)))
												{result=m1;
												 añadeDef(def.getText(),m1);}
				                              |#(NODO_APPEARANCE #(MATERIAL m1=nodo_materialDef))
				                              	{result=m1;}
				                              |#(USE h:ID_MAY)
				                              	{result=confirmaEImprime(h.getText());}
				                              ;
nodo_materialDef returns [String result=""]{String rt="";}
: #(DEF def:ID_MAY rt=nodo_material)
				     {result=rt;
				      añadeDef(def.getText(),rt);}
				     |rt=nodo_material
				     {result=rt;}
				     |#(USE h:ID_MAY)
				     {result=confirmaEImprime(h.getText());}
				 
;

figura returns [ArrayList<String> result = new ArrayList()]
	:  #(NODO_BOX #(SIZE a1:NUMERO a2:NUMERO a3:NUMERO))
        {result.add("\tglutSolidCube("+a1.getText()+")"+";");
         result.add("\tglScalef("+a1.getText()+","+a2.getText()+","+a3.getText()+")"+";");
         result.add("Box");
         }
       |#(NODO_CONE #(HEIGHT b1:NUMERO)
				    #(BOTTOM_RADIUS b2:NUMERO)
				   (#(BOTTOM (TRUE|FALSE)))?
				   (#(SIDE (TRUE|FALSE)))?)
	    {result.add("\tglutSolidCone("+b1.getText()+","+b2.getText()+","+"50,50)"+";");
	     result.add("Cone");
	     }
       |#(NODO_CYLINDER #(HEIGHT c1:NUMERO)
						#(RADIUS c2:NUMERO)
					   (#(BOTTOM (TRUE|FALSE)))? 
					   (#(SIDE (TRUE|FALSE)))? 
					   (#(TOP (TRUE|FALSE)))?)
	    {result.add("\tglutSolidCylinder("+c2.getText()+","+c1.getText()+","+"50,50)"+";");
	     result.add("Cylinder");
	     }
       |#(NODO_SPHERE #(RADIUS d1:NUMERO))
        {result.add("\tglutSolidSphere("+d1.getText()+","+"50,50"+")");
         result.add("Sphere");
         }
       ;
nodo_Shape returns [ArrayList<String> result= new ArrayList<String>()]{String ap="";
								                                       ArrayList<String> fig;}
: 
#(NODO_SHAPE (#(APPEARANCE ap=nodo_aparienciaDef){result.add(ap);})? #(GEOMETRY fig=figura))
{result.addAll(fig);}

;

nodo_material returns [String result=""]{String rt="";}: #(NODO_MATERIAL 
					(#(DIFFUSE_COLOR d1:NUMERO d2:NUMERO d3:NUMERO)
	                 {rt+="\tGLfloat mat_diffuse[] = {"+d1.getText()+","+d2.getText()+","+d3.getText()+"};\n"+
		             "\tglMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);\n";})?
				    (#(EMISSIVE_COLOR e1:NUMERO e2:NUMERO e3:NUMERO)
		             {rt+="\tGLfloat mat_emissive[] = {"+e1.getText()+","+e2.getText()+","+e3.getText()+"};\n"+
		             "\tglMaterialfv(GL_FRONT, GL_EMISSION, mat_emissive);\n";})?
		            (#(SPECULAR_COLOR s1:NUMERO s2:NUMERO s3:NUMERO)
					 {rt+="\tGLfloat mat_specular[] = {"+s1.getText()+","+s2.getText()+","+s3.getText()+"};\n"+
				     "\tglMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);\n";})?
					(#(AMBIENT_INTENSITY a:NUMERO)
					 {rt+="\tGLfloat mat_ambient[] = {"+a.getText()+"};\n"+
				 	 "\tglMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);\n";})?
					(#(TRANSPARENCY t:NUMERO)
					 {rt+="\tGLfloat mat_transparency[] = {"+t.getText()+"};\n"+
				 	 "\tglMaterialfv(GL_FRONT, GL_TRANSPARENCY, mat_transparency);\n";})?
					(#(SHININESS s:NUMERO)
					 {rt+="\tGLfloat mat_shininess[] = {"+s.getText()+"};\n"+
				     "\tglMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);\n";})?)
				     
   {result=rt;}
;
