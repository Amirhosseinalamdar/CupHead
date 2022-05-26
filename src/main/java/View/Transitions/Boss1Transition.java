package View.Transitions;

import Application.App;
import Controller.GameController;
import View.Component.Ball;
import View.Component.Boss1;
import View.Component.Plane;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;

public class Boss1Transition extends Transition {
    private boolean shoot;
    private boolean flag;
    private boolean ball;
    private int bossPhase;
    private boolean boss2Shoot;
    private boolean isBoss2Idle;
    private int way;
    private Random random = new Random();
    private int counter;
    private boolean isBoss3Idle;
    private boolean boss3Shoot;

    public void setBossPhase(int bossPhase) {
        this.bossPhase = bossPhase;
    }

    public int getBossPhase() {
        return bossPhase;
    }

    public Boss1Transition() {
        bossPhase = 1;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        isBoss2Idle = true;
        isBoss3Idle = true;
        flag = true;
        ball = true;
        boss2Shoot = false;
        boss3Shoot = false;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
        this.isBoss2Idle = !shoot;
        this.isBoss3Idle = !shoot;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    protected void interpolate(double frac) {
        if (bossPhase == 1) {
            runPhaseOne(frac);
        } else if (bossPhase == 2) {
            runPhaseTwo(frac);
        } else if (bossPhase == 3) {
            runPhaseThree(frac);
        }
    }

    public boolean isBoss2Idle() {
        return isBoss2Idle;
    }

    private void runPhaseThree(double frac){
        Boss1.getInstance().setY(650);
        if(isBoss3Idle){
            int frame = (int) (frac * 11 + 4);
            if(frame < 10)
                Boss1.getInstance().setImage(new Image("/images/boss3/stretcher_idle_000"+frame+".png"));
            else
                Boss1.getInstance().setImage(new Image("/images/boss3/stretcher_idle_00"+frame+".png"));
        }else{
            int frame = (int) (frac * 29 + 1);
            if(frame < 10)
                Boss1.getInstance().setImage(new Image("/images/boss3Shoot/stretcher_garbage_000"+frame+".png"));
            else
                Boss1.getInstance().setImage(new Image("/images/boss3Shoot/stretcher_garbage_00"+frame+".png"));
            if(frame == 30){
                isBoss3Idle = true;
                boss3Shoot = false;
            }else if(frame == 18 && !boss3Shoot){
                GameController.getInstance().getBossShots().stop();
                GameController.getInstance().getBossShots().play();
                boss3Shoot = true;
                Ball ball = new Ball(
                        Boss1.getInstance().getX() + 600, Boss1.getInstance().getY(), "/images/egg2.png");
                ball.getBallShootTransition().play();
                ((Pane) (App.getStage().getScene().getRoot())).getChildren().add(ball);
                ball.setPhase3(true);
            }
        }
        Boss1.getInstance().setX(Boss1.getInstance().getX()+Boss1.getInstance().getSpeed());
        if(Boss1.getInstance().getX()<-300 || Boss1.getInstance().getX()>1000)
            Boss1.getInstance().setSpeed(-Boss1.getInstance().getSpeed());
        checkCollision();
    }
    private void runPhaseTwo(double frac) {
        if (isBoss2Idle) {
            int frame = (int) (frac * 15 + 1);
            if (frame < 10)
                Boss1.getInstance().setImage(new Image("/images/boss2/egghead_idle_000" + frame + ".png"));
            else
                Boss1.getInstance().setImage(new Image("/images/boss2/egghead_idle_00" + frame + ".png"));
        } else {
            int frame = (int) (frac * 19 + 1);
            if (frame < 10)
                Boss1.getInstance().setImage(new Image("/images/boss2Shoot/egghead_shoot_000" + frame + ".png"));
            else
                Boss1.getInstance().setImage(new Image("/images/boss2Shoot/egghead_shoot_00" + frame + ".png"));
            if (frame == 20) {
                isBoss2Idle = true;
                boss2Shoot = false;
            } else if (frame == 13 && !boss2Shoot) {
                GameController.getInstance().getBossShots().stop();
                GameController.getInstance().getBossShots().play();
                boss2Shoot = true;
                Ball ball = new Ball(Boss1.getInstance().getX(), Boss1.getInstance().getY() + 70, "/images/eggHead.png");
                ball.getBallShootTransition().play();
                ((Pane) (App.getStage().getScene().getRoot())).getChildren().add(ball);
            }
        }

        int key = 0;
        double x = Boss1.getInstance().getX();
        double y = Boss1.getInstance().getY();
        double speed = Boss1.getInstance().getSpeed();
        if(counter<100){
            counter++;
        }else {
            counter = 0;
            while (true) {
                if ((key = random.nextInt(4)) == 0 && checkBounds(x, y + 100 * speed)) {
                    way = 1;
                    break;
                } else if (key == 1 && checkBounds(x, y - 100 * speed)) {
                    way = 2;
                    break;
                } else if (key == 2 && checkBounds(x + 100 * speed, y)) {
                    way = 3;
                    break;
                } else if (checkBounds(x - 100 * speed, y)) {
                    way = 4;
                    break;
                }
                key = random.nextInt(4);
            }
        }
        way(way,x,y,speed);
        checkCollision();
    }
    private void way(int key,double x,double y,double speed){
        if(key == 1){
            Boss1.getInstance().setY(y+speed);
        }else if(key == 2){
            Boss1.getInstance().setY(y-speed);
        }else if(key == 3){
            Boss1.getInstance().setX(x+speed);
        }else{
            Boss1.getInstance().setX(x - speed);
        }
    }

    private boolean checkBounds(double x, double y) {
        return x < 1500 && y < 800 && x > 300 && y > 0;
    }

    private void runPhaseOne(double frac) {
        if (!shoot) {
            int frame = (int) (frac * 5 + 1);
            Boss1.getInstance().setImage(new Image("/images/boss1/" + frame + ".png"));
        } else {
            int frame = (int) (frac * 11 + 1);
            if (flag) {
                Boss1.getInstance().setX(Boss1.getInstance().getX() - 120);
                flag = false;
            }
            Boss1.getInstance().setImage(new Image("/images/boss1Shoot/" + frame + ".png"));
            if (frac == 1) {
                flag = true;
                shoot = false;
                this.ball = true;
                Boss1.getInstance().setX(Boss1.getInstance().getX() + 120);
            }
            if (frame == 6 && ball) {
                GameController.getInstance().getBossShots().stop();
                GameController.getInstance().getBossShots().play();
                Ball ball = new Ball(Boss1.getInstance().getX(),
                        Boss1.getInstance().getY() + 220, "/images/egg.png");
                ((Pane) (App.getStage().getScene().getRoot())).getChildren().add(ball);
                ball.getBallShootTransition().play();
                this.ball = false;
            }
        }
        if (Boss1.getInstance().getY() + Boss1.getInstance().getSpeed() +
                Boss1.getInstance().getLayoutBounds().getHeight() <= 1000 && Boss1.getInstance().getY()
                + Boss1.getInstance().getSpeed() >= -100)
            Boss1.getInstance().setY(Boss1.getInstance().getY() + Boss1.getInstance().getSpeed());
        else Boss1.getInstance().setSpeed(-Boss1.getInstance().getSpeed());
        checkCollision();
    }
    private void checkCollision(){
        if (Boss1.getInstance().hasCollisionWithPlane() && !Plane.getInstance().isBlinked()) {
            Plane.getInstance().setHealth(Plane.getInstance().getHealth() - Boss1.getInstance().getDamage());
            GameController.getInstance().updatePlayer();
            Plane.getInstance().setX(0);
            Plane.getInstance().setBlinked(true);
            Plane.getInstance().setTime0(System.currentTimeMillis());
        }
    }
}
