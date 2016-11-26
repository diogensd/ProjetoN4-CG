package n4;

import java.util.ArrayList;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

public class World {
	
	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;
	
	
	private ArrayList<ObjetoGrafico> objetos;
	private ArrayList<ObjetoGrafico> objetosInativos;
	private ArrayList<Asteroid> asteroids;
	private Espaco espaco;
	private StarShip starShip;
	
	
	public World(GL gl, GLU glu, GLUT glut, GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = gl;
		glu = glu;
		glut = glut;
		glDrawable.setGL(new DebugGL(gl));
		
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		objetos = new ArrayList<>();
		objetosInativos = new ArrayList<>();
		asteroids = new ArrayList<>();
		
		espaco = new Espaco(gl);	
		objetos.add(espaco);
		
		starShip = new StarShip(gl, glut);
		objetos.add(starShip);
		
		for (int i = 0; i < 2 ; i++) {
			Asteroid a = new Asteroid(gl, glut);
			objetos.add(a);
			asteroids.add(a);
		}	
		posiciona();
		
	}
	
	public void desenha() {
		
		if(!objetosInativos.isEmpty()){
			for (ObjetoGrafico objeto : objetosInativos) {
				objeto.setAtivo(true);
				objetos.remove(objeto);
			}
		}
		if (!objetos.isEmpty()) {
			for (ObjetoGrafico objeto : objetos) {
				
				if(objeto.isAtivo()){
					
					if(objeto instanceof Bullet){
						Bullet b = (Bullet) objeto;
						ArrayList<Asteroid> objc = b.checkColisao(asteroids);
						if (!objc.isEmpty()) {
							System.out.println(" COLISAO BULLET");
							b.setExplodiu(true);
							objetosInativos.add(b);
							for (Asteroid asteroid : objc) {
								objetosInativos.add(asteroid);
							}
						}
					}
					 objeto.drawn();
				
				}else{
					objetosInativos.add(objeto);
				}
			}
		}
	}
	
	private void posiciona(){
		int limite = 10;
		for (ObjetoGrafico asteroid : asteroids) {
			asteroid.translacaoXYZ(limite, 0.0f, -60.f);
			limite *= -1;
		}
	}
	
	public void addBUllets(StarShip starShip) {
        Bullet bullet = starShip.shootBullet(asteroids);
        if (bullet != null) {
            objetos.add(bullet);
        }
    }
	
	public ArrayList<ObjetoGrafico> getObjetos() {
		return objetos;
	}

	public void setObjetos(ArrayList<ObjetoGrafico> objetos) {
		this.objetos = objetos;
	}
	
	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}

	public void setAsteroids(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}

	public Espaco getEspaco() {
		return espaco;
	}

	public void setEspaco(Espaco espaco) {
		this.espaco = espaco;
	}

	public StarShip getStarShip() {
		return starShip;
	}

	public void setStarShip(StarShip starShip) {
		this.starShip = starShip;
	}
	
}
