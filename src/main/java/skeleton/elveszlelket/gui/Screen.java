package skeleton.elveszlelket.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import skeleton.elveszlelket.Student;

public class Screen extends Pane {
	private GameView palyamegjelenito;
	
	public Screen(float WIDTH, float HEIGHT) {
		palyamegjelenito = new GameView(WIDTH, HEIGHT);
		this.getChildren().add(palyamegjelenito);
		
		this.setOnKeyPressed(e -> {
			switch(e.getCode()) {
				case I:
					this.palyamegjelenito.translateItemMenu();
					this.palyamegjelenito.closePickUpMenu();
					break;
				case S:
					this.palyamegjelenito.playerDown();
					this.palyamegjelenito.closePickUpMenu();
					break;
				case W:
					this.palyamegjelenito.playerUp();
					this.palyamegjelenito.closePickUpMenu();
					break;
				case D:
					this.palyamegjelenito.playerRight();
					this.palyamegjelenito.closePickUpMenu();
					break;
				case A:
					this.palyamegjelenito.playerLeft();
					this.palyamegjelenito.closePickUpMenu();
					break;
				case P:
					this.palyamegjelenito.translatePickUpMenu();
					this.palyamegjelenito.closeItemMenu();
					break;
				}
			e.consume();
		});
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getCode()) {
			case I:
				this.palyamegjelenito.translateItemMenu();
				this.palyamegjelenito.closePickUpMenu();
				break;
			case S:
				this.palyamegjelenito.playerDown();
				this.palyamegjelenito.closePickUpMenu();
				break;
			case W:
				this.palyamegjelenito.playerUp();
				this.palyamegjelenito.closePickUpMenu();
				break;
			case D:
				this.palyamegjelenito.playerRight();
				this.palyamegjelenito.closePickUpMenu();
				break;
			case A:
				this.palyamegjelenito.playerLeft();
				this.palyamegjelenito.closePickUpMenu();
				break;
			case P:
				this.palyamegjelenito.translatePickUpMenu();
				this.palyamegjelenito.closeItemMenu();
				break;
			}
		e.consume();
	}
	
	public void Update(Student jelenlegiJatekos) {
		palyamegjelenito.Update(jelenlegiJatekos);
	}
	
	// Csak a tesztelesnel kell.
	public void updateBecauseItemsHaveBeenManuallyAddedToStudent() {
		palyamegjelenito.updateItemsPos();
	}
}
