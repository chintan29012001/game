package sample;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {
    private BallComponent playerball;
    private List<ObstacleComponent> total_obstacles;
    private List<Boolean> visited;
    private int count = 0;
    private List<ObstacleComponent> total_stars;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(900);
        settings.setHeight(900);
        settings.setTitle("Color Switch 2");
        settings.setMainMenuEnabled(true);

    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                playerball.up();
//                System.out.println(total_obstacles.size());
                for (int i = 0; i < total_obstacles.size(); i++) {
                    total_obstacles.get(i).getEntity().translateY(15);
                    if (playerball.getEntity().getY() < total_obstacles.get(i).getEntity().getY() && visited.get(i) == false) {
                        getGameState().increment("score", 1);
                        visited.set(i, true);

                    }
                }
                for (int i = 0; i < total_stars.size(); i++) {

                    total_stars.get(i).getEntity().translateY(15);
                }


            }

            @Override
            protected void onActionEnd() {
                playerball.stop();


            }
        }, KeyCode.W);
        onKeyDown(KeyCode.F, () -> {
            play("hello.wav");
        });

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("life", 1);


    }

    @Override
    protected void initUI() {
        Text score = new Text();

        score.layoutBoundsProperty().addListener((observable, oldValue, newBounds) -> {
            score.setTranslateX(getAppWidth() / 2 - newBounds.getWidth() / 2 + 100);
        });

        score.setTranslateY(50);
        score.setFont(Font.font(36));
        score.setFill(Color.WHITE);
        score.textProperty().bind(getGameState().intProperty("score").asString("score %d"));
        getGameScene().addUINode(score);

        Text life = new Text();

        life.layoutBoundsProperty().addListener((observable, oldValue, newBounds) -> {
            life.setTranslateX(getAppWidth() / 2 - newBounds.getWidth() / 2 - 100);
        });

        life.setTranslateY(50);
        life.setFont(Font.font(36));
        life.setFill(Color.WHITE);
        life.textProperty().bind(getGameState().intProperty("life").asString("life %d"));
        getGameScene().addUINode(life);

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory());
        getGameScene().setBackgroundColor(Color.rgb(5, 5, 5));
        initGameObjects();


    }

    @Override
    protected void onUpdate(double tpf) {
//        var a=getGameWorld().getEntitiesByComponent(ObstacleComponent.class);
//        int c=0;
//        f.physics.setAngularVelocity(1);
//        f.rotateBy(1);
//
//            if(f.getEntity().getY()>200)
//                f.down(-1*420);
//            else
//                f.stop();
        boolean a = false;


//        for(int i=0;i<total_obstacles.size();i++)
////            if(playerball.getEntity().getY()<=total_obstacles.get(i).getEntity().getY())
//                total_obstacles.get(i).getEntity().translateY(10);
//        if(a)
//        {
//            for(int i=0;i<total_obstacles.size();i++)
//                total_obstacles.get(i).getEntity().translateY(10);
//
//        }
//        for(int i=0;i<total_obstacles.size();i++)
//            if(total_obstacles.get(i).getEntity().getY()>getAppHeight()/2-100)
//                total_obstacles.get(i).getEntity().translateY(-5);

//
//
//        var a=getGameWorld().getEntitiesByType(EntityType.OBSTACLE);
//        for (var x:a)
//        {
////            x.rotateBy(1);
//
//        }


    }

    @Override
    protected void initPhysics() {
//        getPhysicsWorld().setGravity(0, 9);
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BALL, EntityType.OBSTACLE) {
                                                  @Override
                                                  protected void onCollision(Entity a, Entity b) {
//                System.out.println("helloasa");
//                System.out.println(a.getComponents());
//                System.out.println(b.getComponents());
                                                      try {

                                                          if (a.getComponent(BallComponent.class).getColor().equals(b.getComponent(ObstacleComponent.class).getColor())) {
                                                              System.out.println("1");
//                        ObstacleComponent m=b.getComponent(ObstacleComponent.class);
//                        System.out.println(m.idef);

                                                          } else {
                                                              System.out.println("collision" + count);

                                                              count++;
                                                          }

                                                      } catch (Exception e) {
                                                          if (b.getComponent(BallComponent.class).getColor().equals(a.getComponent(ObstacleComponent.class).getColor())) {
                                                              System.out.println("2");
//                        ObstacleComponent m=b.getComponent(ObstacleComponent.class);
//                        System.out.println(m.idef);

                                                          } else {
                                                              System.out.println("collision" + count);
                                                              count++;
                                                          }


                                                      }


                                                  }

                                                  @Override
                                                  protected void onCollisionEnd(Entity a, Entity b) {
                                                      super.onCollisionEnd(a, b);
                                                      try {

                                                          if (a.getComponent(BallComponent.class).getColor().equals(b.getComponent(ObstacleComponent.class).getColor())) {
                                                              System.out.println("1");


                                                          } else {
                                                              System.out.println("collision" + count);
                                                              getGameState().increment("life", -1);

                                                              count++;
                                                          }

                                                      } catch (Exception e) {
                                                          if (b.getComponent(BallComponent.class).getColor().equals(a.getComponent(ObstacleComponent.class).getColor())) {
                                                              System.out.println("2");


                                                          } else {
                                                              System.out.println("collision" + count);
                                                              getGameState().increment("life", -1);
                                                              count++;
                                                          }


                                                      }
                                                  }
                                              }

        );
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BALL, EntityType.ScoreBooster) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                if (a.getType() == EntityType.ScoreBooster)
                {
                    total_stars.remove(a.getComponent(ObstacleComponent.class));

                }

                else
                    {
                        total_stars.remove(b.getComponent(ObstacleComponent.class));

                }

            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                getGameState().increment("score", 100);
                if (a.getType() == EntityType.ScoreBooster)
                {

                    getGameWorld().removeEntity(a);
                }

                else
                {

                    getGameWorld().removeEntity(b);
                }

            }
        });


    }

    void createRectangle(double h, double w) {
        h -= 200;
        w += 200;
        double s = 350;
        SpawnData data = new SpawnData(w, h);
        data.put("side", s);
        data.put("rx", -s / 2);
        data.put("ry", s / 2);
        Entity obs1 = spawn("obstacle", data);
        obs1.getComponent(ObstacleComponent.class).setYcord(h);
        if (total_obstacles == null)
            total_obstacles = new ArrayList<>();
        total_obstacles.add(obs1.getComponent(ObstacleComponent.class));

        Entity obs2 = spawn("obstacle", data);
        obs2.getComponent(ObstacleComponent.class).setYcord(h);
        Rectangle a = new Rectangle(0, 0, 10, s);
        a.setFill(Color.GOLD);
        obs2.getViewComponent().clearChildren();
        obs2.getViewComponent().addChild(a);
        obs2.rotateBy(-90);
        ObstacleComponent temp = obs2.getComponent(ObstacleComponent.class);
        temp.setColor(Color.GOLD);
        total_obstacles.add(obs2.getComponent(ObstacleComponent.class));

        Entity obs3 = spawn("obstacle", data);
        obs3.getComponent(ObstacleComponent.class).setYcord(h);
        a = new Rectangle(0, 0, 10, s);
        a.setFill(Color.BLUE);
        obs3.getViewComponent().clearChildren();
        obs3.getViewComponent().addChild(a);
        obs3.setRotation(90);
        temp = obs3.getComponent(ObstacleComponent.class);
        temp.setColor(Color.BLUE);
        total_obstacles.add(obs3.getComponent(ObstacleComponent.class));

        Entity obs4 = spawn("obstacle", data);
        obs4.getComponent(ObstacleComponent.class).setYcord(h);
        a = new Rectangle(0, 0, 10, s);
        a.setFill(Color.BLUEVIOLET);
        obs4.getViewComponent().clearChildren();
        obs4.getViewComponent().addChild(a);
        obs4.setRotation(180);
        temp = obs4.getComponent(ObstacleComponent.class);
        temp.setColor(Color.BLUEVIOLET);
        total_obstacles.add(obs4.getComponent(ObstacleComponent.class));
    }

    void createFan(double h, double w) {
        h -= 200;
        w += 200;
        double s = 200;
        SpawnData data = new SpawnData(w, h);
        data.put("side", s);
        data.put("rx", 0.0);
        data.put("ry", s);
        Entity obs1 = spawn("obstacle", data);
        obs1.getComponent(ObstacleComponent.class).setYcord(h);
        if (total_obstacles == null)
            total_obstacles = new ArrayList<>();
        total_obstacles.add(obs1.getComponent(ObstacleComponent.class));

        Entity obs2 = spawn("obstacle", data);
        obs2.getComponent(ObstacleComponent.class).setYcord(h);
        Rectangle a = new Rectangle(0, 0, 10, s);
        a.setFill(Color.GOLD);
        obs2.getViewComponent().clearChildren();
        obs2.getViewComponent().addChild(a);
        obs2.rotateBy(90);
        ObstacleComponent temp = obs2.getComponent(ObstacleComponent.class);
        temp.setColor(Color.GOLD);
        total_obstacles.add(obs2.getComponent(ObstacleComponent.class));

        Entity obs3 = spawn("obstacle", data);
        obs3.getComponent(ObstacleComponent.class).setYcord(h);
        a = new Rectangle(0, 0, 10, s);
        a.setFill(Color.BLUE);
        obs3.getViewComponent().clearChildren();
        obs3.getViewComponent().addChild(a);
        obs3.setRotation(180);
        temp = obs3.getComponent(ObstacleComponent.class);
        temp.setColor(Color.BLUE);
        total_obstacles.add(obs3.getComponent(ObstacleComponent.class));

        Entity obs4 = spawn("obstacle", data);
        obs4.getComponent(ObstacleComponent.class).setYcord(h);
        a = new Rectangle(0, 0, 10, s);
        a.setFill(Color.BLUEVIOLET);
        obs4.getViewComponent().clearChildren();
        obs4.getViewComponent().addChild(a);
        obs4.setRotation(270);
        temp = obs4.getComponent(ObstacleComponent.class);
        temp.setColor(Color.BLUEVIOLET);
        total_obstacles.add(obs4.getComponent(ObstacleComponent.class));


    }

    void createTriangle(double h, double w) {
        h -= 200;
        w += 100;
        double s = 400;
        SpawnData data = new SpawnData(w, h);
        data.put("side", s);
        data.put("rx", (-s / 2) / Math.pow(3, 0.5));
        data.put("ry", s / 2);

        Entity obs1 = spawn("obstacle", data);
        obs1.getComponent(ObstacleComponent.class).setYcord(h);
//        obs1.rotateBy(30);
        if (total_obstacles == null)
            total_obstacles = new ArrayList<>();
        total_obstacles.add(obs1.getComponent(ObstacleComponent.class));
        ObstacleComponent temp;
        Rectangle a = new Rectangle(0, 0, 10, s);
        if (random(0, 2) == 0) {
            Entity obs2 = spawn("obstacle", data);
            obs2.getComponent(ObstacleComponent.class).setYcord(h);

            a.setFill(Color.GOLD);
            obs2.getViewComponent().clearChildren();
            obs2.getViewComponent().addChild(a);
            obs2.rotateBy(120);
//        obs2.translateX(-50);
            temp = obs2.getComponent(ObstacleComponent.class);
            temp.setColor(Color.GOLD);
            total_obstacles.add(obs2.getComponent(ObstacleComponent.class));
//
        } else {
            Entity obs3 = spawn("obstacle", data);
            obs3.getComponent(ObstacleComponent.class).setYcord(h);
            a = new Rectangle(0, 0, 10, s);
            a.setFill(Color.BLUE);
            obs3.getViewComponent().clearChildren();
            obs3.getViewComponent().addChild(a);
            obs3.setRotation(120);
            temp = obs3.getComponent(ObstacleComponent.class);
            temp.setColor(Color.BLUE);
            total_obstacles.add(obs3.getComponent(ObstacleComponent.class));

        }


//
        Entity obs4 = spawn("obstacle", data);
        obs4.getComponent(ObstacleComponent.class).setYcord(h);
        a = new Rectangle(0, 0, 10, s);
        a.setFill(Color.BLUEVIOLET);
        obs4.getViewComponent().clearChildren();
        obs4.getViewComponent().addChild(a);
        obs4.setRotation(240);
//        obs4.translateX(180);

        temp = obs4.getComponent(ObstacleComponent.class);
        temp.setColor(Color.BLUEVIOLET);
        total_obstacles.add(obs4.getComponent(ObstacleComponent.class));


    }

    private void initScoreBooster() {
        if(total_stars==null)
            total_stars=new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            Entity a = spawn("ScoreBooster", getAppWidth() / 2 - 25, getAppHeight() / 2 + 100 - 500 - 1000 * i);
            a.getComponent(ObstacleComponent.class).setYcord(getAppHeight() / 2 + 100 - 500 - 1000 * i);
            total_stars.add(a.getComponent(ObstacleComponent.class));
        }

    }

    private void initGameObjects() {


        Entity ball = spawn("ball", getAppWidth() / 2, getAppHeight() - 30);

        playerball = ball.getComponent(BallComponent.class);
        for (int i = 0; i < 10; i++) {
            int x = random(0, 3);
            switch (x) {
                case 0:
                    createRectangle(getAppHeight() / 2 - 700 * i, getAppWidth() / 2);
                    break;
                case 1:
                    createTriangle(getAppHeight() / 2 - 700 * i, getAppWidth() / 2);
                    break;
                case 2:
                    createFan(getAppHeight() / 2 - 700 * i, getAppWidth() / 2);
                    createFan(getAppHeight() / 2 - 700 * i, 50);
                    break;
            }
        }
        initScoreBooster();


        visited = new ArrayList<>(total_obstacles.size());

        for (int i = 0; i < total_obstacles.size(); i++)
            visited.add(false);

    }
}



