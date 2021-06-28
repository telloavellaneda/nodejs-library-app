package force.eai.mx.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken; 

public class TweetApp {
	
	public Properties getPropValues() {
		Properties prop = new Properties();
		
		try {
			String propFileName = "resources/twitter.properties";
			String basePath = Properties.class.getResource("/").getPath();

			//System.out.println(basePath + propFileName );
			prop.load( new FileInputStream( basePath + propFileName ) );
   			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;		
	}
	
	public static void main (String a[]) throws TwitterException, IOException {
		
		//String[] mensaje = {"- Para leer el mensaje completo, ve a mi Timeline :) -","Yo","les","quiero","decir","en","x","tweets","que","esto","es","muy","divertido [PUNTO]",
		//					"Parece","BOT","pero","no [COMA]","se","llama", "creatividad [PUNTO] {y un poco de ocio}", "* se rie como bruja de Disney *",
		//					"Por","su","atención [COMA]","gracias","PUNTO (final)"};

		//String[] mensaje = {"Esto", "es", "por", "estarme", "trolleando", "cuando", "hago", "cosas", "creativas", "¡Ché!"};
		
		/*		String[] mensaje = {
			//"1020352964 SEMOVI CDMX : Buen día @lourdesvalle555 compartimos los requisitos para el aviso de venta de vehículo de uso partisASD ASDASD DSJ",
			"Tello Blogger: On / \"Y de pronto empiezas a revisar\"",
			"¿Les ha pasado que cuando alguna cuenta tuitea un link con algo interesante, al darle click no abre \"rápido\" y mejor cierran la ventana?",
			"Esto sobre todo cuando revisas tu TL desde un smartphone. Yo me pregunto si las empresas validan con cada proveedor de internet, el número ",
			"de hits no resueltos o por el router, o porque el servidor donde está alojado el sitio no respondió antes de que el usuario cerrara su",
			"navegador. Yo creo que no pasa. Desde ahora, te voy a tuitear así. Secuencial, y con altas probabilidades de que no veas un tuit de",
			"una cuenta distinta a la mía entre el mensaje que te quiero dar. Ojalá que Twitter no se ponga loco por esto. Digamos que los blogs me",
			"parecen de 1975 (¡ja!), y deben haber otros mecanismos para impactar a la gente de forma rápida. Porque con todo y las redes 4G, los", 
			"\"Wordpress'es\", los \"Joomla's\", y las compañías con Hostings más lentos que Murillo Karam no ayudan mucho.",
			"Tello Blogger: Off / " */
		
		String[] mensaje = {
		//	"01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
			"Este es un tuit desde mi generador autómatico",
			"Si, ya se, es mamalón, pero a poco no es divertido.",
			"Espero todavía funcione"
		};		
		
		TweetApp obj = new TweetApp();
		Properties objeto = obj.getPropValues();

		TwitterFactory factory = new TwitterFactory();
	    AccessToken accessToken = new AccessToken( objeto.getProperty("oauth.accessToken") , objeto.getProperty("oauth.accessTokenSecret")  );
	    Twitter twitter = factory.getInstance();
	    twitter.setOAuthConsumer( objeto.getProperty("oauth.consumerKey") , objeto.getProperty("oauth.consumerSecret") );
	    twitter.setOAuthAccessToken(accessToken);
	
	    /*List<Status> statuses = twitter.getHomeTimeline();
	    for (Status status : statuses) {
	        System.out.println(status.getUser().getId() + " " + status.getUser().getName() + " : " + status.getText());
	    }*/	    
	    	    
	    
		for (int i = 0; i < mensaje.length; i++) {
			//System.out.println( (mensaje.length-1) - i );

			String tmpMensaje = "";
			
			if ( mensaje[ (mensaje.length-1) - i ].equals("x") ) 
				tmpMensaje = "" + mensaje.length;
			else 
				tmpMensaje = mensaje[ (mensaje.length-1) - i ];

			//System.out.println( "Tweeting: " + tmpMensaje + " " + tmpMensaje.length() );
			Status status = twitter.updateStatus( tmpMensaje );
		    //DirectMessage message = twitter.sendDirectMessage(43634624, tmpMensaje);			 
			System.out.println(status);

			
		  try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
}
