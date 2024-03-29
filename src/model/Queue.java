package model;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import util.Point3D;
import util.Triangle;

/**
 * Represente la queue de l'oiseau en deux parties, droite et gauche
 * 
 * @author mitrail
 *
 */
public class Queue implements InterfaceCorps {

	/**
	 * variation de la hauteur de la queue
	 */
	private float delta = 0;
	/**
	 * savoir si la queue monte ou descent
	 */
	private boolean monte = true;

	/**
	 * L'oiseau auquel le membre est rattache
	 */
	private Oiseau oiseau; // l'oiseau

	/**
	 * Liste des triangles de la partie droite de la queue
	 */
	private ArrayList<Triangle> pDroite = new ArrayList<Triangle>();
	/**
	 * Liste des triangles de la partie gauche de la queue
	 */
	private ArrayList<Triangle> pGauche = new ArrayList<Triangle>();
	private float coef = 1.0f;

	/**
	 * Ensemble des points de la queue
	 */
	private Point3D topLeft, topRight, rearLeft, rearRight, botLeft, botRight;

	/**
	 * 
	 * @param o
	 *            Oiseau objet central
	 */
	public Queue(Oiseau o) {

		// haut/bas
		float top = 1.5f * coef;
		float bottom = -1.5f * coef;
		// milieu
		float alignRight = -4.0f * coef;
		float aligneLeft = 4.0f * coef;
		// droite/gauche
		float left = -2.5f * coef;
		float right = 2.5f * coef;
		// avant/arriere
		float end = -8.0f * coef;
		float beg = -2 * coef;
		// centre
		float mid = 0 * coef;

		this.oiseau = o;
		topLeft = new Point3D(oiseau.getX() + left, oiseau.getY() + top, oiseau.getZ() + beg);
		topRight = new Point3D(oiseau.getX() + right, oiseau.getY() + top, oiseau.getZ() + beg);
		rearLeft = new Point3D(oiseau.getX() + aligneLeft, oiseau.getY() + mid, oiseau.getZ() + end);
		rearRight = new Point3D(oiseau.getX() + alignRight, oiseau.getY() + mid, oiseau.getZ() + end);
		botLeft = new Point3D(oiseau.getX() + left, oiseau.getY() + bottom, oiseau.getZ() + beg);
		botRight = new Point3D(oiseau.getX() + right, oiseau.getY() + bottom, oiseau.getZ() + beg);

		// top
		pGauche.add(new Triangle(topLeft, rearLeft, topRight));
		// interieur
		pGauche.add(new Triangle(botLeft, rearLeft, topLeft));
		// bottom
		pGauche.add(new Triangle(botRight, rearLeft, botLeft));
		// exterieur
		pGauche.add(new Triangle(botRight, topRight, rearLeft));

		// top
		pDroite.add(new Triangle(rearRight, topRight, topLeft));
		// interieur
		pDroite.add(new Triangle(topRight, rearRight, botRight));
		// bottom
		pDroite.add(new Triangle(botRight, rearRight, botLeft));
		// exterieur
		pDroite.add(new Triangle(topLeft, botLeft, rearRight));

	}

	/**
	 * Faut varier les points de la queue pour les faire bouger
	 * 
	 * @param angle
	 *            float la modification a apporter a la queue
	 */
	public void battre(float angle) {
		if (monte && delta > 1.0f) {
			monte = false;
		} else if (!monte && delta < -1.0f) {
			monte = true;
		}

		if (!monte) {
			angle = -angle;
		}

		delta += angle;
		rearLeft.moveY(angle);
		rearRight.moveY(angle);

	}

	/**
	 * Trace les informations contenues dans l'objet
	 * 
	 * @param gl
	 *            l'objet effectuant les positionnement des points
	 */
	public void render(GL2 gl) {
		for (Triangle t : pDroite) {
			t.render(gl, oiseau);
		}

		for (Triangle t : pGauche) {
			t.render(gl, oiseau);
		}
	}

}
