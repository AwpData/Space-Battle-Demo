import ihs.apcs.spacebattle.BasicEnvironment;
import java.awt.Color;
import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;
import ihs.apcs.spacebattle.BasicSpaceship;
import ihs.apcs.spacebattle.RegistrationData;
import ihs.apcs.spacebattle.commands.ShipCommand;

public class RotatorShip extends BasicSpaceship {

	public static void main(String[] args) {
		// run method on TextClient with the IP Address and an instance of your Ship class.10.52.105.88
		ihs.apcs.spacebattle.TextClient.main(new String[] { "10.52.105.88", "RotatorShip" });
	}

	private int currentState = 0;

	@Override
	public ShipCommand getNextCommand(BasicEnvironment env) {
		ObjectStatus ship = env.getShipStatus();
		Point position = ship.getPosition();
		int orientation = ship.getOrientation();
		int toCenter = position.getAngleTo(center);
		switch (currentState) {
		case 0:
			currentState++;
			return new RotateCommand(toCenter - orientation);
		case 1:
			currentState = 0;
			// return new FireTorpedoCommand('F');
			return new ThrustCommand('B', 3.5, 1);
		default:
			return new BrakeCommand(0.1);
		}
	}

	private Point center;

	@Override
	public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
		center = new Point(worldWidth / 2, worldHeight / 2);
		return new RegistrationData("Trevor T", new Color(0, 0, 0), 10);

	}
}
