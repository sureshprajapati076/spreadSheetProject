package elements;

public class Reference extends Contents{
	
	private Cell cell;

	@Override
	public String value() {
		return cell.value();
	}

	@Override
	public String formula() {
		return cell.getCoordinates();//
	}

	@Override
	public float data() {
		
		return cell.data();
	}
	
	public Reference(Cell cell) {
		this.cell = cell;
	}

}
