import java.awt.Color;
import java.math.*;
import java.util.*;

class Particle {
	double mass;
	double velocity_X;
	double velocity_Y;
	double x, y;
	double radius;

	public Particle(double mass, double velocity_X, double velocity_Y) {
		this.x = Math.random();
		this.y = Math.random();
		
		// Out of bound ?
		while( (x + radius) > 1 || (x-radius) < 0) this.x = Math.random();
		while(y + radius > 1 || y-radius < 0) this.y = Math.random();
		
		this.mass = mass;
		this.velocity_X = velocity_X;
		this.velocity_Y = velocity_Y;
		this.radius = mass * 0.001;
	}
	public void update() {
	    // did I hit a wall 
		if (this.x <= radius || this.x >= 1-radius) { 
			this.velocity_X = -velocity_X;
		}	
		else if(this.y >= 1 - radius || this.y <=  radius) {
			this.velocity_Y = -velocity_Y;
		}
			x += (velocity_X * 0.0001);
			y += (velocity_Y * 0.0001);
		}
	
	public void collided(Particle b){
		// X component
		double newvelocity_X = ( ((this.mass  - b.mass) / (this.mass + b.mass))*velocity_X ) + ( ( (2*b.mass)/(this.mass + b.mass) )*b.velocity_X ); 	
		// Y component
		double newvelocity_Y = ( ((this.mass  - b.mass) / (this.mass + b.mass))*velocity_Y ) + ( ( (2*b.mass)/(this.mass + b.mass) )*b.velocity_Y ); 
		
		
		b.velocity_X = ( ((2*this.mass)/(this.mass + b.mass))*this.velocity_X) - ( ((this.mass - b.mass)/(this.mass + b.mass))*b.velocity_X);
		b.velocity_Y = ( ((2*this.mass)/(this.mass + b.mass))*this.velocity_Y) - ( ((this.mass - b.mass)/(this.mass + b.mass))*b.velocity_Y);
		
		
		this.velocity_X = newvelocity_X;
		this.velocity_Y = newvelocity_Y;
		
		
		this.update();
		b.update();
	}
	}


public class Animation {

	public static void main(String[] args) {
		ArrayList<Particle> system = new ArrayList<>();
		Scanner in = new Scanner(System.in);
		
		
		System.out.print("How many bodies in the system ? -> ");
		int n = in.nextInt();

		
		for (int i = 0; i < n; i++) {
			System.out.print("Enter mass of ball " + (i + 1) + " -> ");
			double mass = in.nextDouble();
			System.out.print("Enter initial horizontal velocity of ball " + (i + 1) + " -> ");
			double velocity_X = in.nextDouble();
			System.out.print("Enter initial vertical velocity of ball " + (i + 1) + " -> ");
			double velocity_Y = in.nextDouble();
			
			Particle p = new Particle(mass, velocity_X, velocity_Y);
			system.add(p);
		}
		
		ArrayList<Particle> collided = new ArrayList<>();
		
		while (true) {
			StdDraw.clear();
			collided.clear();
			// Check for collisions 
			for(Particle a : system) {
				
				for(Particle b : system) {
				
				if(a == b) continue;
				if(collided.contains(a) || collided.contains(b)) continue;
				
				double dist = Math.sqrt( ( Math.pow(a.x-b.x,2) + Math.pow(a.y-b.y, 2) ));
				dist = dist * 100;
				dist = Math.round(dist);
				dist = dist/100;
			
				double radius = Math.round( (a.radius +  b.radius) *100);
				radius = radius/100;
				
				System.out.println("dist "+ dist + " , radius " + (radius) );
				if(dist == (a.radius + b.radius)) {
					a.collided(b);
					collided.add(b);
				}
				
				}	
			}
			// Update motion
			for(Particle p : system) {
				p.update();
			}
			
			// Draw Again	
			for (Particle p : system) {
				StdDraw.filledCircle(p.x, p.y, p.radius);
				StdDraw.setPenColor(Color.BLUE);
				StdDraw.text(p.x, p.y, "V = "+  ( Math.round( (Math.sqrt(Math.pow(p.velocity_X,2) + Math.pow(p.velocity_Y, 2)))*1000 ) ) /1000);
				StdDraw.setPenColor(Color.BLACK);
			}
			
			StdDraw.show(10);
		}
	}

}
