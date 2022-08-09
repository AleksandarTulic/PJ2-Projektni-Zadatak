package elements;

public class GameTimeController extends Thread{
	private long seconds = 0;
	private boolean flag = true;
	
	@Override
	public void run() {
		do {
			if (!flag)
				break;
			
			try {
				Thread.sleep(1000);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			forms.Other.changeGameTime(++seconds);
		}while(true);
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
