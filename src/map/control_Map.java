package map;

import Control.Controller;
import Units.Object_Unit;

public class control_Map{

	Controller			Class_Controller;
	Control_Climate		Class_Control_Climate;
	int					locations_int;
	// Object_Unit[][] Locations = new Object_Unit[100][100];
	Object_Location[]	location_obj	= new Object_Location[100];

	public control_Map( Controller Class_Controller, int locations2 ) {
		System.out.println( "control_Map " );
		this.Class_Controller = Class_Controller;
		locations_int = locations2;
		location_obj = new Object_Location[locations_int];
		Class_Control_Climate = new Control_Climate( this );
	}

	public void ProcessTick() {
		// System.out.println ( "control_Map ProcessTick" );
		for ( int X = 0; X < locations_int; X++ ) {
			if ( location_obj[X] != null ) {
				// System.out.println( "control_Map ProcessTick Location # " + X
				// + " " + location_obj[X] );
				ProcessLocation( location_obj[X] );
			}
		}
	}

	private void ProcessLocation( Object_Location Location ) {
		if ( Location != null ) {
			Object_Unit[] UnitList = Location.Get_UnitList();
			for ( int Y = 0; Y < UnitList.length; Y++ ) {
				if ( UnitList[Y] != null ) {
					System.out.println( "control_Map ProcessLocation Location # " + Location.GetID() + " unit#" + Y );
					if ( UnitList[Y].Get_Dead() == true ) {
						UnitList[Y] = UnitList[Y + 1];
						UnitList[Y + 1] = null;
					}
					else {
						ProcessUnit( UnitList[Y], Location );
					}
				}
			}
			Location.SetUnitList( UnitList );
		}
	}

	private void ProcessUnit( Object_Unit Unit, Object_Location Location ) {
		System.out.println( "control_Map ProcessUnit Unit=" + Unit );
		boolean Processed = SearchNeighbors( Unit, Location );
		if ( Processed == false ) {
			Movement( Unit, Location );
		}
	}

	private boolean SearchNeighbors( Object_Unit Interactor, Object_Location Location ) {
		System.out.println( "control_Map SearchNeighbors Interactor=" + Interactor + " location=" + Location.GetID() );
		Object_Unit[] UnitList = Location.Get_UnitList();
		for ( int Y = 0; Y < UnitList.length; Y++ ) {
			if ( UnitList[Y] != null ) {
				if ( UnitList[Y].equals( Interactor ) ) {

				}
				else {
					return InteractUnits( Interactor, UnitList[Y] );
				}
			}
		}
		return false;
	}

	private boolean InteractUnits( Object_Unit Interactor, Object_Unit Interacted ) {
		System.out.println( "control_Map InteractUnits Interactor=" + Interactor + " Interactor.Get_Male()=" + Interactor.Get_Male() );
		if ( Interactor.Get_Male() == true ) {
			if ( Interacted.Get_Male() == true ) { // fight?
				return true;
			}
			else { // breed?
				Interacted.Breed( Interactor );
				return true;
			}
		}
		else { // female meets female. do nothing

		}
		return false;
	}

	private void Movement( Object_Unit Unit, Object_Location oldLocation ) { // Location=der unit befinner seg atm
		int DirectionX = RandomInt( -1, 1 - (-1) );
		int DirectionY = RandomInt( -1, 1 - (-1) );
		// decide location to move to
		Object_Location NewLoc = FindLocationByCoords( (DirectionX + oldLocation.GetCoordX()), (DirectionY + oldLocation.GetCoordY()) );
		while ( NewLoc == null ) {
			DirectionX = RandomInt( -10, 10 - (-10) );
			DirectionY = RandomInt( -10, 10 - (-10) );
			NewLoc = FindLocationByCoords( (DirectionX + oldLocation.GetCoordX()), (DirectionY + oldLocation.GetCoordY()) );
		}
		// System.out.println
		// ("control_Map DirectionX="+DirectionX+" DirectionY="+DirectionY+" (DirectionX+Location.GetCoordX())="+(DirectionX+Location.GetCoordX())+" (DirectionY+Location.GetCoordY())="+(DirectionY+Location.GetCoordY()));
		// move unit
		oldLocation.SendAwayUnit( Unit );
		NewLoc.ReceiveUnit( Unit );
		// check that unit has successfully moved
		Object_Location Check = FindLocation( Unit );
		if ( Check == null ) {
			System.err.println( "! " + new Throwable().fillInStackTrace().getStackTrace()[0] + ") <- " + new Throwable().fillInStackTrace().getStackTrace()[1] + ") <- " + new Throwable().fillInStackTrace().getStackTrace()[2] + ") Burde aldri havne her" );
		}
	}

	private int RandomInt( int Min, int Max ) {
		double RngD = Math.random();
		double RngD2 = RngD * Max;
		int Rng = Round( RngD2 );
		int Rng2 = (Rng + Min);
		int Rng3 = Round( Rng2 / 10.0 );
		// System.out.println (
		// "control_Map RandomInt Rng="+RngD+" "+RngD2+" "+Rng+" "+Rng2+" "+Rng3
		// );
		return Rng3;
	}

	private int Round( double X ) {
		int Multi = 0;
		if ( X > 1 ) {
			Multi = (int) (X / 1);
		}
		else if ( X < -1 ) {
			Multi = (int) (X / 1);
		}
		// System.out.println ( "control_Map Round X="+X+" Multi="+Multi );
		if ( X > 0.5 ) {
			return Multi + 1;
		}
		else if ( X < -0.5 ) {
			return Multi + (-1);
		}
		else {
			return Multi;
		}
	}

	private Object_Location FindLocationByCoords( int X, int Y ) {
		// System.out.println(
		// "control_Map FindLocationByCoords Searching for X=" + X + " Y=" + Y
		// );
		for ( int teller = 0; teller < locations_int; teller++ ) {
			if ( location_obj[teller] != null ) {
				// System.out.println (
				// "control_Map FindLocationByCoords loc ID "+teller+"="+location_obj[teller]+" y="+location_obj[teller].GetCoordY()+" x="+location_obj[teller].GetCoordX());
				if ( location_obj[teller].GetCoordX() == X ) {
					if ( location_obj[teller].GetCoordY() == Y ) {
						return location_obj[teller];
					}
				}
			}
		}
		System.err.println( "! control_Map FindLocationByCoords could not find location X=" + X + " Y=" + Y );
		return null;
	}

	public void PlaceNewUnit( Object_Unit newUnit ) {
		System.out.println( "control_Map PlaceNewUnit " + newUnit );
		Object_Unit Mother = newUnit.Get_Mother();
		if ( Mother != null ) {
			Movement( newUnit, FindLocation( Mother ) );
		}
		else {
			Object_Location location = FindLocationByCoords( 20, 20 );
			if ( location != null ) {
				// Movement( newUnit, location );
				location.ReceiveUnit( newUnit );
			}
			else {
				System.err.println( "! control_Map PlaceNewUnit could not find location " );
			}

		}
	}

	private Object_Location FindLocation( Object_Unit Unit ) {
		for ( int X = 0; X < location_obj.length; X++ ) {
			// System.out.println (
			// "control_Map FindLocation Unit="+Unit+" X="+LocationUnits[X].GetCoordX()+" Y="+LocationUnits[X].GetCoordY()+" X="+X
			// );
			boolean Found = location_obj[X].FindUnit( Unit );
			if ( Found == true ) {
				return location_obj[X];
			}
		}
		return null;
	}

	public void LagdNyLoc( Object_Location ny2 ) {
		for ( int X = 0; X < location_obj.length; X++ ) {
			if ( location_obj[X] == null ) {
				location_obj[X] = ny2;
				// System.out.println(
				// "Control_Map LagdNyLoc X="+LocationUnits[X].GetCoordX()+" Y="+LocationUnits[X].GetCoordY()
				// );
				break;
			}
		}
	}

	public int GetLocTemp( int z ) {
		for ( int X = 0; X < location_obj.length; X++ ) {
			if ( location_obj[X] != null ) {
				if ( location_obj[X].GetID() == z ) {
					return location_obj[X].GetTemp();
				}
			}
			else {
				return 0;
			}
		}
		return 0;
	}

	public void SendTemp( int z, int i ) {
		for ( int X = 0; X < location_obj.length; X++ ) {
			if ( location_obj[X] != null ) {
				if ( location_obj[X].GetID() == z ) {
					location_obj[X].SetTemp( i );
				}
			}
			else {
				break;
			}
		}
	}

	public int GetLocCoordY( int x ) {
		for ( int X = 0; X < location_obj.length; X++ ) {
			if ( location_obj[X] != null ) {
				if ( location_obj[X].GetID() == x ) {
					return location_obj[X].GetCoordY();
				}
			}
			else {
				return 0;
			}
		}
		return 0;
	}

	public int GetLocCoordX( int x ) {
		for ( int X = 0; X < location_obj.length; X++ ) {
			if ( location_obj[X] != null ) {
				if ( location_obj[X].GetID() == x ) {
					return location_obj[X].GetCoordX();
				}
			}
			else {
				return 0;
			}
		}
		return 0;
	}

	public int GetUnitsLocated( int x ) {
		int Teller = 0;
		Object_Unit[] UnitList = location_obj[x].Get_UnitList();
		for ( int Y = 0; Y < UnitList.length; Y++ ) {
			if ( UnitList[Y] != null ) {
				Teller++;
			}
		}
		return 0;
	}
}
