package Objects;

public class Object_Kromosom {
	
	Object_Gen[] Gener = new Object_Gen[100];

	public Object_Kromosom( Object_Gen[] Gener ) {
		//System.out.println ( "Object_Kromosom " );
		//System.out.println("- " + new Throwable().fillInStackTrace().getStackTrace()[0]+") <- " + new Throwable().fillInStackTrace().getStackTrace()[1]+") <- " + new Throwable().fillInStackTrace().getStackTrace()[2]+") Burde aldri havne her");
		this.Gener = Gener;
		/*
		for ( int X = 0 ; X < Gener.length ; X++ ) {
			if ( Gener[X] != null ) {
				System.out.println ( "Object_Kromosom har "+X+" gener med allel: "+Gener[X].Allel );
			}
		}
		System.out.println("- " + new Throwable().fillInStackTrace().getStackTrace()[0]+") <- " + new Throwable().fillInStackTrace().getStackTrace()[1]+") <- " + new Throwable().fillInStackTrace().getStackTrace()[2]+") Burde aldri havne her");
		*/
	}

	public void DNACopying() {
		//System.out.println ( "Object_Kromosom sjekker for gener å mutere" );
		for ( int X = 0 ; X < Gener.length ; X++ ) {
			if ( Gener[X] != null ) {
				Gener[X].DNACopying(); //genet har et null allel
			}
		}
	}
}
