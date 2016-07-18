package Units;

import Control.Controller;
import Objects.Object_Allele;
import Objects.Object_DNA;
import Objects.Object_Feature_Template;
import Objects.Object_Gen;
import Objects.Object_Kromosom;
import Objects.Object_Species;

public class Control_Units{

	Controller		Class_Controller;
	Object_Unit[]	UnitDatabase;
	Object_Gen[]	Startgener;
	int				Units	= 0;

	public Control_Units( Controller Class_Controller ) {
		// System.out.println ( "Control_Units " );
		this.Class_Controller = Class_Controller;
		UnitDatabase = new Object_Unit[99];

		LagStartgener();
	}

	private void LagStartgener() {
		Startgener = new Object_Gen[100];
		Object_Allele Startallel = new Object_Allele( 100, new Object_Feature_Template( 0, 0, 0 ) );
		// System.out.println (
		// "Control_Units LagStartgener "+Startgener+" Startallel="+Startallel
		// );
		Startgener[0] = new Object_Gen( Startallel );
		Startgener[1] = new Object_Gen( Startallel );
		// System.out.println (
		// "Control_Units LagStartgener Startgener="+Startgener+" gen0="+Startgener[0].Allel+" gen1="+Startgener[1].Allel
		// );
	}

	public void Startup_UnitSetup( Object_Species Species ) {
		System.out.println( "Control_Units Startup_UnitSetup Startgener=" + Startgener + " gen0=" + Startgener[0].Allel + " gen1=" + Startgener[1].Allel );
		Object_Kromosom ForelderKromosomA = new Object_Kromosom( Startgener );
		Object_Kromosom ForelderKromosomB = new Object_Kromosom( Startgener );
		Object_DNA DNAa = new Object_DNA( ForelderKromosomA, ForelderKromosomB );
		CreateUnit( Species, DNAa, true ); // DNA inneholder et null allel
		System.out.println( "- Control_Units lagde nettopp første unit" );
		Object_DNA DNAb = new Object_DNA( ForelderKromosomA, ForelderKromosomB );
		CreateUnit( Species, DNAb, false );
		System.out.println( "- Control_Units lagde nettopp andre unit" );
	}

	private void CreateUnit( Object_Species Species, Object_DNA NewDNA, boolean Male ) {
		System.out.println( "Control_Units CreateUnit" );

		Object_Unit NewUnit = new Object_Unit( Species, NewDNA, Male ); // DNA inneholder et null allel
		for ( int X = 0; X < UnitDatabase.length; X++ ) {
			if ( UnitDatabase[X] == null ) {
				UnitDatabase[X] = NewUnit;
				Class_Controller.PlaceNewUnit( NewUnit );
				Units++;
				return;
			}
		}
		System.err.println( "! " + new Throwable().fillInStackTrace().getStackTrace()[0] + ") <- " + new Throwable().fillInStackTrace().getStackTrace()[1] + ") Burde aldri havne her" );
	}

	public void ProcessTick() {

		for ( int X = 0; X < UnitDatabase.length; X++ ) {
			// System.out.println (
			// "Control_Units ProcessTick ID="+X+" "+UnitDatabase[X] );
			if ( UnitDatabase[X] != null ) {
				UnitDatabase[X].Action();
				// System.out.println (
				// "Control_Units ProcessTick Get_Dead()="+UnitDatabase[X].Get_Dead()
				// );
				if ( UnitDatabase[X].Get_Dead() == true ) {
					UnitDatabase[X] = UnitDatabase[X + 1];
					UnitDatabase[X + 1] = null;
				}
				else {
					ProcessUnit( UnitDatabase[X] );
				}
				if ( X >= 1 ) {
					if ( UnitDatabase[X - 1] == null ) {
						UnitDatabase[X - 1] = UnitDatabase[X];
						UnitDatabase[X] = null;
					}
				}
			}
		}

	}

	private void ProcessUnit( Object_Unit Unit ) {
		// System.out.println (
		// "Control_Units ProcessUnit Unit.Get_Pregnant()="+Unit.Get_Pregnant()
		// );
		if ( Unit.Get_Pregnant() == true ) {
			double Pregnancy = Unit.Get_Pregnancy();
			// System.out.println (
			// "Control_Units ProcessUnit Pregnancy="+Pregnancy );
			if ( Pregnancy >= 1.0 ) {
				Unit.Birth();
				Breed( Unit );
			}
		}
	}

	private void Breed( Object_Unit Mother ) {
		double Male = Math.random() * 1;
		Object_DNA NewDNA = new Object_DNA( Mother.Get_Kromosom(), Mother.Get_Mate().Get_Kromosom() );
		CreateUnit( Mother.Get_Species(), NewDNA, (Male >= 0.5) );
		System.out.println( "Control_Units Breed -A new unit is born- Male=" + (Male >= 0.5) + " Units total=" + Units );
	}

	public Object_Unit Get_StartunitA() {
		return UnitDatabase[0];
	}

	public Object_Unit Get_StartunitB() {
		return UnitDatabase[1];
	}

}
