import java.util.*;

public class MaxwellContainer
{
    
    private int h;
    private int w;
    private int d;
    private int b;
    private int r;
    private int[][] particles;
    private int[][] holes = new int[0][3];
    private ArrayList<Circle> holeCircles = new ArrayList<>();
    private int[] demons = new int[0];
    private ArrayList<Circle> particleCircles = new ArrayList<>();
    private String color;
    private boolean isRed;
    private int px;
    private int py;
    private int vx;
    private int vy;
    private Rectangle rectangleLeft;
    private Rectangle rectangleRight;
    private Rectangle demon;
    private Circle newCircle;
    private int middle;
    private int squareSize;
    private ArrayList<Rectangle> demonRectangles = new ArrayList<>();
    private int xPositionLeft;
    private boolean isRunning;
    private boolean lastActionSuccess = false;
    
    /**
     * Constructor de MaxwellContainer con altura y anchura.
     * 
     * @param h Altura del contenedor.
     * @param w Anchura del contenedor.
     */
    public MaxwellContainer(int h, int w) {
        
        this.h = h;
        this.w = w;
        
        this.particles = new int[0][4];
        Canvas.getCanvas();
        xPositionLeft = 50;
        rectangleLeft = new Rectangle(w, h, xPositionLeft, 0, "magenta", true);
        rectangleRight = new Rectangle(w, h, w+xPositionLeft+5, 0, "yellow", true);
        
 
    }
    
    public MaxwellContainer(int h, int w, int d, int b, int r, int[][] particles){
    
    this.h = h;
    this.w = w;
    this.d = d;
    this.b = b;
    this.r = r;
    this.particles = new int[b + r][4];
    particleCircles = new ArrayList<>();

    rectangleLeft = new Rectangle(w, h, 0, 50, "magenta", true);
    rectangleRight = new Rectangle(w, h, w, 50, "yellow", true);

    Canvas.getCanvas();

    int xMin1 = rectangleLeft.getxPosition();
    int yMin1 = rectangleLeft.getyPosition();
    int xMax1 = xMin1 + w - 10;
    int yMax1 = yMin1 + h - 10;

    int xMin2 = rectangleRight.getxPosition();
    int yMin2 = rectangleRight.getyPosition();
    int xMax2 = xMin2 + w - 10;
    int yMax2 = yMin2 + h - 10;
    
    boolean[] particlePlaced = new boolean[b + r];
    Random random = new Random();

    
    for (int i = 0; i < b; i++) {
        
        boolean inFirstContainer = random.nextBoolean();
        int px, py;
        
        if (inFirstContainer) {
            
            px = xMin1 + random.nextInt(w - 20) + 10;
            py = yMin1 + random.nextInt(h - 20) + 10;
            
        } 
        else {

            px = xMin2 + random.nextInt(w - 20) + 10;
            py = yMin2 + random.nextInt(h - 20) + 10;
            
        }
    
        int vx = particles[i][2];
        int vy = particles[i][3];
    
        String particleId = "blue_" + i;
        addParticles(particleId, false, px, py, vx, vy);
    }

    for (int i = 0; i < r; i++) {

        boolean inFirstContainer = random.nextBoolean();
        int px, py;
        
        if (inFirstContainer) {

            px = xMin1 + random.nextInt(w - 20) + 10;
            py = yMin1 + random.nextInt(h - 20) + 10;
            
        } 
        else {
            
            px = xMin2 + random.nextInt(w - 20) + 10;
            py = yMin2 + random.nextInt(h - 20) + 10;
            
        }
    
        int vx = particles[i + b][2]; 
        int vy = particles[i + b][3];
    
        String particleId = "red_" + i;
        addParticles(particleId, true, px, py, vx, vy);
        
    }

    demons = Arrays.copyOf(demons, demons.length + 1);
    demons[demons.length - 1] = d;
    addDemon(d);
}
    

    /**
     * Agrega un demonio en la posición dada.
     * 
     * @param d Posición del demonio.
     */

    public void addDemon(int d){
        int xLeft = rectangleRight.getxPosition();  
        int xRight = rectangleLeft.getxPosition();  
        int wLeft = rectangleRight.getWidth();
        int wRight = rectangleLeft.getWidth();
    
    
        int centerLeft = xLeft + wLeft / 2;
        int centerRight = xRight + wRight / 2;
    
     
        int middle = (centerLeft + centerRight) / 2;
    
        demons = Arrays.copyOf(demons, demons.length + 1);
        demons[demons.length - 1] = d;
        
        Rectangle demon1 = new Rectangle(10, 10, middle - 5, d, "green", true);
    
        demonRectangles.add(demon1);
        lastActionSuccess = true;
    }


    /**
     * Elimina un demonio del contenedor.
     * 
     * @param d Posición del demonio a eliminar.
     */
    public void delDemon(int d) {
        boolean found = false;
        int indexToRemove = 0;
        for (int i = 0; i < demons.length; i++) {
            if (demons[i] == d) {
                found = true;
                indexToRemove = i;
                break;
            }
    }

    
    if (found) {
        
        int[] newDemons = new int[demons.length - 1];
        for (int i = 0, j = 0; i < demons.length; i++) {
            if (i != indexToRemove) {
                newDemons[j++] = demons[i];
            }
        }
        demons = newDemons;

        
        if (indexToRemove < demonRectangles.size()) {
            Rectangle demonToRemove = demonRectangles.get(indexToRemove);
            demonToRemove.makeInvisible(); 
            demonRectangles.remove(indexToRemove);
            
        lastActionSuccess = true; 
    } else{
        lastActionSuccess = false;
        }
        }
    }
    /**
     * Agrega una partícula al contenedor.
     * 
     * @param color  Color de la partícula.
     * @param isRed  Indica si la partícula es roja.
     * @param px     Posición x de la partícula.
     * @param py     Posición y de la partícula.
     * @param vx     Velocidad en x de la partícula.
     * @param vy     Velocidad en y de la partícula.
     */
    public void addParticles(String color, boolean isRed, int px, int py, int vx, int vy){
        int[][] newParticles = new int[particles.length + 1][4];
    
        for(int i = 0; i < particles.length; i++) {
            newParticles[i] = particles[i];
        }
    
        newParticles[particles.length] = new int[]{px, py, vx, vy};
        
        particles = newParticles;
        
        Circle newParticle;
        if(isRed){
            newParticle = new Circle(color, 5, px, py, "red", true);
        }
        else{
            newParticle = new Circle(color, 5, px, py, "blue", true);
        }
        
        particleCircles.add(newParticle);
        
        lastActionSuccess = true;
    }
    
    /**
     * Borra una particula del contenedor.
     * 
     * @param el identificador de cada particula.
     */
    public void delParticle(String id) {
        List<Integer> indicesToRemove = new ArrayList<>();

        for (int j = 0; j < particleCircles.size(); j++) {
            Circle particle = particleCircles.get(j);
            if (particle.getid().equals(id)) {
                indicesToRemove.add(j);
            }
        }

        if (!indicesToRemove.isEmpty()) {
            for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
                int index = indicesToRemove.get(i);
                particleCircles.remove(index);
            }

            int[][] newParticles = new int[particles.length - indicesToRemove.size()][4];
            int index = 0;
            for (int i = 0; i < particles.length; i++) {
                if (!indicesToRemove.contains(i)) {
                    newParticles[index++] = particles[i];
                }
                else{
                    makeInvisible();
                    makeVisible();
                }
            }
            particles = newParticles;
            lastActionSuccess = true;
        } else {
            lastActionSuccess = false;
        }
    }

    /**
     * Agrega un hoyo al contenedor.
     * 
     * @param px     Posición x de la partícula.
     * @param py     Posición y de la partícula.
     * @param particles 
     */
    public void addHole(int px, int py, int particles){
        int xMin1 = rectangleLeft.getxPosition();
        int yMin1 = rectangleLeft.getyPosition();
        int xMax1 = xMin1 + w;
        int yMax1 = yMin1 + h;

        int xMin2 = rectangleRight.getxPosition();  
        int yMin2 = rectangleRight.getyPosition();
        int xMax2 = xMin2 + w;
        int yMax2 = yMin2 + h;

        int xMin, yMin, xMax, yMax;

        if (px >= xMin1 && px <= xMax1 && py >= yMin1 && py <= yMax1) {
            xMin = xMin1;
            yMin = yMin1;
            xMax = xMax1;
            yMax = yMax1;
        } 
        else {
            xMin = xMin2;
            yMin = yMin2;
            xMax = xMax2;
            yMax = yMax2;
        }
        
        px = Math.max(xMin, Math.min(px, xMax));
        py = Math.max(yMin, Math.min(py, yMax));
        
        int[][] newHoles = new int[holes.length + 1][3];
        
        for(int i = 0; i < holes.length; i++) {
            newHoles[i] = holes[i];
        }
        
        newHoles[holes.length] = new int[]{px, py, vx, particles};
        
        holes = newHoles;
        
        Circle newHole = new Circle(color, 5, px, py, "black", true);
        
        holeCircles.add(newHole);
        
        lastActionSuccess = true;
    }
    
    public void start(int tick) {
        isRunning = true;
        for (int t = 0; t < tick && isRunning; t++) {
            
            List<Integer> indicesToRemove = new ArrayList<>();
            
            for (int i = 0; i < particles.length; i++) {
                int px = particles[i][0];  
                int py = particles[i][1];  
                int vx = particles[i][2];  
                int vy = particles[i][3];  
    
                boolean isOnLeftSide = false;
                int xMin1 = rectangleLeft.getxPosition();
                int yMin1 = rectangleLeft.getyPosition();
                int xMax1 = xMin1 + w;
                int yMax1 = yMin1 + h;
    
                int xMin2 = rectangleRight.getxPosition();  
                int yMin2 = rectangleRight.getyPosition();
                int xMax2 = xMin2 + w;
                int yMax2 = yMin2 + h;
    
                int xMin, yMin, xMax, yMax;
    
                if (px >= xMin1 && px <= xMax1 && py >= yMin1 && py <= yMax1) {
                    xMin = xMin1;
                    yMin = yMin1;
                    xMax = xMax1;
                    yMax = yMax1;
                    isOnLeftSide = true;
                } 
                else {
                    xMin = xMin2;
                    yMin = yMin2;
                    xMax = xMax2;
                    yMax = yMax2;
                    isOnLeftSide = false;
                }
                
                boolean hitsDemon = false;
                boolean shouldPassThrough = false;
                
                int nextPx = px + vx;
                int middleX = (xMax1 + xMin2) / 2;
                
                if ((isOnLeftSide && vx > 0 && nextPx >= middleX) || (!isOnLeftSide && vx < 0 && nextPx <= middleX)) {
                    for (int j = 0; j < demons.length; j++) {
                        int demonY = demons[j];
                        if (Math.abs(py - demonY) <= 5) { 
                            hitsDemon = true;
                            break;
                        }
                    }
                }
                
                if (hitsDemon) {
                    Circle particleCircle = particleCircles.get(i);
                    String particleColor = particleCircle.getColor();
                    boolean isRedParticle = particleColor.equals("red");
                    
                    if ((isRedParticle && !isOnLeftSide) || (!isRedParticle && isOnLeftSide)) {
                        shouldPassThrough = true;
                    } else {
                        vx = -vx;
                        particles[i][2] = vx;
                    }
                    
                    if (shouldPassThrough) {
                        if (isOnLeftSide) {
                            px = xMin2 + 5; 
                        } else {
                            px = xMax1 - 5; 
                        }
                        particles[i][0] = px;
                        particleCircle.moveHorizontal(px - particles[i][0]);
                    }
                }
                else {
                    if (px + vx < xMin || px + vx > xMax) {
                        vx = -vx;
                        particles[i][2] = vx;
                    }
                    if (py + vy < yMin || py + vy > yMax) {
                        vy = -vy;
                        particles[i][3] = vy;
                    }
                    
                    px += vx;
                    py += vy;
                    particles[i][0] = px;
                    particles[i][1] = py;
                    particleCircles.get(i).moveHorizontal(vx);
                    particleCircles.get(i).moveVertical(vy);
                }
                
                
                for (int[] hole : holes){
                    int holeX = hole[0];
                    int holeY = hole[1];
                    
                    if (Math.abs(px - holeX) <= 3 && Math.abs(py - holeY) <= 3){
                        indicesToRemove.add(i);
                        break;
                    }
                }
            }
            
            if (!indicesToRemove.isEmpty()){
                int[][] newParticles = new int[particles.length - indicesToRemove.size()][4];
                ArrayList<Circle> newParticleCircles = new ArrayList<>();
                int index = 0;
                
                for (int i = 0; i < particles.length; i++){
                    
                    if (!indicesToRemove.contains(i)){
                        newParticles[index] = particles[i];
                        newParticleCircles.add(particleCircles.get(i));
                        index++;
                    }
                    else{
                        makeInvisible();
                        makeVisible();
                    }
                }
                particles = newParticles;
                particleCircles = newParticleCircles;
            }
            
            if (isGoal()) {
                break;
            }
        }
    }
    
    


    /**
     * Verifica si se ha alcanzado el estado objetivo:
     * Todas las partículas rojas en el rectangulo izquierdo,
     * y todas las partículas azules en el derech.
     * 
     * @return `true` si se alcanzó el objetivo, `false` si no se alcanzo.
     */
    public boolean isGoal() {
        if (particles.length == 0) {
            return false; 
        }
        
        int xMin1 = rectangleLeft.getxPosition();
        int yMin1 = rectangleLeft.getyPosition();
        int xMax1 = xMin1 + w;
        int yMax1 = yMin1 + h;
    
        int xMin2 = rectangleRight.getxPosition();  
        int yMin2 = rectangleRight.getyPosition();
        int xMax2 = xMin2 + w;
        int yMax2 = yMin2 + h;
        
        for (int i = 0; i < particles.length; i++) {
            int px = particles[i][0];
            int py = particles[i][1];
            Circle particle = particleCircles.get(i);
            String color = particle.getColor();
            boolean isRedParticle = color.equals("red");
            
            boolean isInLeftChamber = (px >= xMin1 && px <= xMax1 && py >= yMin1 && py <= yMax1);
            
            if ((isRedParticle && !isInLeftChamber) || (!isRedParticle && isInLeftChamber)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Devuelve la lista de demonios en el contenedor.
     * 
     * @return Un arreglo con las posiciones de los demonios.
     */
    public int[] demons(){
        Arrays.sort(demons);
        return demons;
    }
    
    /**
     * Devuelve la lista de partículas en el contenedor.
     * 
     * @return Una matriz con la información de las partículas.
     */
    public int[][] particles(){
        return particles;
    }
    
    /**
     * Devuelve la lista de agujeros en el contenedor.
     * 
     * @return Una matriz con la información de los agujeros.
     */
    public int[][] holes(){
        return holes;
    }
    
    /**
     * Hace invisible el contenedor y sus elementos.
     */
    public void makeVisible(){
        
        rectangleLeft.makeVisible();
        rectangleRight.makeVisible();
    
        
        for (Rectangle demon : demonRectangles) {
        demon.makeVisible();
        }
    
        
        for (Circle particle : particleCircles) {
            particle.makeVisible();
        }

        for (Circle h : holeCircles) {
            h.makeVisible();
        }
        
    }
    
    /**
     * Hace invisible el contenedor y sus elementos.
     */
    public void makeInvisible(){
            
        rectangleLeft.makeInvisible();
        rectangleRight.makeInvisible();
    
        
        for (Rectangle demon : demonRectangles) {
        demon.makeInvisible();
        }
    
        
        for (Circle particle : particleCircles) {
            particle.makeInvisible();
        }
        
        for (Circle h : holeCircles) {
            h.makeInvisible();
        }
    }
    
    public void finish(){
        
        isRunning = false;
        
    }
    
    /**
     * Método de verificación del estado del contenedor.
     * 
     * @return `true` si el estado es válido, `false` en caso contrario.
     */
    public boolean ok(){
        return lastActionSuccess;
    }
    
    /**
     * Devuelve la altura del contenedor.
     * 
     * @return La altura del contenedor.
     */
    private int getH(){
        return h;
    }
    
     /**
     * Devuelve el ancho del contenedor.
     * 
     * @return El ancho del contenedor.
     */
    private int getW(){
        return w;
    }
    
}
