package ac.ucas.ir.document;

public class Field {
	private String name;
	private String value;
	private float boost;
	
	public Field(String name,String value){
		this.name = name;
		this.value = value;
		this.boost = 1;
	}
	
	public Field(String name,String value,float boost){
		this.name = name;
		this.value = value;
		this.boost = boost;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public float getBoost() {
		return boost;
	}
	
	public void setBoost(float boost) {
		this.boost = boost;
	}
	
	public void print(){
		System.out.println(name + ":" + value + " boost:" + boost);
	}
}