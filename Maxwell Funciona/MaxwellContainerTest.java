

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class MaxwellContainerTest {
    
    private MaxwellContainer container;
    
    @Before
    public void setUp() {
        container = new MaxwellContainer(200, 300); 
    }

    @Test
    public void accordingBRShouldCreateEmptyContainer() {
        assertNotNull(container); 
    }

    @Test
    public void accordingBRShouldAddDemon() {
        container.addDemon(100);
        int[] demons = container.demons();
        assertTrue(demons.length > 0);
        assertEquals(100, demons[0]);
    }

    @Test
    public void accordingBRShouldAddParticle() {
        container.addParticles("red", true, 50, 50, 5, 5);
        int[][] particles = container.particles();
        assertEquals(1, particles.length);
        assertArrayEquals(new int[]{50, 50, 5, 5}, particles[0]);
    }

    @Test
    public void accordingBRShouldMoveParticles() {
        container.addParticles("blue", false, 50, 50, 10, 0);
        container.start(1); 
        int[][] particles = container.particles();
        assertEquals(60, particles[0][0]); 
        assertEquals(50, particles[0][1]); 
    }
}
