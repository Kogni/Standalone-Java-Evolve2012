package Control;

import map.MapApplet;
import map.Object_Location;
import map.control_Map;
import Objects.Object_Feature_Template;
import Objects.Object_Species;
import Units.Control_Units;
import Units.Object_Unit;
import Units.Thread_PlayUnit;

public class Controller{

	Control_Units	Class_Control_Units;
	Control_Species	Class_Control_Species;
	control_Map		Class_control_Map;
	TimeKeeper		Class_TimeKeeper;

	public Controller( int locations ) {
		System.out.println( "Controller creation" );

		Class_Control_Species = new Control_Species( this );
		Class_Control_Units = new Control_Units( this );
		Class_control_Map = new control_Map( this, locations );
		Class_TimeKeeper = new TimeKeeper( this, 100 );
	}

	public Controller() {
		// TODO Auto-generated constructor stub
	}

	public void Startup() {
		System.out.println( "Controller startup" );
		Class_Control_Species.Startup_SpeciesSetup();
		Class_Control_Units.Startup_UnitSetup( (Object_Species) Class_Control_Species.Get_StartupSpecies() );
	}

	public void TimeTick( TimeKeeper timeKeeper ) {
		// System.out.println ( "TimeTick" );
		Class_Control_Units.ProcessTick();
		Class_control_Map.ProcessTick();
	}

	public void PlaceNewUnit( Object_Unit newUnit ) {
		Class_control_Map.PlaceNewUnit( newUnit );
	}

	public void LagdNyLoc( Object_Location ny2 ) {
		Class_control_Map.LagdNyLoc( ny2 );
	}

	public int GetLocTemp( int z ) {
		return Class_control_Map.GetLocTemp( z );
	}

	public void SendTemp( int z, int i ) {
		Class_control_Map.SendTemp( z, i );
	}

	public int GetLocCoordY( int x ) {
		return Class_control_Map.GetLocCoordY( x );
	}

	public int GetLocCoordX( int x ) {
		return Class_control_Map.GetLocCoordX( x );
	}

	public int GetUnitsLocated( int x ) {
		return Class_control_Map.GetUnitsLocated( x );
	}

}
