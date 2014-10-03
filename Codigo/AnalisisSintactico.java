/////////////////////////////// 
// Analizador sintáctico 
/////////////////////////////// 
class Anasint extends Parser; 

options 
{ 
 buildAST = true; 
}
tokens{
LISTA_NODOS;
} 

entrada : CABECERA^ nodos EOF!;
nodos: (nodoDef)*
	 {##=#(#[LISTA_NODOS,"LISTA_NODOS"],##);} 
              ; 
use: USE^ ID_MAY;

nodoDef:(DEF ID_MAY) => DEF^ ID_MAY nodo
		|nodo
		|use;
nodo: nodo_Shape
	 |nodo_transform
	 |nodo_grupo
	 ;
//NODO SHAPE//
nodo_Shape: NODO_SHAPE^ "{"! apariencia geometria "}"!;
apariencia: (APPEARANCE^ nodo_aparienciaDef)?;
geometria: GEOMETRY^ figura;

nodo_aparienciaDef:(DEF ID_MAY) => DEF^ ID_MAY nodo_apariencia
		           |nodo_apariencia
				   |use;
nodo_apariencia: NODO_APPEARANCE^ "{"! material "}"!;
material: MATERIAL^ nodo_materialDef;
nodo_materialDef:(DEF ID_MAY) => DEF^ ID_MAY nodo_material
		           |nodo_material
				   |use;
nodo_material: NODO_MATERIAL^ "{"! diffuse_color //Los 3 numeros representan los colores RGB
								 emissive_color
								 specular_color
								 ambient
								 transparency
								 shininess "}"!;
								 
diffuse_color: (DIFFUSE_COLOR^ NUMERO NUMERO NUMERO)?;
emissive_color: (EMISSIVE_COLOR^ NUMERO NUMERO NUMERO)?;
specular_color: (SPECULAR_COLOR^ NUMERO NUMERO NUMERO)?;
ambient: (AMBIENT_INTENSITY^ NUMERO)?;
transparency: (TRANSPARENCY^ NUMERO)?;
shininess: (SHININESS^ NUMERO)?;

figura: caja
 	  | cono
 	  | cilindro
 	  | esfera
 	  ;
caja: NODO_BOX^ "{"! size "}"!;

cono: NODO_CONE^ "{"! height
					bottom_radius
					bottom //Si es TRUE, podemos no ponerlo
					side "}"! ; //Si es TRUE, podemos no ponerlo

cilindro: NODO_CYLINDER^ "{"! height
							radius
							bottom //Si es TRUE, podemos no ponerlo
							side //Si es TRUE, podemos no ponerlo
							top "}"!; //Si es TRUE, podemos no ponerlo

esfera: NODO_SPHERE^ "{"! radius "}"! ;


size : SIZE^ NUMERO NUMERO NUMERO;
height: HEIGHT^ NUMERO;
bottom_radius: BOTTOM_RADIUS^ NUMERO;
radius: RADIUS^ NUMERO;
bottom: (BOTTOM^ booleano)?;
side: (SIDE^ booleano)?;
top: (TOP^ booleano)?;
booleano : TRUE
         | FALSE
         ;

//NODO TRANSFORM//
nodo_transform: NODO_TRANSF^ "{"!traslacion
								 rotacion
								 escala 
								 children "}"!;
traslacion: (TRANSLATION^ NUMERO NUMERO NUMERO)?;
rotacion:(ROTATION^ NUMERO NUMERO NUMERO NUMERO)?;
escala:(SCALE^ NUMERO NUMERO NUMERO)?;

//NODO GROUP//
nodo_grupo: NODO_GROUP^ "{"! children_group "}"! ;
children_group: CHILDREN^ "["! lista_nodos "]"!;

children: CHILDREN^ "["!nodoDef"]"!;
lista_nodos: nodoDef (","! nodoDef)*;




