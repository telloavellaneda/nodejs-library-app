package force.eai.mx.tools;

import force.eai.mx.util.Persona;

public class CreateRfcCurp {

	private String apellidoPaterno = "";
	private String apellidoMaterno = "";
	private String primerNombre = "";
	private String segundoNombre = "";
	private String fecha = "";
	private String sexo = "";
	private String estado = "";
	private boolean tipo = true;
	
	private String fullName = "";

	private String rfc = "";
	private String homoclave = "";
	private String curp = "";
	
	public CreateRfcCurp(Persona persona) {
		this.apellidoPaterno = validate(persona.getApellidoPaterno());
		this.apellidoMaterno = validate(persona.getApellidoMaterno());
		this.primerNombre = validate(persona.getNombre());
		this.segundoNombre = validate(persona.getSegundoNombre());
		this.fecha = persona.getFecha();
		this.sexo = validate(persona.getSexo());
		this.estado = validate(persona.getEstadoNacimiento());
		this.tipo = ( persona.getTipo().equals("2") ? false : true);
	}
	
	private String creaBaseRfc4() {
		String rfc4 = "";

		if ( tipo ) {
			String tmpApellidoPaterno = apellidoPaterno;
			String tmpApellidoMaterno = apellidoMaterno;
			String tmpPrimerNombre = primerNombre;
			String tmpSegundoNombre = segundoNombre;

			// REGLA 8: Reemplaza valores no utilizados
			for (int i = 0 ; i < reemplazar.length; i++ ) {
				tmpApellidoPaterno = tmpApellidoPaterno.replaceAll( "\\b"+reemplazar[i] +"\\b", "" ).trim();
				tmpApellidoMaterno = tmpApellidoMaterno.replaceAll( "\\b"+reemplazar[i] +"\\b", "" ).trim();
				tmpPrimerNombre = tmpPrimerNombre.replaceAll( "\\b"+reemplazar[i] +"\\b", "" ).trim();
				tmpSegundoNombre = tmpSegundoNombre.replaceAll( "\\b"+reemplazar[i] +"\\b", "" ).trim();
			}			

			// REGLA 6: MARIA Y JOSE COMO PRIMER NOMBRE
			if ( tmpPrimerNombre.equals("MARIA") || tmpPrimerNombre.equals("JOSE") ) {
				tmpPrimerNombre = ( !tmpSegundoNombre.equals("") ) ? tmpSegundoNombre : tmpPrimerNombre;
			}

			// REGLA 7: Persona física con un solo apellido
			if ( tmpApellidoPaterno.equals("") ) {
				tmpApellidoPaterno = tmpApellidoMaterno;
				tmpApellidoMaterno = "";
			}
					
			//System.out.println( tmpApellidoPaterno );
			//System.out.println( tmpApellidoMaterno );
			//System.out.println( tmpPrimerNombre );
			
			// REGLA 1: RFC normal
			if ( !tmpApellidoPaterno.equals("") && !tmpApellidoMaterno.equals("") && tmpApellidoPaterno.length() > 2 ) {
				rfc4 = tmpApellidoPaterno.substring(0, 1) + 
						obtenerPrimerVocal( tmpApellidoPaterno ) + 
						tmpApellidoMaterno.substring(0, 1) + 
						tmpPrimerNombre.substring(0, 1);
				
				fullName = apellidoPaterno + " " + apellidoMaterno + " " + primerNombre + " " + segundoNombre;

			// REGLA 2: Calculo de Fecha (easy peasy)
				
			// REGLA 3: Cuando la letra inicial de cualquiera de los apellidos o nombre sea compuesta, 
			//			únicamente se anotará la inicial de ésta.
			//			MUST en todos los casos -> SIEMPRE se usa la primer letra o de los apellidos o del nombre
				
			// REGLA 4: Apellido paterno de la persona física se componga de una o dos letras
			} else if ( !tmpApellidoPaterno.equals("") && !tmpApellidoMaterno.equals("") && tmpApellidoPaterno.length() <= 2 ) {
				rfc4 = tmpApellidoPaterno.substring(0, 1) + 
						tmpApellidoMaterno.substring(0, 1) + 
						tmpPrimerNombre.substring(0, 2);

				fullName = apellidoPaterno + " " + apellidoMaterno + " " + primerNombre + " " + segundoNombre;

			// REGLA 5: Apellido paterno o materno sean compuestos, se tomará para la clasificación 
			//			la primera palabra que corresponda a cualquiera de ellos.
			//			MUST en todos los casos -> SIEMPRE se usa la primer letra de los apellidos	
			
			// REGLA 6: Arriba
				
			// REGLA 7: (Complemento) Persona con un solo Apellido (Paterno o Materno)
			} else if ( !tmpApellidoPaterno.equals("") && tmpApellidoMaterno.equals("") ) {
				rfc4 = tmpApellidoPaterno.substring(0, 2) + 
						tmpPrimerNombre.substring(0, 2);				

				fullName = apellidoPaterno + " " + primerNombre + " " + segundoNombre;
			}

			// REGLA 9: Palabras inconvenientes
			for (int i = 0 ; i < palabrasInconvenientes.length; i++ ) {
				rfc4 = rfc4.replaceAll( "\\b" + palabrasInconvenientes[i][0] + "\\b", palabrasInconvenientes[i][1] ).trim();
			}
			
						
		} else {
			// PERSONAS MORALES

			String nombre2Use = primerNombre;
			String[] nombre2UseArray;
			//String accentedCharacters = "àèìòùÀÈÌÒÙáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãñõÃÑÕäëïöüÿÄËÏÖÜŸçÇßØøÅåÆæœ";
			
			nombre2Use = nombre2Use.replaceAll("\\.|\\,","");
			
			// REGLA 8: Reemplaza valores no utilizados
			for (int i = 0 ; i < reemplazarMoralesNombre.length; i++ ) {
				//System.out.println ( reemplazar[i] );
				nombre2Use = nombre2Use.replaceAll( "\\b" + reemplazarMoralesNombre[i] + "\\b", "" ).trim();
				nombre2Use = nombre2Use.replaceAll("  "," ");
			}
			//System.out.println( "Uno " + nombre2Use );
			
			// REGLA 8: Reemplaza valores no utilizados
			for (int i = 0 ; i < reemplazarMorales.length; i++ ) {
				//System.out.println ( reemplazar[i] );
				nombre2Use = nombre2Use.replaceAll( "\\b" + reemplazarMorales[i] + "\\b", "" ).trim();
				nombre2Use = nombre2Use.replaceAll("  "," ");
			}			

			//nombre2Use = nombre2Use.replaceAll("  "," ").replaceAll("  ", " ");
			nombre2UseArray = nombre2Use.split(" ");
			
			//System.out.println( nombre2Use );
			//System.out.println( nombre2UseArray.length );
			//for (int i = 0 ; i < nombre2UseArray.length; i++ ) {
			//	System.out.println ( nombre2UseArray[i] );
			//}

			// REGLA 11
			if ( nombre2UseArray.length > 2 && nombre2UseArray[1].length() > 1 && nombre2UseArray[2].length() > 1 ) {
				rfc4 = nombre2UseArray[0].substring(0, 1) +
						nombre2UseArray[1].substring(0, 1) +
						nombre2UseArray[2].substring(0, 1);
			
			} else if ( nombre2UseArray.length == 2 && nombre2UseArray[1].length() > 1 )  {
				rfc4 = nombre2UseArray[0].substring(0, 1) +
						nombre2UseArray[1].substring(0, 2);
			
			} else if ( nombre2UseArray.length == 1 && nombre2UseArray[0].length() >= 3 ) {
				rfc4 = nombre2UseArray[0].substring(0, 3);
				
			} else if ( nombre2UseArray.length == 1 && nombre2UseArray[0].length() == 2 ) {
				rfc4 = nombre2UseArray[0].substring(0, 2) + "X";

			} else if ( nombre2UseArray.length == 1 && nombre2UseArray[0].length() == 1 ) {
				rfc4 = nombre2UseArray[0].substring(0, 1) + "XX";
				
			}

			nombre2Use = primerNombre;
			nombre2Use = nombre2Use.replaceAll("\\.|\\,","");
			
			// REGLA 8: Reemplaza valores no utilizados
			for (int i = 0 ; i < reemplazarMoralesNombre.length; i++ ) {
				//System.out.println ( reemplazar[i] );
				nombre2Use = nombre2Use.replaceAll( "\\b" + reemplazarMoralesNombre[i] + "\\b", "" ).trim();
			}
			fullName = nombre2Use.replaceAll("  ", " ");
			//System.out.println( "FullName Depurado " + fullName );
			
		}
		return rfc4;
	}
	
	/*
	 * Obtiene primer vocal de un apellido paterno/materno
	 */
	private String obtenerPrimerVocal ( String valor ) {
	
		String vocal = "";
		int desdeCaracter = 1;
		
		for (int i = desdeCaracter ; i < valor.length(); i++ ) {
		    if((valor.toUpperCase().charAt(i) == 'A') || 
		        (valor.toUpperCase().charAt(i) == 'E')  ||
		        (valor.toUpperCase().charAt(i) == 'I') ||
		        (valor.toUpperCase().charAt(i) == 'O') ||
		        (valor.toUpperCase().charAt(i) == 'U')) {
		    	vocal = Character.toString( valor.charAt(i) );
		        //System.out.println(" valor " + valor);
		        break;
		    }
		}
		return vocal;
	}

	/*
	 * Obtiene primer vocal de un apellido paterno/materno
	 */
	private String obtenerPrimeraConsonante ( String valor ) {
	
		String consonante = "";
		int desdeCaracter = 1;
		
		for (int i = desdeCaracter ; i < valor.length(); i++ ) {
		    if ((valor.toUpperCase().charAt(i) != 'A') && 
		        (valor.toUpperCase().charAt(i) != 'E') &&
		        (valor.toUpperCase().charAt(i) != 'I') &&
		        (valor.toUpperCase().charAt(i) != 'O') &&
		        (valor.toUpperCase().charAt(i) != 'U')) {
		    	consonante = Character.toString( valor.charAt(i) );
		    	
		    	// Posicion 14-16: Criterio de Excepcion
		    	consonante = ( consonante.equals("Ñ") ) ? "X" : consonante; 
		        //System.out.println("consonante = " + consonante);
		        break;
		    }
		}
		
		// Posicion 14-16: Criterio de Excepcion
		consonante = ( consonante.equals("") ) ? "X" : consonante;
		return consonante;
	}

	
	private String obtenerFecha() {
		try {
			return fecha.split("/")[2].substring(2,4) + fecha.split("/")[1] + fecha.split("/")[0];
		} catch (Exception e) {
			return "";
		}
	}
	
	/*
	 * Calcula las dos primeras letras de la homoclave
	 */
	private String creaBaseHomoclave() {

		String dosEnDos = "";
		String h[] = new String[] { "", "" };
		int resultado = 0;
		
		for ( int i = 0; i < fullName.length(); i++) {
			String temp = fullName.substring(i, i+1);
			String valor = "";
			
			for ( int j = 0; j < valoresHomoclave.length; j++ ) {
				if ( temp.equals( valoresHomoclave[j][0] ) ) {
					valor = valoresHomoclave[j][1];
					break;
				}	
			}
			valor = ( i==0 ) ? "0" + valor : "" + valor; 
			dosEnDos += valor;
			//System.out.print( " valor " + temp + " " + valor );
		}

		for ( int i = 0; i < dosEnDos.length()-1; i++) {
			//System.out.println( dosEnDos.substring(i, i+2) + " " + dosEnDos.substring(i+1, i+2) + "<br>" );
			int tmpUno = new Integer(dosEnDos.substring(i, i+2)).intValue();
			int tmpDos = new Integer(dosEnDos.substring(i+1, i+2)).intValue();
			resultado += tmpUno * tmpDos;
		}

		String tmpResultado = resultado + "";
		//System.out.println(resultado);
		tmpResultado = tmpResultado.substring(tmpResultado.length()-3, tmpResultado.length());

		int divResultado = new Integer( tmpResultado ).intValue() / 34;
		int modResultado = new Integer( tmpResultado ).intValue() % 34;
		String[] cocientes = new String[] {divResultado + "", modResultado + ""};

		for ( int i = 0; i < cocientes.length; i++) {
			for ( int j = 0; j < valoresCocientes.length; j++ ) {
				if ( cocientes[i].equals( valoresCocientes[j][0]) ) {
					h[i] = valoresCocientes[j][1];
					break;
				}
			}
		}

		return h[0] + h[1];
	}
	
	/*
	 * Digito Verificador a partir del RFC a 11  o 12 caracteres
	 * Aquí es donde se utiliza el factor 11
	 */
	private String creaDigitoVerificador( String rfc ) {

		// Validacion de Personas Morales 
		// Si encuentro 11 digitos, concateno un espacio en blanco al inicio
		// de la cadena
		rfc = ( rfc.length() == 11 ) ? " " + rfc : rfc;
		rfc = rfc.toUpperCase();
		
		int factor = 13;
		int resultado = 0;
		int[] digitos = new int[ rfc.length() ];		
		
		for ( int i = 0; i < rfc.length(); i++) {
			String temp = rfc.substring(i, i+1);
			digitos[i] = 0;
			
			for ( int j = 0; j < digitoVerificador.length; j++ ) {
				if ( temp.equals( digitoVerificador[j][0] ) ) {
					digitos[i] = new Integer(digitoVerificador[j][1]).intValue();
					break;
				}	
			}
			resultado += digitos[i] * ( factor-- );
			//System.out.println( " valor " + digitos[i] + " x " + ( factor-- ) );
		}
		
		//System.out.println( resultado%11 );
		if ( resultado%11 == 0 )
			return (resultado%11) + "";
		else if ( (11 - resultado%11) == 10 )
			return "A";
		else
			return ( 11 - resultado%11 ) + "";
	}
		
	private String calculaSexo() {
		return ( sexo.equals("M") ) ? "H" : "M";
	}
	
	private String calculaEstado() {
		String tmpEstado = "";
		for( int i = 0; i < estados.length; i++ ) {
			if ( estado.equals( estados[i][0] ) ) {
				tmpEstado =  estados[i][1];
				break;
			}
		}
		
		// Para el caso de los extranjeros
		tmpEstado = ( tmpEstado.equals("") ) ? "NE" : tmpEstado;
		
		return tmpEstado;
	}
	
	/*
	 * Obtener CURP
	 */
	private String calcularCurp() {
		
		String tmpApellidoPaterno = apellidoPaterno;
		String tmpApellidoMaterno = apellidoMaterno;
		String tmpPrimerNombre = primerNombre;
		String tmpSegundoNombre = segundoNombre;
		String curp4 = "";
		String tmpCurp = "";

		// REGLA 8: Reemplaza valores no utilizados
		for (int i = 0 ; i < reemplazar.length; i++ ) {
			tmpApellidoPaterno = tmpApellidoPaterno.replaceAll( "\\b"+reemplazar[i] +"\\b", "" ).trim();
			tmpApellidoMaterno = tmpApellidoMaterno.replaceAll( "\\b"+reemplazar[i] +"\\b", "" ).trim();
			tmpPrimerNombre = tmpPrimerNombre.replaceAll( "\\b"+reemplazar[i] +"\\b", "" ).trim();
			tmpSegundoNombre = tmpSegundoNombre.replaceAll( "\\b"+reemplazar[i] +"\\b", "" ).trim();
		}
		
		// REGLA 6: MARIA Y JOSE COMO PRIMER NOMBRE
		if ( tmpPrimerNombre.equals("MARIA") || tmpPrimerNombre.equals("JOSE") ) {
			tmpPrimerNombre = ( !tmpSegundoNombre.equals("") ) ? tmpSegundoNombre : tmpPrimerNombre;
		}
		
		//System.out.println( tmpApellidoPaterno );
		//System.out.println( tmpApellidoMaterno );
		//System.out.println( tmpPrimerNombre );
		//System.out.println( tmpSegundoNombre );

		if ( !tmpApellidoPaterno.equals("") && !tmpApellidoMaterno.equals("") ) {
			String tmpValidaPaterno = ( tmpApellidoPaterno.substring(0, 1).equals("Ñ") ) ? "X" : tmpApellidoPaterno.substring(0, 1);
			String tmpValidaMaterno = ( tmpApellidoMaterno.substring(0, 1).equals("Ñ") ) ? "X" : tmpApellidoMaterno.substring(0, 1);
			String tmpValidaVocal = obtenerPrimerVocal( tmpApellidoPaterno );
			tmpValidaVocal = ( tmpValidaVocal.equals("") ) ? "X" : tmpValidaVocal;
			
			curp4 = tmpValidaPaterno + 
					tmpValidaVocal + 
					tmpValidaMaterno + 
					tmpPrimerNombre.substring(0, 1);

		} else if ( !tmpApellidoPaterno.equals("") && tmpApellidoMaterno.equals("") ) {
			String tmpValidaPaterno = ( tmpApellidoPaterno.substring(0, 1).equals("Ñ") ) ? "X" : tmpApellidoPaterno.substring(0, 1);
			String tmpValidaMaterno = "X";
			String tmpValidaVocal = obtenerPrimerVocal( tmpApellidoPaterno );
			tmpValidaVocal = ( tmpValidaVocal.equals("") ) ? "X" : tmpValidaVocal;
			
			curp4 = tmpValidaPaterno + 
					tmpValidaVocal + 
					tmpValidaMaterno + 
					tmpPrimerNombre.substring(0, 1);
		}

		// REGLA 9: Palabras inconvenientes
		for (int i = 0 ; i < palabrasInconvenientesCurp.length; i++ ) {
			curp4 = curp4.replaceAll( "\\b" + palabrasInconvenientesCurp[i][0] + "\\b", palabrasInconvenientesCurp[i][1] ).trim();
		}
		
		String primeraApellidoPaterno = obtenerPrimeraConsonante(tmpApellidoPaterno);
		String primeraApellidoMaterno = ( !tmpApellidoMaterno.equals("") ) ? obtenerPrimeraConsonante(tmpApellidoMaterno) : "X";
		String primeraNombre = obtenerPrimeraConsonante(tmpPrimerNombre);
		
		tmpCurp = curp4 
		+ this.obtenerFecha() 
		+ this.calculaSexo() 
		+ this.calculaEstado() 
		+ primeraApellidoPaterno + primeraApellidoMaterno + primeraNombre
		+ "0"
		+ "0";
		
		return tmpCurp;
	}
	
	/*
	 * Metodo de arranque para calcular RFC, Homoclave y CURP
	 */
	public void calcular() {
		try {
			rfc = this.creaBaseRfc4() + this.obtenerFecha();
			String tmpHomoclave = this.creaBaseHomoclave();
			homoclave = tmpHomoclave + this.creaDigitoVerificador(rfc + tmpHomoclave);
			
			curp = ( tipo ) ? this.calcularCurp() : "";
			
		} catch (Exception e) {
			e.printStackTrace();
			rfc = "";
			homoclave = "";
			curp = "";
		}
	}
	
	private String validate( String value ) {
		return ( value != null ) ? value.toUpperCase().trim().replaceAll("Á", "A").replaceAll("É","E").replaceAll("Í", "I").replaceAll("Ó", "O").replaceAll("Ú","U") : "";
	}
		
	public String getRfc() {
		return rfc;
	}
	public String getHomoclave() {
		return homoclave;
	}
	public String getCurp() {
		return curp;
	}
	
	public static void main(String a[]) {
		
		Persona persona = new Persona();
		persona.setApellidoPaterno("tello");
		persona.setApellidoMaterno("avellaneda");
		persona.setNombre("salvador");
		persona.setSegundoNombre("");
		persona.setFecha("08/11/1980");
		persona.setSexo("M");
		persona.setEstadoNacimiento("Distrito federal");
		persona.setTipo("1");
		
		CreateRfcCurp rfc = new CreateRfcCurp(persona);
		rfc.calcular();
		
		System.out.println( rfc.getRfc() + "-" + rfc.getHomoclave() );
		System.out.println( rfc.getCurp() ); 
	}
	
	final private String[][] palabrasInconvenientes = new String[][] {
		{"BUEI","BUEX"},
		{"CACA","CACX"},
		{"CAGA","CAGX"},
		{"CAKA","CAKX"},
		{"COGE","COGX"},
		{"COJE","COJX"},
		{"COJO","COJX"},
		{"FETO","FETX"},
		{"JOTO","JOTX"},
		{"KACO","KACX"},
		{"KAGO","KAGX"},
		{"KOJO","KOJX"},
		{"KULO","KULX"},
		{"MAMO","MAMX"},
		{"MEAS","MEAX"},
		{"MION","MIOX"},
		{"MULA","MULX"},
		{"PEDO","PEDX"},
		{"PUTA","PUTX"},
		{"QULO","QULX"},
		{"RUIN","RUIX"},
		{"BUEY","BUEX"},
		{"CACO","CACX"},
		{"CAGO","CAGX"},
		{"CAKO","CAKX"},
		{"COJA","COJX"},
		{"COJI","COJX"},
		{"CULO","CULX"},
		{"GUEY","GUEX"},
		{"KACA","KACX"},
		{"KAGA","KAGX"},
		{"KOGE","KOGX"},
		{"KAKA","KAKX"},
		{"MAME","MAMX"},
		{"MEAR","MEAX"},
		{"MEON","MEOX"},
		{"MOCO","MOCX"},
		{"PEDA","PEDX"},
		{"PENE","PENX"},
		{"PUTO","PUTX"},
		{"RATA","RATX"}
	};

	final private String[][] palabrasInconvenientesCurp = new String[][] {
		{"BACA","BXCA"},
		{"BAKA","BXKA"},
		{"BUEI","BXEI"},
		{"BUEY","BXEY"},
		{"CACA","CXCA"},
		{"CACO","CXCO"},
		{"CAGA","CXGA"},
		{"CAGO","CXGO"},
		{"CAKA","CXKA"},
		{"CAKO","CXKO"},
		{"COGE","CXGE"},
		{"COGI","CXGI"},
		{"COJA","CXJA"},
		{"COJE","CXJE"},
		{"COJI","CXJI"},
		{"COJO","CXJO"},
		{"COLA","CXLA"},
		{"CULO","CXLO"},
		{"FALO","FXLO"},
		{"FETO","FXTO"},
		{"GETA","GXTA"},
		{"GUEI","GXEI"},
		{"GUEY","GXEY"},
		{"JETA","JXTA"},
		{"JOTO","JXTO"},
		{"KACA","KXCA"},
		{"KACO","KXCO"},
		{"KAGA","KXGA"},
		{"KAGO","KXGO"},
		{"KAKA","KXKA"},
		{"KAKO","KXKO"},
		{"KOGE","KXGE"},
		{"KOGI","KXGI"},
		{"KOJA","KXJA"},
		{"KOJE","KXJE"},
		{"KOJI","KXJI"},
		{"KOJO","KXJO"},
		{"KOLA","KXLA"},
		{"KULO","KXLO"},
		{"LILO","LXLO"},
		{"LOCA","LXCA"},
		{"LOCO","LXCO"},
		{"LOKA","LXKA"},
		{"LOKO","LXKO"},
		{"MAME","MXME"},
		{"MAMO","MXMO"},
		{"MEAR","MXAR"},
		{"MEAS","MXAS"},
		{"MEON","MXON"},
		{"MIAR","MXAR"},
		{"MION","MXON"},
		{"MOCO","MXCO"},
		{"MOKO","MXKO"},
		{"MULA","MXLA"},
		{"MULO","MXLO"},
		{"NACA","NXCA"},
		{"NACO","NXCO"},
		{"PEDA","PXDA"},
		{"PEDO","PXDO"},
		{"PENE","PXNE"},
		{"PIPI","PXPI"},
		{"PITO","PXTO"},
		{"POPO","PXPO"},
		{"PUTA","PXTA"},
		{"PUTO","PXTO"},
		{"QULO","QXLO"},
		{"RATA","RXTA"},
		{"ROBA","RXBA"},
		{"ROBE","RXBE"},
		{"ROBO","RXBO"},
		{"RUIN","RXIN"},
		{"SENO","SXNO"},
		{"TETA","TXTA"},
		{"VACA","VXCA"},
		{"VAGA","VXGA"},
		{"VAGO","VXGO"},
		{"VAKA","VXKA"},
		{"VUEI","VXEI"},
		{"VUEY","VXEY"},
		{"WUEI","WXEI"},
		{"WUEY","WXEY"}		
	};

	final private String[][] valoresHomoclave = new String[][] { 
		{" ", "00"}, 
		{"0", "00"},
		{"1", "01"},
		{"2", "02"},
		{"3", "03"},
		{"4", "04"},
		{"5", "05"},
		{"6", "06"},
		{"7", "07"},
		{"8", "08"},
		{"9", "09"},
		{"&", "10"},
		{"A", "11"},
		{"B", "12"},
		{"C", "13"},
		{"D", "14"},
		{"E", "15"},
		{"F", "16"},
		{"G", "17"},
		{"H", "18"},
		{"I", "19"},
		{"J", "21"},
		{"K", "22"},
		{"L", "23"},
		{"M", "24"},
		{"N", "25"},
		{"O", "26"},
		{"P", "27"},
		{"Q", "28"},
		{"R", "29"},
		{"S", "32"},
		{"T", "33"},
		{"U", "34"},
		{"V", "35"},
		{"W", "36"},
		{"X", "37"},
		{"Y", "38"},
		{"Z", "39"},
		{"Ñ", "40"}
		};

	final private String[][] valoresCocientes = new String[][] { 
		{"0","1"},
		{"1","2"},
		{"2","3"},
		{"3","4"},
		{"4","5"},
		{"5","6"},
		{"6","7"},
		{"7","8"},
		{"8","9"},
		{"9","A"},
		{"10","B"},
		{"11","C"},
		{"12","D"},
		{"13","E"},
		{"14","F"},
		{"15","G"},
		{"16","H"},
		{"17","I"},
		{"18","J"},
		{"19","K"},
		{"20","L"},
		{"21","M"},
		{"22","N"},
		{"23","P"},
		{"24","Q"},
		{"25","R"},
		{"26","S"},
		{"27","T"},
		{"28","U"},
		{"29","V"},
		{"30","W"},
		{"31","X"},
		{"32","Y"},
		{"33","Z"},
		};
		
	final private String[][] digitoVerificador = new String[][] {
		{"0","00"},
		{"1","01"},
		{"2","02"},
		{"3","03"},
		{"4","04"},
		{"5","05"},
		{"6","06"},
		{"7","07"},
		{"8","08"},
		{"9","09"},
		{"A","10"},
		{"B","11"},
		{"C","12"},
		{"D","13"},
		{"E","14"},
		{"F","15"},
		{"G","16"},
		{"H","17"},
		{"I","18"},
		{"J","19"},
		{"K","20"},
		{"L","21"},
		{"M","22"},
		{"N","23"},
		{"&","24"},
		{"O","25"},
		{"P","26"},
		{"Q","27"},
		{"R","28"},
		{"S","29"},
		{"T","30"},
		{"U","31"},
		{"V","32"},
		{"W","33"},
		{"X","34"},
		{"Y","35"},
		{"Z","36"},
		{" ","37"},
		{"Ñ","38"}
	};
			
	final private String[] reemplazar = new String[] { 
		"DE LAS",
		"DE LOS",
		"DE LA",
		"SA",
		"SCL",
		"SC",
		"SCS",
		"SNC",
		"COMPANY",
		"COMPAÑIA",
		"SOCIEDAD",
		"COOPERATIVA",
		"CIA",
		"CO",
		"SOC",
		"COOP",
		"THE",
		"AND",
		"OF",
		"DAS",
		"DA",
		"DER",
		"DE",
		"DI",
		"DIE",
		"DD",
		"DEGLI",
		"DEL",
		"DALL",
		"DELLA",
		"EL",
		"LA",
		"LOS",
		"LAS",
		"PARA",
		"POR",
		"Y",
		"E",
		"MC",
		"MAC",
		"MI",
		"EN",
		"CON",
		"SUS",
		"VON",
		"VAN",
		"VANDEN",
		"VANDER",
		"A"
	};

	final private String[] reemplazarMorales = new String[] { 
		"COMPANY",
		"COMPAÑIA",
		"SOCIEDAD",
		"COOPERATIVA",
		"CIA",
		"CO",
		"SOC",
		"COOP",
		"THE",
		"AND",
		"OF",
		"DE LA",
		"DE LAS",
		"DE LOS",
		"DEL",
		"DA",
		"DE",
		"DI",
		"DEGLI",
		"DEL",
		"DALL",
		"DELLA",
		"EL",
		"LAS",
		"PARA",
		"POR",
		"E",
		"MC",
		"MI",
		"LA",
		"LOS",
		"Y",
		"EN",
		"CON",
		"SUS",
		"MAC",
		"VON",
		"VAN",
		"VANDEN",
		"VANDER",
		"A",
		"LA",
		"LAS",
		"MC",
		"LOS",
		"MI"
		};
	
	final private String[] reemplazarMoralesNombre = new String[] {
		"SA DE CV MI",
		"SA DE CV",
		"SC DE AP DE RL DE CV",
		"SC DE AP DE RL",
		"SC DE RL DE CV",
		"SC DE RL",
		"S DE RL DE CV",
		"S DE RL",
		"S EN C POR A",
		"S EN NC",
		"SRL CV MI",
		"SRL CV",
		"SRL MI",
		"SA MI",
		"S EN C",
		"A EN P",
		"AC",
		"SA",
		"SNC",
		"SCL",
		"SCS",
		"SC"
	};
	
	@SuppressWarnings("unused")
	final private String[][] caracterEspecialesValor =  new String[][] {
		{"@", "ARROBA"},
		{"´", "APOSTROFE"},
		{"%", "PORCIENTO"},
		{"#", "NUMERO"},
		{"!", "ADMIRACION"},
		{".", "PUNTO"},
		{"$", "PESOS"},
		{"”", "COMILLAS"},
		{"-", "GUION"},
		{"/", "DIAGONAL"},
		{"+", "SUMA"},
		{"(", "ABRE PARENTESIS"},
		{")", "CIERRA PARENTESIS"},
		{"'", "APOSTROFE"},
		{".", "PUNTO"}
	};
	
	final private String[][] estados = new String[][] {
		{"AGUASCALIENTES", "AS"},
		{"BAJA CALIFORNIA", "BC"},
		{"BAJA CALIFORNIA SUR", "BS"},
		{"CAMPECHE", "CC"},
		{"COAHUILA", "CL"},
		{"COLIMA", "CM"},
		{"CHIAPAS", "CS"},
		{"CHIHUAHUA", "CH"},
		{"DISTRITO FEDERAL", "DF"},
		{"DURANGO", "DG"},
		{"GUANAJUATO", "GT"},
		{"GUERRERO", "GR"},
		{"HIDALGO", "HG"},
		{"JALISCO", "JC"},
		{"MEXICO", "MC"},
		{"MICHOACAN", "MN"},
		{"MORELOS", "MS"},
		{"NAYARIT", "NT"},
		{"NUEVO LEON", "NL"},
		{"OAXACA", "OC"},
		{"PUEBLA", "PL"},
		{"QUERETARO", "QT"},
		{"QUINTANA ROO", "QR"},
		{"SAN LUIS POTOSI", "SP"},
		{"SINALOA", "SL"},
		{"SONORA", "SR"},
		{"TABASCO", "TC"},
		{"TAMAULIPAS", "TS"},
		{"TLAXCALA", "TL"},
		{"VERACRUZ", "VZ"},
		{"YUCATAN", "YN"},
		{"ZACATECAS", "ZS"},
	};
}