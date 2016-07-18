package Units;

import Objects.Object_DNA;
import Objects.Object_Feature;
import Objects.Object_Kromosom;
import Objects.Object_Species;

public class Object_Unit extends Object_Species{

	int				Age_Minutes		= 0;
	int				Age_Days		= 0;
	double			TelomereLeft	= 1;		// gjenstående dager å leve.
												// Opplevd stress fjerner biter
												// av telomeren.

	int				HP_Max			= 1;
	int				HP_Left			= 0;

	Object_Species	Species;
	Object_DNA		DNA;
	Object_Unit		Mother;

	Object_Feature	Teeth;
	Object_Feature	Eyes;
	Object_Feature	Skin;
	Object_Feature	FrontLegs;
	Object_Feature	Hindlegs;
	Object_Feature	Tail;

	boolean			Male;
	boolean			Pregnant		= false;
	double			Pregnancy		= 0;
	Object_Unit		Mate;

	public Object_Unit( Object_Species Parent, Object_DNA DNA, boolean Male ) {

		super( Parent.Teeth, Parent.Eyes, Parent.Skin, Parent.FrontLegs, Parent.Hindlegs, Parent.Tail );
		Species = Parent;
		Teeth = new Object_Feature( Parent.Teeth );
		Eyes = new Object_Feature( Parent.Eyes );
		Skin = new Object_Feature( Parent.Skin );
		FrontLegs = new Object_Feature( Parent.FrontLegs );
		Hindlegs = new Object_Feature( Parent.Hindlegs );
		Tail = new Object_Feature( Parent.Tail );
		this.DNA = DNA;
		this.Male = Male;
		// System.out.println("- " + new
		// Throwable().fillInStackTrace().getStackTrace()[0]+") <- " + new
		// Throwable().fillInStackTrace().getStackTrace()[1]+") <- " + new
		// Throwable().fillInStackTrace().getStackTrace()[2]+") Burde aldri havne her");
		DNACopying(); // DNA'et har et kromosom som har et gen som har et null
						// allel
		// System.out.println(
		// "This unit has "+Get_Teeth_Length()+"x long teeth" );

		ApplyDNA();
	}

	private void ApplyDNA() {
		TelomereLeft = DNA.Get_TelomereLength();
	}

	private void DNACopying() {
		// rng for mutasjoner når et nytt individ lages
		DNA.DNACopying(); // et kromosom har et gen som har et null allel
	}

	public double Get_Teeth_Length() {
		return Teeth.Teeth_Length;
	}

	public double Get_Teeth_Sharpness() {
		return Teeth.Teeth_Sharpness;
	}

	public void Command_SearchForFood() {
		System.out.println( "This unit is searching for food" );
	}

	public void Action() {
		Age_Minutes++;
		Age_Days = (int) (Age_Minutes / 60.0 / 24.0);

		// aging
		// TelomereLeft = TelomereLeft - (Age_Minutes / 60.0 / 24.0);
		;

		if ( Pregnant == true ) {
			Pregnancy = Pregnancy + 0.1;
		}
		// System.out.println( "This unit is "+Age_Minutes+" minutes old" );
		// System.out.println( "This unit is "+temp+" days old" );
		if ( Male == false ) {
			System.out.println( "This unit's Pregnancy=" + Pregnancy );
		}

	}

	public void Breed( Object_Unit Mate ) {
		if ( Pregnant == false ) {
			System.out.println( "This unit is pregnant" );
			Pregnant = true;
			Pregnancy = 0;
			this.Mate = Mate;
		}
	}

	public boolean Get_Male() {
		return Male;
	}

	public Object_Kromosom Get_Kromosom() {
		return DNA.Kromosom;
	}

	public boolean Get_Pregnant() {
		return Pregnant;
	}

	public double Get_Pregnancy() {
		return Pregnancy;
	}

	public void Birth() {
		Pregnancy = 0;
		Pregnant = false;
	}

	public Object_Species Get_Species() {
		return Species;
	}

	public Object_Unit Get_Mate() {
		return Mate;
	}

	public void Set_Mother( Object_Unit Mother ) {
		this.Mother = Mother;
	}

	public Object_Unit Get_Mother() {
		return Mother;
	}

	public boolean Get_Dead() {
		return(TelomereLeft <= 0.0);
	}

}
