package test.entity;

import engine.*;
import entity.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ShipTest {
    private Ship ship;

    @BeforeEach
    public void setUp() {
        ship = new Ship(0, 0, Color.RED, DrawManager.SpriteType.Ship, false);
    }

    @Test
    public void testMoveRight() {
        int initialPositionX = ship.getPositionX();
        int speed = ship.getShipSpeed();

        ship.moveRight();

        Assertions.assertEquals(initialPositionX + speed, ship.getPositionX());
    }

    @Test
    public void testMoveLeft() {
        int initialPositionX = ship.getPositionX();
        int speed = ship.getShipSpeed();

        ship.moveLeft();

        Assertions.assertEquals(initialPositionX - speed, ship.getPositionX());
    }

    @Test
    public void testShoot() {
        Set<Bullet> bullets = new HashSet<>();
        int initialBulletsSize = bullets.size();
        int shooter = 1;

        ship.shoot(bullets, shooter);

        Assertions.assertEquals(initialBulletsSize + 1, bullets.size());
    }

    @Test
    public void testItemCoolTime() {
        Assertions.assertTrue(ship.itemCoolTime());
    }

    @Test
    public void testUpdate() {
        ship.update();

        Assertions.assertEquals(DrawManager.SpriteType.Ship, ship.getSpriteType());
    }

    @Test
    public void testDestroy() {
        ship.destroy();

        Assertions.assertTrue(ship.isDestroyed());
    }

    @Test
    public void testGetBULLET_SPEED() {
        int bulletSpeed = ship.getBULLET_SPEED();

        Assertions.assertEquals(-6, bulletSpeed);
    }

    @Test
    public void testGetShipSpeed() {
        int speed = ship.getShipSpeed();

        Assertions.assertEquals(2, speed);
    }

    @Test
    public void testSetShipSpeed() {
        ship.setShipSpeed(4);
        int speed = ship.getShipSpeed();

        Assertions.assertEquals(4, speed);
    }

    @Test
    public void testResetSpeed() {
        ship.setShipSpeed(4);
        ship.resetSpeed();
        int speed = ship.getShipSpeed();

        Assertions.assertEquals(2, speed);
    }

    @Test
    public void testGetItemImpact() {
        boolean itemImpact = ship.getItemImpact();

        Assertions.assertFalse(itemImpact);
    }

    @Test
    public void testItemImpactUpdate() {
        // 테스트를 위한 적절한 설정 추가

        ship.itemImpactUpdate();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testSetItemSpeed() {
        // 테스트를 위한 적절한 설정 추가

        ship.setItemSpeed();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testBuyItemSpeed() {
        // 테스트를 위한 적절한 설정 추가

        ship.buyItemSpeed();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testIsInvincible() {
        // 테스트를 위한 적절한 설정 추가

        boolean isInvincible = ship.isInvincible();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testRunInvincible() {
        // 테스트를 위한 적절한 설정 추가

        ship.runInvincible();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testGetItemQueue() {
        ItemQueue itemQueue = ship.getItemQueue();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testGetAuxiliaryShips() {
        List<Ship> auxiliaryShips = ship.getAuxiliaryShips();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testIsExistAuxiliaryShips() {
        boolean isExistAuxiliaryShips = ship.isExistAuxiliaryShips();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testSetAuxiliaryShipsMode() {
        // 테스트를 위한 적절한 설정 추가

        ship.setAuxiliaryShipsMode();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testSetExistAuxiliaryShips() {
        // 테스트를 위한 적절한 설정 추가

        ship.setExistAuxiliaryShips(true);

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testApplyFasterShootingItem() {
        // 테스트를 위한 적절한 설정 추가

        ship.applyFasterShootingItem();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testApplyLifeIncreaseItem() {
        // 테스트를 위한 적절한 설정 추가

        ship.applyLifeIncreaseItem(true);

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testGetHasLifeIncreaseItem() {
        // 테스트를 위한 적절한 설정 추가

        boolean hasLifeIncreaseItem = ship.getHasLifeIncreaseItem();

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testSetOriginalSpeed() {
        // 테스트를 위한 적절한 설정 추가

        ship.setOriginalSpeed(4);

        // 테스트 결과에 대한 Assertions 추가
    }

    @Test
    public void testGetOriginalSpeed() {
        // 테스트를 위한 적절한 설정 추가

        int originalSpeed = ship.getOriginalSpeed();

        // 테스트 결과에 대한 Assertions 추가
    }
}
