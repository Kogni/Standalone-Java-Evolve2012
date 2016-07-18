package Objects;

public class Object_DNA {
	
	public Object_Kromosom Kromosom;
	
	public Object_DNA( Object_Kromosom ForelderKromosomA, Object_Kromosom ForelderKromosomB ) {
		//System.out.println ( "Object_DNA " );
		
		MergeChromosomes( ForelderKromosomA, ForelderKromosomB );
		/*
		double Mutation = Math.random() * 100;
		if ( Mutation <= 1 ) {
			for ( int X = 0 ; X < Kromosomer.length ; X++ ) {
				if ( Kromosomer[X] == null ) {
					Kromosomer[X] = new Object_Kromosom( null );
					break;
				}
			}
		}*/
	}
	
	private void MergeChromosomes( Object_Kromosom ForelderKromosomA, Object_Kromosom ForelderKromosomB ) {
		//System.out.println ( "Object_DNA MergeChromosomes" );
		//System.out.println("- " + new Throwable().fillInStackTrace().getStackTrace()[0]+") <- " + new Throwable().fillInStackTrace().getStackTrace()[1]+") <- " + new Throwable().fillInStackTrace().getStackTrace()[2]+") Burde aldri havne her");
		
		Object_Gen[] Gener = new Object_Gen[100];
		int Teller = 0;
		for ( int X = 0 ; X < ForelderKromosomA.Gener.length ; X++ ) {
			if ( ForelderKromosomA.Gener[X] != null ) {
				//System.out.println ( "Object_DNA MergeChromosomes legger til et genA med allel="+ForelderKromosomA.Gener[X].Allel );
				Gener[X] = VurderNyttGen( ForelderKromosomA.Gener[X], ForelderKromosomB.Gener[X] ); //gen har null allel
				Teller ++;
			} else if ( ForelderKromosomB.Gener[X] != null ) {
				//System.out.println ( "Object_DNA MergeChromosomes legger til et genB med allel="+ForelderKromosomB.Gener[X].Allel );
				Gener[X] = VurderNyttGen( ForelderKromosomA.Gener[X], ForelderKromosomB.Gener[X] ); //gen har null allel
				Teller ++;
			}
		}
		//System.out.println ( "Object_DNA MergeChromosomes totale gener: "+Teller );
		Kromosom = new Object_Kromosom( Gener );
	}
	
	private Object_Gen VurderNyttGen( Object_Gen ForelderA, Object_Gen ForelderB ) {
		//System.out.println ( "Object_DNA VurderNyttGen allelA="+ForelderA.Allel+" allelb="+ForelderB.Allel );
		//System.out.println("- " + new Throwable().fillInStackTrace().getStackTrace()[0]+") <- " + new Throwable().fillInStackTrace().getStackTrace()[1]+") <- " + new Throwable().fillInStackTrace().getStackTrace()[2]+") Burde aldri havne her");
		
		int[] Vekt = new int[2];
		Vekt[0] = 0;
		Vekt[1] = 0;
		if ( ForelderA.Allel != null ) {
			Vekt[0] = ForelderA.Allel.DominantVekt;
		}
		if ( ForelderB.Allel != null ) {
			Vekt[1] = ForelderB.Allel.DominantVekt;
		}
		if ( Vekt[0] >= Vekt[1] ) {
			return new Object_Gen( ForelderA.Allel );
		} else {
			return new Object_Gen( ForelderB.Allel );
		}
	}

	public void DNACopying() {
		Kromosom.DNACopying(); //et gen har et null allel
	}

	public double Get_TelomereLength() {
		double TelomereLength = 0;
		for ( int X = 0 ; X < Kromosom.Gener.length ; X++ ) {
			if ( Kromosom.Gener[X] != null ) {
				TelomereLength = TelomereLength + Kromosom.Gener[X].Allel.TelomereLength;
			}
		}
		return TelomereLength;
	}

}
