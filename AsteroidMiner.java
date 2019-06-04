import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class RotatorShip extends BasicSpaceship {

	public static void main(String[] args) {
		// run method on TextClient with the IP Address and an instance of your Ship class.10.52.105.88
		ihs.apcs.spacebattle.TextClient.main(new String[] { "10.52.105.88", "RotatorShip", "2019" });
	}

	private int currentState = -1;
	double thrustnum = 2;

	@Override
	public ShipCommand getNextCommand(BasicEnvironment env) {

		ObjectStatus ship = env.getShipStatus();
		Point position = ship.getPosition();
		int orientation = ship.getOrientation();
		int toCenter = position.getAngleTo(center);

		double myAngle = env.getShipStatus().getOrientation();
		Point myPos = env.getShipStatus().getPosition();

		double angleTo = myPos.getAngleTo(ship.getPosition()) - myAngle;
		double distanceTo = myPos.getDistanceTo(ship.getPosition());

		currentState++;
		switch (currentState) {
		case 0:
			return new RadarCommand(5);
		case 1:
			RadarResults results = env.getRadar();
			if (results == null) {
				System.out.println("null");
				currentState = 0;
				return new RadarCommand(5);
			}
			if (results.getNumObjects() == 0) {
				System.out.println("No objects");
				currentState = 0;
				return new RadarCommand(5);
			}
			if (results.size() < 1) {
				System.out.println("Empty array");
				currentState = 0;
				return new RadarCommand(5);
			} else {
				System.out.println(results.getNumObjects());
				System.out.println(results);
				Point p = results.get(0).getPosition();
				int angle = position.getAngleTo(p);
				double speed = results.get(0).getSpeed();
				return new RotateCommand(angle);
			}
		case 2:
			if (Math.abs(angleTo % 360) < 60) {
				if (distanceTo < 75) {
					return new BrakeCommand(0);
				}
			}
		case 3:
			return new FireTorpedoCommand('F');
		default:
			currentState = -1;
			return new FireTorpedoCommand('F');

		}
	}

	private Point center;

	@Override
	public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
		center = new Point(worldWidth / 2, worldHeight / 2);
		return new RegistrationData("Trevor", new Color(26, 236, 41), 11);

	}
}

