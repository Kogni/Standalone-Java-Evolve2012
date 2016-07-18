package Control;

import Objects.Object_Feature_Template;
import Objects.Object_Species;

public class Control_Species{

	Controller			Class_Controller;
	Object_Species[]	SpeciesDatabase;

	public Control_Species( Controller Class_Controller ) {
		System.out.println( "Control_Species" );
		this.Class_Controller = Class_Controller;
		SpeciesDatabase = new Object_Species[99];
	}

	public void Startup_SpeciesSetup() {
		System.out.println( "Control_Species Startup_SpeciesSetup" );

		Object_Feature_Template Teeth = new Object_Feature_Template( 0, 0, 0 );
		Object_Feature_Template Eyes = new Object_Feature_Template( 0, 0, 0 );
		Object_Feature_Template Skin = new Object_Feature_Template( 0, 0, 0 );
		Object_Feature_Template FrontLegs = new Object_Feature_Template( 0, 0, 0 );
		Object_Feature_Template Hindlegs = new Object_Feature_Template( 0, 0, 0 );
		Object_Feature_Template Tail = new Object_Feature_Template( 0, 0, 0 );
		Object_Species NewSpecies = new Object_Species( Teeth, Eyes, Skin, FrontLegs, Hindlegs, Tail );

		for ( int X = 0; X < SpeciesDatabase.length; X++ ) {
			if ( SpeciesDatabase[X] == null ) {
				SpeciesDatabase[X] = NewSpecies;
				return;
			}
		}
		System.err.println( "- " + new Throwable().fillInStackTrace().getStackTrace()[0] + ") <- " + new Throwable().fillInStackTrace().getStackTrace()[1] + ") Burde aldri havne her" );
	}

	public Object Get_StartupSpecies() {
		System.out.println( "Control_Species Get_StartupSpecies" );
		for ( int X = 0; X < SpeciesDatabase.length; X++ ) {
			if ( SpeciesDatabase[X] != null ) {
				return SpeciesDatabase[X];
			}
		}
		System.err.println( "- " + new Throwable().fillInStackTrace().getStackTrace()[0] + ") <- " + new Throwable().fillInStackTrace().getStackTrace()[1] + ") Burde aldri havne her" );
		return null;
	}

}
