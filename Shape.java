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
		ihs.apcs.spacebattle.TextClient.main(new String[] { "10.52.105.128", "RotatorShip" });
	}

	private int currentState = -1;

	@Override
	public ShipCommand getNextCommand(BasicEnvironment env) {
		ObjectStatus ship = env.getShipStatus();
		Point position = ship.getPosition();
		int orientation = ship.getOrientation();
		int toCenter = position.getAngleTo(center);

		switch (currentState) {
		case -1: // Hits one time only so that it creates a straight shape
			currentState++;
			return new RotateCommand(ship.getOrientation() * -1);
		case 0:
			currentState++;
			return new RotateCommand(180 + 45); // Star (Looks like a sun though)
		// return new RotateCommand(45); // Octogon
		// return new RotateCommand(90); //Square
		case 1:
			currentState++;
			return new DeployLaserBeaconCommand();
		case 2:
			currentState++;
			return new ThrustCommand('B', 4, 1);
		case 3:
			currentState++;
			return new BrakeCommand(0.000000001);
		default:
			currentState = 0;
			return new DeployLaserBeaconCommand();
		}
	}

	private Point center;

	@Override
	public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
		center = new Point(worldWidth / 2, worldHeight / 2);
		return new RegistrationData("Trevor T", new Color(26, 236, 41), 8);

	}
}
