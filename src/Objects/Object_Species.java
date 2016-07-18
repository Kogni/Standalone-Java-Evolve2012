package Objects;

public class Object_Species {
	
	public Object_Feature_Template Teeth;
	public Object_Feature_Template Eyes;
	public Object_Feature_Template Skin;
	public Object_Feature_Template FrontLegs;
	public Object_Feature_Template Hindlegs;
	public Object_Feature_Template Tail;

	public Object_Species( 
			Object_Feature_Template Teeth, 
			Object_Feature_Template Eyes, 
			Object_Feature_Template Skin,  
			Object_Feature_Template FrontLegs,
			Object_Feature_Template Hindlegs,
			Object_Feature_Template Tail
			) {
		this.Teeth = Teeth;
		this.Eyes = Eyes;
		this.Skin = Skin;
		this.FrontLegs = FrontLegs;
		this.Hindlegs = Hindlegs;
		this.Tail = Tail;
		
	}
	
}
