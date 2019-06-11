import java.awt.Color;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class RotatorShip extends BasicSpaceship {
	static enum States {
		NEW_RADAR, GET_RADAR_RESULTS, FIRE_TORPEDO;
	}

	public static void main(String[] args) {
		// run method on TextClient with the IP Address and an instance of your Ship class.10.52.105.88
		ihs.apcs.spacebattle.TextClient.main(new String[] { "10.52.105.88", "RotatorShip", "2019" });
	}

	double thrustnum = 2;
	States state = States.NEW_RADAR;

	@Override
	public ShipCommand getNextCommand(BasicEnvironment env) {

		ObjectStatus ship = env.getShipStatus();
		Point position = ship.getPosition();
		int orientation = ship.getOrientation();
		switch (state) {
		case NEW_RADAR:
			state = States.GET_RADAR_RESULTS;
			return new RadarCommand(4);
		case GET_RADAR_RESULTS:
			RadarResults results = env.getRadar();
			if (results == null) {
				System.out.println("null");
				state = States.NEW_RADAR;
				return new RadarCommand(4);
			}
			if (results.getNumObjects() == 0) {
				System.out.println("No objects found");
				state = States.NEW_RADAR;
				return new RadarCommand(4);
			}
			if (results.size() < 1) {
				System.out.println("Empty array");
				state = States.NEW_RADAR;
				return new RadarCommand(4);
			} else {
				state = States.FIRE_TORPEDO;
				System.out.println(results.getNumObjects() + " objects nearby");
				// System.out.println(results); // Full information of nearby objects
				Point p = results.get(0).getPosition(); // position of object to fire at
				String name = results.get(0).getType();
				System.out.println("Attemping to fire at " + name + " at " + p);
				return new RotateCommand(position.getAngleTo(p) - orientation);
			}
		case FIRE_TORPEDO:
			state = States.NEW_RADAR;
			return new FireTorpedoCommand('F');
		default:
			throw new IllegalStateException("Unknwon State");
		}
	}

	private Point center;

	@Override
	public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
		center = new Point(worldWidth / 2, worldHeight / 2);
		return new RegistrationData("Trevor", new Color(26, 236, 41), 11);

	}
}
