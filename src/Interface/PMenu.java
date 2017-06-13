package Interface;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import IA.IAMinMax;
import Moteur.Case;
import Moteur.Humain;
/**
 * Class Menu qui permet de g�rer le menu principale
 *
 */
public class PMenu extends JPanel implements ActionListener{
	private JCoolButton bIAvIA = new JCoolButton("IA vs IA");
	private JCoolButton bJvIA = new JCoolButton("Joueur vs IA");
	private JCoolButton bJvJ = new JCoolButton("Joueur vs Joueur");
	private JCoolButton bScores = new JCoolButton("Scores");
	private JCoolButton bOptions = new JCoolButton("Options");
	private JLabel lTitre = new JLabel("PUISSANCE X");
	private Font buttonFont = new Font("Serif", Font.PLAIN, 26);
	private Font titreFont = new Font("ARIAL", Font.BOLD, 45);
	private final Application app;
	private int buttonWidth = 350;
	private int buttonHeight = 70;
	
	public PMenu(final Application application){
		this.app = application;
		setLayout(null);
		lTitre.setBounds(175, 20, 350, 100);
		lTitre.setHorizontalTextPosition(SwingConstants.CENTER);
		lTitre.setFont(titreFont);
		lTitre.setForeground(Color.RED);
		
		bIAvIA.setBounds(175, 150, buttonWidth, buttonHeight);
		bIAvIA.setFont(buttonFont); 
		bJvIA.setBounds(175, 220, buttonWidth, buttonHeight);
		bJvIA.setFont(buttonFont); 
		bJvJ.setBounds(175, 290, buttonWidth, buttonHeight);
		bJvJ.setFont(buttonFont); 
		bScores.setBounds(175, 360, buttonWidth, buttonHeight);
		bScores.setFont(buttonFont); 
		bOptions.setBounds(175, 550, buttonWidth, buttonHeight);
		bOptions.setFont(buttonFont);
		
		this.add(bIAvIA);
		this.add(bJvIA);
		this.add(bJvJ);
		this.add(bScores);
		this.add(bOptions);
		this.add(lTitre);
		
		bIAvIA.addActionListener(this);
		bJvIA.addActionListener(this);
		bJvJ.addActionListener(this);
		bScores.addActionListener(this);
		bOptions.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Bouton IA vs IA
		if (e.getSource() == bIAvIA)
		{
			IAMinMax r1 = new IAMinMax(Case.ROUGE);
	    	IAMinMax r2 = new IAMinMax(Case.JAUNE);
			app.montrerPlateau(r1, r2);
		}
		// Bouton Joueur vs IA
		else if (e.getSource() == bJvIA){
			Thread thread = new Thread(){
				public void run(){
					String nom = app.montrerPseudo(1);
					
					if (nom != null)
					{
						app.getGestionnaireScore().ajouterJoueur(nom);
						Humain j = new Humain(nom, Case.ROUGE);
					   	IAMinMax r1 = new IAMinMax(Case.JAUNE);
						app.montrerPlateau(j, r1);
					}
				}
			};
			thread.start();
		}
		// Bouton Joueur vs Joueur
		else if (e.getSource() == bJvJ){
			Thread thread = new Thread(){
				public void run(){
					String nomJ1 = app.montrerPseudo(1);
					
					if (nomJ1 != null)
					{
						app.getGestionnaireScore().ajouterJoueur(nomJ1);
						String nomJ2;
						
						// On ne veut pas que les 2 joueurs aient les mêmes pseudo
						do{
							nomJ2 = app.montrerPseudo(2);
						}
						while (nomJ2 == nomJ1);
							
						if (nomJ2 != null)
						{
							app.getGestionnaireScore().ajouterJoueur(nomJ2);
							Humain j1 = new Humain(nomJ1, Case.ROUGE);
						   	Humain j2 = new Humain(nomJ2, Case.JAUNE);
							app.montrerPlateau(j1, j2);
						}
					}
				}
			};
			thread.start();
		}
		// Bouton Scores
		else if (e.getSource() == bScores){
			app.montrerScores();
		}
		// Bouton Options
		else if (e.getSource() == bOptions){
			app.montrerOptions();
		}
	}
	
}
