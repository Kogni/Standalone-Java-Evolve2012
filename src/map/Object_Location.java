package map;

import Units.Object_Unit;

public class Object_Location{
	private int		ID, CoordX, CoordY, Temp;
	private String	Fargen;
	Object_Location	neste;

	Object_Unit[]	UnitsInLocation	= new Object_Unit[100];

	public Object_Location( int A, int B, int C, String D, int E ) {
		ID = A;
		CoordX = B;
		CoordY = C;
		Fargen = D;
		Temp = E;

		neste = null;
		// System.out.println( "Object_Location created. ID=" + ID + "CoordX="
		// +CoordX + " CoordY=" + CoordY );
	}

	public int GetID() {
		return ID;
	}

	public int GetCoordX() {
		return CoordX;
	}

	public int GetCoordY() {
		return CoordY;
	}

	public String GetFargen() {
		return Fargen;
	}

	public int GetTemp() {
		return Temp;
	}

	// ----------------------

	public void SetFargen( String F ) {
		Fargen = F;
	}

	public void SetTemp( int G ) {
		Temp = G;
	}

	public Object_Unit[] Get_UnitList() {
		return UnitsInLocation;
	}

	public boolean ReceiveUnit( Object_Unit unit ) {
		System.out.println( "Object_Location ReceiveUnit male=" + unit.Get_Male() + " X=" + this.CoordX + " Y=" + this.CoordY );
		int Teller = 0;
		for ( int x = 0; x < UnitsInLocation.length; x++ ) {
			Teller++;
			if ( UnitsInLocation[x] == null ) {
				UnitsInLocation[x] = unit;
				System.out.println( "Object_Location ReceiveUnit placement check: unit#" + x + " =" + UnitsInLocation[x] );
				return true;
			}
			else {
				System.out.println( "Object_Location ReceiveUnit unit#" + x + " =" + UnitsInLocation[x] );
			}
		}
		System.err.println( "Object_Location ReceiveUnit could not place unit! " + Teller );
		return false;
	}

	public boolean SendAwayUnit( Object_Unit unit ) {
		// Called when a unit is moving away from a tile. Update the tile by
		// removing the unit that is moving away
		System.out.println( "Object_Location SendAwayUnit from X=" + this.CoordX + " Y=" + this.CoordY );
		boolean Found = false;
		int Teller = 0;
		for ( int x = 0; x < UnitsInLocation.length; x++ ) {
			Teller++;
			// System.out.println( "Object_Location SendAwayUnit x=" + x +
			// " UnitsInLocation=" + UnitsInLocation[x] );
			if ( UnitsInLocation[x] != null ) {
				// System.out.println("Object_Location SendAwayUnit x="+x+" UnitsInLocation="+UnitsInLocation[x]);
				if ( UnitsInLocation[x].equals( unit ) ) {
					UnitsInLocation[x] = null;
					Found = true;
				}
				else if ( Found == true ) {
					UnitsInLocation[x - 1] = UnitsInLocation[x];
					UnitsInLocation[x] = null;
				}
			}
		}
		if ( Found == false ) {
			System.err.println( "! Object_Location SendAwayUnit could not locate the moving unit! " + Teller + " " + unit );
			System.err.println( "! " + new Throwable().fillInStackTrace().getStackTrace()[0] + ") <- " + new Throwable().fillInStackTrace().getStackTrace()[1] + ") <- " + new Throwable().fillInStackTrace().getStackTrace()[2] + ") Burde aldri havne her" );
		}
		return Found;
	}

	public boolean FindUnit( Object_Unit Unit ) {
		for ( int Y = 0; Y < UnitsInLocation.length; Y++ ) {
			if ( UnitsInLocation[Y] != null ) {
				// System.out.println
				// ("control_Map FindLocation Unit="+Unit+" UnitList[Y]="+UnitsInLocation[Y]);
				if ( UnitsInLocation[Y].equals( Unit ) ) {
					return true;
				}
			}
		}
		return false;
	}

	public void SetUnitList( Object_Unit[] unitList ) {
		UnitsInLocation = unitList;
	}
}
