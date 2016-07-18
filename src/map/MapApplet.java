package map;

import java.applet.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;

import Control.Controller;
import easyIO.*;

public class MapApplet extends Applet implements Runnable, MouseMotionListener, MouseListener{

	Controller	Class_Controller;

	int			width, height;
	int			i						= 0;
	Thread		t						= null;
	boolean		threadSuspended;
	In			SquaresIn				= new In();
	In			RowsIn					= new In();
	int			numberOfLocations_int	= 100;

	int			Rows					= (int) Math.sqrt( numberOfLocations_int );
	int			Collumns				= numberOfLocations_int / Rows;
	int[]		SquareX					= new int[numberOfLocations_int + 1];
	int[]		SquareY					= new int[numberOfLocations_int + 1];

	String[]	terrainColor			= new String[6];
	int[]		terrainTemp				= new int[6];
	int[]		tempOnLocation			= new int[numberOfLocations_int + 1];
	String[]	squareColor				= new String[numberOfLocations_int + 1];

	int			curX, curY;
	int			x						= 0;
	int			X						= 0;
	int			Y						= 0;
	int			Z						= 0;
	int			SpawnTimer				= 0;
	int			CoordX					= 0;
	int			CoordY					= 0;

	int			xpos;
	int			ypos;
	int			rect1xco, rect1yco, rect1width, rect1height;
	boolean		mouseEntered;
	boolean		rect1Clicked;

	// private TerrainList TerrainSquares;
	int			SquareSize				= 20;
	int			NyX						= 0;
	int			NyY						= 0;
	int			colorpicker				= -1;
	int			intervals				= 6000 / numberOfLocations_int;

	int			locationToModify;
	int			NextSquare				= 4;
	/*
	 * int R = 0; int G = 0; int B = 0;
	 */
	double		R						= 0;
	double		G						= 0;
	double		B						= 0;
	double		TempNyX					= 0;
	double		TempNyY					= 0;

	Color		Color30					= new Color( 255, 0, 0 );
	Color		Color20					= new Color( 255, 255, 0 );
	Color		Color10					= new Color( 0, 255, 0 );
	Color		Color0					= new Color( 255, 255, 255 );
	int			GreenMin				= 0;
	int			GreenMax				= 20;
	int			RedMin					= 20;
	int			RedMax					= 30;
	int			BlueMax					= -5;
	int			BlueMin					= -10;
	int			WhiteMax				= 0;
	int			WhiteMin				= -5;
	int			RedDiff					= 0;
	int			GreenDiff				= 0;
	int			BlueDiff				= 0;
	double		ToMax					= 0;
	Color		Color_10				= new Color( 0, 0, 255 );

	Graphics	bufferGraphics;
	Image		offscreen;
	Dimension	dim;

	public MapApplet() {
		System.out.println( "MapApplet startup numberOfLocations_int=" + numberOfLocations_int + " rows=" + Rows );

		Class_Controller = new Controller( numberOfLocations_int );

		for ( int x = 0; x <= numberOfLocations_int; x++ ) {
			// System.out.println( "MapApplet startup x="+x );
			int TempTemp;
			colorpicker = colorpicker + 1;
			TempNyY = (double) x / (double) Rows;
			TempNyY = (TempNyY + 1.0);
			NyY = (int) (TempNyY - 1);
			NyX = (x - ((NyY - 1) * 10));
			NyY = NyY * SquareSize;
			NyX = NyX * SquareSize;
			NyX = NyX - (10 * SquareSize);

			if ( colorpicker > 5 ) {
				colorpicker = 0;
			}

			TempTemp = (-5) + (int) (Math.random() * 20);
			Object_Location ny2 = new Object_Location( x, NyX, NyY, "white", TempTemp );
			// System.out.println( "Location ID="+x+": Y=" + NyY + " X=" + NyX +
			// " SquareSize=" + SquareSize+" "+ny2 );
			// TerrainSquares.settInn( ny2 );
			// System.out.println(
			// "Applet kommanderer om å lagre location X="+(NyX/20)+" Y="+(NyY/20)
			// );
			Class_Controller.LagdNyLoc( ny2 );

			tempOnLocation[x] = TempTemp;
		}

		Class_Controller.Startup();
	}

	public void init() {
		width = getSize().width;
		height = getSize().height;
		setBackground( Color.white );

		addMouseMotionListener( this );
		// TerrainSquares = new TerrainList();

		SpawnTimer = 0;

		Random randomizer = new Random( 1L ); // remember longs are appended
												// with
												// L
		int randomInt = randomizer.nextInt();
		randomInt = Math.abs( randomInt );

		dim = getSize();
		addMouseMotionListener( this );
		setBackground( Color.black );
		offscreen = createImage( dim.width, dim.height );
		bufferGraphics = offscreen.getGraphics();

	}

	public void destroy() {
	}

	public void start() {
		if ( t == null ) {
			t = new Thread( this );
			threadSuspended = false;
			t.start();
		}
		else {
			if ( threadSuspended ) {
				threadSuspended = false;
				synchronized ( this ) {
					notify();
				}
			}
		}
	}

	public void stop() {
		threadSuspended = true;
	}

	public void run() {
		try {
			while ( true ) {
				++i;
				if ( i == numberOfLocations_int ) {
					i = 0;
				}
				if ( threadSuspended ) {
					synchronized ( this ) {
						while ( threadSuspended ) {
							wait();
						}
					}
				}
				SpawnTimer = SpawnTimer + 1;
				// finn gammmel temp
				// finn ut varmere\kaldere
				// plukk random rute og fix
				locationToModify = 0 + (int) (Math.random() * numberOfLocations_int);
				if ( locationToModify > numberOfLocations_int ) { // 401
					locationToModify = 0; // 0
				}
				Z = locationToModify; // location ID to change
				tempOnLocation[Z] = Class_Controller.GetLocTemp( Z );
				if ( Z <= numberOfLocations_int ) {
					if ( (Z - 11) < 0 ) {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[numberOfLocations_int - 11] / 40.0));
					}
					else {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[Z - 11] / 40.0));
					}
					if ( (Z - 10) < 0 ) {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[numberOfLocations_int - 10] / 20.0));
					}
					else {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[Z - 10] / 20.0));
					}
					if ( (Z - 9) < 0 ) {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[numberOfLocations_int - 9] / 40.0));
					}
					else {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[Z - 9] / 40.0));
					}

					if ( (Z - 1) < 0 ) {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[numberOfLocations_int - 1] / 20.0));
					}
					else {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[Z - 1] / 20.0));
					}
					if ( (Z + 1) > numberOfLocations_int ) {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[0 + 1] / 20.0));
					}
					else {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[Z + 1] / 20.0));
					}

					if ( (Z + 9) > numberOfLocations_int ) {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[0 + 9] / 40.0));
					}
					else {
						tempOnLocation[Z] = ((int) (tempOnLocation[Z] + (tempOnLocation[Z + 9] / 40.0)));
					}
					if ( (Z + 10) > numberOfLocations_int ) {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[0 + 10] / 20.0));
					}
					else {
						tempOnLocation[Z] = ((int) (tempOnLocation[Z] + (tempOnLocation[Z + 10] / 20.0)));
					}
					if ( (Z + 11) > numberOfLocations_int ) {
						tempOnLocation[Z] = (int) (tempOnLocation[Z] + (tempOnLocation[0 + 11] / 40.0));
					}
					else {
						tempOnLocation[Z] = ((int) (tempOnLocation[Z] + (tempOnLocation[Z + 11] / 40.0)));
					}

					// limit temperatures
					if ( tempOnLocation[Z] < -10 ) {
						tempOnLocation[Z] = -10;
					}
					if ( tempOnLocation[Z] > 30 ) {
						tempOnLocation[Z] = 30;
					}
					// random, local, slight temp change
					int TempChange = -1 + (int) (Math.random() * 3);
					// System.out.println(
					// "random, local, slight temp change="+TempChange);
					tempOnLocation[Z] = tempOnLocation[Z] + TempChange;
					// TerrainSquares.SendTemp(Z, temp[Z]);
					Class_Controller.SendTemp( Z, tempOnLocation[Z] );
				}
				repaint();
				t.sleep( intervals ); // interval given in milliseconds
			}
		}
		catch ( InterruptedException e ) {
		}
	}

	public void paint( Graphics g ) {

		bufferGraphics.clearRect( 0, 0, dim.width, dim.width );
		bufferGraphics.setColor( Color.red );
		// bufferGraphics.drawString("Bad Double-buffered",10,10);
		g.drawImage( offscreen, 0, 0, this );

		for ( int locationID = 0; locationID <= (numberOfLocations_int - 1); locationID++ ) {
			// System.out.println( "painting location #"+locationID);
			// SquareY[x] = TerrainSquares.GetCoordY(x);
			// SquareX[x] = TerrainSquares.GetCoordX(x);
			// temp[x] = TerrainSquares.GetTemp(x);
			SquareY[locationID] = Class_Controller.GetLocCoordY( locationID ) / 1;
			SquareX[locationID] = Class_Controller.GetLocCoordX( locationID ) / 1;
			tempOnLocation[locationID] = Class_Controller.GetLocTemp( locationID );
			// System.out.println(
			// "temp on location #"+locationID+": "+tempOnLocation[locationID]);

			if ( tempOnLocation[locationID] >= 10 ) {
				RedMax = 255;
				RedMin = 0;
				// GreenMax = 0;
				GreenMax = 127;
				GreenMin = 255;
				BlueMax = 0;
				BlueMin = 0;
				ToMax = (double) tempOnLocation[locationID] / 20.0;
				RedDiff = (RedMax - RedMin);
				RedDiff = RedMin + (int) (ToMax * RedDiff);
				GreenDiff = (GreenMax - GreenMin);
				GreenDiff = GreenMin + (int) (ToMax * GreenDiff);
				BlueDiff = (BlueMax - BlueMin);
				BlueDiff = BlueMin + (int) (ToMax * BlueDiff);

				R = RedDiff;
				G = GreenDiff;
				B = BlueDiff;
			}
			else

			if ( tempOnLocation[locationID] >= 0 ) {
				RedMax = 0;
				RedMin = 255;
				GreenMax = 255;
				GreenMin = 255;
				BlueMax = 0;
				BlueMin = 255;
				ToMax = (double) tempOnLocation[locationID] / 10.0;
				RedDiff = (RedMax - RedMin);
				RedDiff = RedMin + (int) (ToMax * RedDiff);
				GreenDiff = (GreenMax - GreenMin);
				GreenDiff = GreenMin + (int) (ToMax * GreenDiff);
				BlueDiff = (BlueMax - BlueMin);
				BlueDiff = BlueMin + (int) (ToMax * BlueDiff);

				R = RedDiff;
				G = GreenDiff;
				B = BlueDiff;
			}
			else

			if ( tempOnLocation[locationID] <= 0 ) {
				RedMax = 255;
				RedMin = 0;
				GreenMax = 255;
				GreenMin = 127;
				BlueMax = 255;
				BlueMin = 255;
				ToMax = (double) tempOnLocation[locationID] / -10.0;
				RedDiff = RedMax - RedMin;
				GreenDiff = GreenMax - GreenMin;
				BlueDiff = BlueMax - BlueMin;
				R = (int) (ToMax * RedDiff) + RedMin;
				G = (int) (ToMax * GreenDiff) + GreenMin;
				B = (int) (ToMax * BlueDiff) + BlueMin;
			}

			if ( R < 0 ) {
				R = 0;
			}
			else if ( R > 255 ) {
				R = 255;
			}
			if ( G < 0 ) {
				G = 0;
			}
			else if ( G > 255 ) {
				G = 255;
			}
			if ( B < 0 ) {
				B = 0;
			}
			else if ( B > 255 ) {
				B = 255;
			}

			int UnitsPresent = Class_Controller.GetUnitsLocated( locationID );
			if ( UnitsPresent > 0 ) {
				R = 0;
				G = 0;
				B = 0;
				// System.out.println(x+" UnitsPresent="+UnitsPresent+" color="+R+G+B);
			}
			// System.out.println("Temp=" + temp[x] + " ToMax=" + ToMax + " R="
			// + R + " G="+G+" B="+B);
			Color mixColor = new Color( (int) R, (int) G, (int) B );
			// g.setColor(mixColor);
			bufferGraphics.setColor( mixColor );
			// System.out.println(
			// "R on location #"+locationID+": "+R+" G="+G+" B="+B);
			// System.out.println(x+" "+mixColor);
			// g.fillRect(SquareX[x],SquareY[x],SquareSize,SquareSize);
			bufferGraphics.fillRect( SquareX[locationID], SquareY[locationID], SquareSize, SquareSize );
			// g.drawImage(offscreen,0,0,this);
			// System.out.println(
			// "location #"+locationID+": SquareX="+SquareX[locationID]+" SquareY="+SquareY[locationID]+" SquareSize="+SquareSize);
		}
		g.drawImage( offscreen, 0, 0, this );
	}

	public void mouseMoved( MouseEvent evt ) {
		curX = evt.getX();
		curY = evt.getY();
	}

	public void mouseDragged( MouseEvent evt ) {
	}

	public void mouseClicked( MouseEvent me ) {
		curX = me.getX();
		curY = me.getY();
		if ( curX > rect1xco && curX < rect1xco + rect1width && curY > rect1yco && curY < rect1yco + rect1height )
			rect1Clicked = true;
		else
			rect1Clicked = false;
		repaint();
	}

	public void mousePressed( MouseEvent me ) {
	}

	public void mouseReleased( MouseEvent me ) {
	}

	public void mouseEntered( MouseEvent me ) {
		mouseEntered = true;
		repaint();
	}

	public void mouseExited( MouseEvent me ) {
		mouseEntered = false;
		repaint();
	}
}