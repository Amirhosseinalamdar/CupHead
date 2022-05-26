package View.Transitions;


import Application.App;
import Controller.GameController;
import View.Component.Ball;
import View.Component.Plane;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class BallShootTransition extends Transition {
    private Ball ball;
    public BallShootTransition(Ball ball) {
        this.ball = ball;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        if(!ball.isPhase3()) {
            ball.setX(ball.getX() - ball.getSpeed());
            if (ball.getX() + ball.getLayoutBounds().getWidth() <= 0) {
                deleteBall();
            } else if (ball.hasCollisionWithPlane() && !Plane.getInstance().isBlinked()) {
                calCollision();
            }
        }else{
            ball.setY(ball.getY() - ball.getSpeed());
            if(ball.getY() + ball.getLayoutBounds().getHeight() <= 0){
                deleteBall();
            }else if(ball.hasCollisionWithPlane() && !Plane.getInstance().isBlinked()){
                calCollision();
            }
        }
    }
    private void calCollision(){
        deleteBall();
        Plane.getInstance().setHealth(Plane.getInstance().getHealth() - ball.getDamage());
        GameController.getInstance().updatePlayer();
        Plane.getInstance().setBlinked(true);
        Plane.getInstance().setTime0(System.currentTimeMillis());
    }
    private void deleteBall(){
        Ball.getBalls().remove(ball);
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(ball);
        ball.getBallShootTransition().stop();
    }
}
