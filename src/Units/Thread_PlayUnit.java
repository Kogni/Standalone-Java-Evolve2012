package Units;

import Control.Controller;

public class Thread_PlayUnit extends Thread {
	
	Controller Class_Controller;
	
	Object_Unit UnitResponsible;
	
	public Thread_PlayUnit( Controller Class_Controller, Object_Unit UnitResponsible ) {
		System.out.println ( "Thread_PlayUnit " );
		
		this.Class_Controller = Class_Controller;
		this.UnitResponsible = UnitResponsible;
		
	}

	public void run() {
		System.out.println ( "Thread_PlayUnit run" );
		UnitResponsible.Command_SearchForFood();
		
	}
}