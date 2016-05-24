/**
 * 
 */
/**
 * @author silence
 *
 */
package GA;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;


class generate_sound {
		
	Player player;
	Pattern pattern;
		
	generate_sound()
	{	
		this.pattern = new Pattern();
		this.player = new Player();
	}
}