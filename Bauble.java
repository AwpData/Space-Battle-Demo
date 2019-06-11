import java.awt.Color;
import java.util.*;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;
import ihs.apcs.spacebattle.games.*;

public class RotatorShip extends BasicSpaceship {
	static enum States {
		NEW_RADAR, GET_RADAR_RESULTS, THRUST, BRAKE, ROTATE;
	}

	public static void main(String[] args) {
		// run method on TextClient with the IP Address and an instance of your Ship class.10.52.105.88
		ihs.apcs.spacebattle.TextClient.main(new String[] { "10.52.105.88", "RotatorShip", "2020" });
	}

	double thrusttime = 2;
	States state = States.NEW_RADAR;

	@Override
	public ShipCommand getNextCommand(BasicEnvironment env) {
		ObjectStatus ship = env.getShipStatus();
		Point position = ship.getPosition();
		BaubleHuntGameInfo gameInfo = (BaubleHuntGameInfo) env.getGameInfo();
		Point outpost = gameInfo.getObjectiveLocation();
		switch (state) {
		case NEW_RADAR:
			state = States.GET_RADAR_RESULTS;
			return new RadarCommand(5);
		case GET_RADAR_RESULTS:
			System.out.println("Your ship has " + gameInfo.getNumBaublesCarried() + " baubles");
			RadarResults results = env.getRadar();
			state = States.THRUST;
			if (gameInfo.getNumBaublesCarried() == 5) {
				System.out.println("Going to outpost");
				return new RotateCommand(ship.getPosition().getAngleTo(outpost) - ship.getOrientation());
			}
			return new RotateCommand(results.getByType("Bauble").get(0).getPosition().getAngleTo(position) - ship.getOrientation());
		case THRUST:
			state = States.BRAKE;
			return new ThrustCommand('B', 5, 1);
		case BRAKE:
			state = States.NEW_RADAR;
			return new BrakeCommand(0.1);
		default:
			throw new IllegalStateException("Unknown State");
		}
	}

	private Point center;

	@Override
	public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
		center = new Point(worldWidth / 2, worldHeight / 2);
		return new RegistrationData("Trevor", new Color(26, 236, 41), 11);
	}
}
