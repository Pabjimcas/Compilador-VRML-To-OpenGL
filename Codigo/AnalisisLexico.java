///////////////////////////////
// Analizador léxico
///////////////////////////////
class Analex extends Lexer;

options{
	importVocab = Anasint;
	charVocabulary = '\3'..'\377';
	k=3;
}

tokens{
NODO_SHAPE = "Shape";
NODO_BOX = "Box";
NODO_CONE = "Cone";
NODO_CYLINDER = "Cylinder";
NODO_SPHERE = "Sphere";
NODO_GROUP = "Group"; //Permite unir un conjunto de nodos de forma única
NODO_TRANSF = "Transform";//Se utiliza para aplicar transformaciones a las figuras
NODO_APPEARANCE = "Appearance";
NODO_MATERIAL = "Material";
BOTTOM_RADIUS = "bottomRadius";
BOTTOM = "bottom";
TRANSLATION = "translation"; 
TRANSPARENCY = "transparency"; 
SPECULAR_COLOR = "specularColor";
}
protected NUEVA_LINEA: "\r\n"
{newline();};
BLANCO: (' '|'\t'|NUEVA_LINEA)
{$setType(Token.SKIP);};
protected DIGITO: '0'..'9';
protected LETRA_MIN : ('a'..'z')|'_';
protected LETRA_MAY : ('A'..'Z');
NUMERO: ('-')?(DIGITO)+('.'(DIGITO)+)?;
IDENT: LETRA_MIN (LETRA_MIN|LETRA_MAY|DIGITO)*;
TRUE: "TRUE";
FALSE: "FALSE";
AGRUPADOR: '{'|'}'|'['|']';
SEPARADOR: ',';

CABECERA: "#VRML V2.0 utf8";
COMENTARIO: ("#" (options {greedy=false;}:.)* "\n")
					{$setType(Token.SKIP);}; 
APPEARANCE: "appearance";//Indica apariencia que pueda tener nuestra figura
MATERIAL: "material";
GEOMETRY: "geometry"; //Indica las caracteristicas geometricas de la figura
SIZE: "size";
HEIGHT: "height";
SIDE: "side";
RADIUS: "radius";
TOP: "top";

CHILDREN: "children";

ROTATION: "rotation";
SCALE: "scale";
DEF: "DEF";
USE: "USE";
ID_MAY: LETRA_MAY(LETRA_MIN|LETRA_MAY)*;

DIFFUSE_COLOR: "diffuseColor";
EMISSIVE_COLOR: "emissiveColor";
AMBIENT_INTENSITY: "ambientIntensity";
SHININESS: "shininess";





					
	
	









///////////////////////////////
// Analizador léxico
///////////////////////////////
class Analex extends Lexer;

options{
	importVocab = Anasint;
	charVocabulary = '\3'..'\377';
	k=3;
}

tokens{
NODO_SHAPE = "Shape";
NODO_BOX = "Box";
NODO_CONE = "Cone";
NODO_CYLINDER = "Cylinder";
NODO_SPHERE = "Sphere";
NODO_GROUP = "Group"; //Permite unir un conjunto de nodos de forma única
NODO_TRANSF = "Transform";//Se utiliza para aplicar transformaciones a las figuras
NODO_APPEARANCE = "Appearance";
NODO_MATERIAL = "Material";
BOTTOM_RADIUS = "bottomRadius";
BOTTOM = "bottom";
TRANSLATION = "translation"; 
TRANSPARENCY = "transparency"; 
SPECULAR_COLOR = "specularColor";
}
protected NUEVA_LINEA: "\r\n"
{newline();};
BLANCO: (' '|'\t'|NUEVA_LINEA)
{$setType(Token.SKIP);};
protected DIGITO: '0'..'9';
protected LETRA_MIN : ('a'..'z')|'_';
protected LETRA_MAY : ('A'..'Z');
NUMERO: ('-')?(DIGITO)+('.'(DIGITO)+)?;
IDENT: LETRA_MIN (LETRA_MIN|LETRA_MAY|DIGITO)*;
TRUE: "TRUE";
FALSE: "FALSE";
AGRUPADOR: '{'|'}'|'['|']';
SEPARADOR: ',';

CABECERA: "#VRML V2.0 utf8";
COMENTARIO: ("#" (options {greedy=false;}:.)* "\n")
					{$setType(Token.SKIP);}; 
APPEARANCE: "appearance";//Indica apariencia que pueda tener nuestra figura
MATERIAL: "material";
GEOMETRY: "geometry"; //Indica las caracteristicas geometricas de la figura
SIZE: "size";
HEIGHT: "height";
SIDE: "side";
RADIUS: "radius";
TOP: "top";

CHILDREN: "children";

ROTATION: "rotation";
SCALE: "scale";
DEF: "DEF";
USE: "USE";
ID_MAY: LETRA_MAY(LETRA_MIN|LETRA_MAY)*;

DIFFUSE_COLOR: "diffuseColor";
EMISSIVE_COLOR: "emissiveColor";
AMBIENT_INTENSITY: "ambientIntensity";
SHININESS: "shininess";





					
	
	









