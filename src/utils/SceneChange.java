
package utils;

import java.io.IOException;
import javafx.event.ActionEvent;

/**
 *
 * @author gmorr
 */
public interface SceneChange {
    void sceneChange(String direct, ActionEvent event)throws IOException;    
}
